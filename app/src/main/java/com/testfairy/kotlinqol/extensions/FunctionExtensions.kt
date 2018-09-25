//  Created by Diego Perini, TestFairy
//  License: Public Domain
package com.testfairy.kotlinqol.extensions

import android.os.AsyncTask
import android.util.Log

interface ScheduledJob {
    fun cancel()
}

interface RunnableScheduledJob: ScheduledJob, Runnable {}

typealias Lambda = ()->Unit
typealias Predicate = ()->Boolean

@JvmOverloads
fun Lambda.runWithPeriod(period: Long = 1000, onCancel: () -> Unit = {}): ScheduledJob {
    val observer = android.os.Handler()
    val self = this

    val job = object : ScheduledJob, Runnable {
        private var canceled = false

        override fun run() {
            synchronized(this) {
                if (canceled) return
                self()
                observer.postDelayed(this, period)
            }
        }

        override fun cancel() {
            synchronized(this) {
                canceled = true
                observer.post(onCancel)
            }
        }
    }

    observer.post(job)

    return job
}

@JvmOverloads
fun Lambda.runWithDelay(delay: Long = 1000, onCancel: () -> Unit = {}): RunnableScheduledJob {
    val observer = android.os.Handler()
    val self = this

    val job = object : RunnableScheduledJob {
        private var canceled = false
        private var done = false

        override fun run() {
            synchronized(this) {
                if (canceled || done) return

                done = true
                self()
            }
        }

        override fun cancel() {
            synchronized(this) {
                canceled = true
                observer.post(onCancel)
            }
        }
    }

    observer.postDelayed(job, delay)

    return job
}

@JvmOverloads
fun Lambda.observeOnce(predicate: Predicate, checkPeriod: Long = 40, timeout: Long = 1000): RunnableScheduledJob {
    val observer = android.os.Handler()
    val self = this

    // Enable this to detect neverending observers
    //        final Exception e = new RuntimeException("WTF");

    val job = object : RunnableScheduledJob {
        private var canceled = false
        private var done = false
        private var timeElapsed = 0

        override fun run() {
            synchronized(this) {
                if (timeElapsed + checkPeriod > timeout) cancel()
                timeElapsed += 40

                if (canceled) return

                var readyToProceed: Boolean? = null
                try {
                    readyToProceed = predicate()
                } catch (e: Exception) {
                    Log.v("Util", "Observing", e)
                    observer.postDelayed(this, checkPeriod)
                    return
                }

                if (readyToProceed && !done) {
                    Log.v("Util", "Observed")
                    done = true
                    self()
                } else if (!done) {
                    Log.v("Util", "Observing")
                    observer.postDelayed(this, checkPeriod)
                }
            }
        }

        override fun cancel() {
            synchronized(this) {
                canceled = true
            }
        }
    }

    observer.post(job)

    return job
}

@JvmOverloads
fun Lambda.observe(predicate: Predicate, checkPeriod: Long = 40, timeout: Long = 1000): ScheduledJob {
    val observer = android.os.Handler()
    val self = this

    val job = object : RunnableScheduledJob {
        private var canceled = false
        private var timeElapsed = 0L

        override fun run() {
            synchronized(this) {
                if (timeElapsed + checkPeriod > timeout) cancel()
                timeElapsed += checkPeriod

                if (canceled) return

                var readyToProceed: Boolean? = null
                try {
                    readyToProceed = predicate()
                } catch (e: Exception) {
                    Log.v("Util", "Observing");
                    observer.postDelayed(this, checkPeriod)
                    return
                }

                if (readyToProceed) {
                    Log.v("Util", "Observed");
                    self()
                    observer.postDelayed(this, checkPeriod)
                } else {
                    Log.v("Util", "Observing");
                    observer.postDelayed(this, checkPeriod)
                }
            }
        }

        override fun cancel() {
            synchronized(this) {
                canceled = true
            }
        }
    }

    observer.post(job)

    return job
}

fun Lambda.invokeInBackground(): RunnableScheduledJob {
    val self = this
    val job = object : RunnableScheduledJob {
        private var canceled = false
        private var done = false

        override fun run() {
            synchronized(this) {
                if (canceled || done) return

                done = true
                self()
            }
        }

        override fun cancel() {
            synchronized(this) {
                canceled = true
            }
        }
    }

    AsyncTask.execute(job)

    return job
}
