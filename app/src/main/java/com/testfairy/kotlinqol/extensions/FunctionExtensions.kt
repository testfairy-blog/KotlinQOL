package com.testfairy.kotlinqol.extensions

import android.util.Log


interface AsyncLoopPredicate {
    fun isReadyToProceed(): Boolean?
}

interface AsyncLoop : Runnable {
    fun cancel()
}

typealias Lambda = ()->Unit

@JvmOverloads
fun Lambda.runWithPeriod(period: Long = 1000, onCancel: () -> Unit = {}): AsyncLoop {
    val observer = android.os.Handler()
    val self = this

    val observeAction = object : AsyncLoop {
        private var canceled = false

        override fun run() {
            if (canceled) return
            self()
            observer.postDelayed(this, period)
        }

        override fun cancel() {
            canceled = true
            onCancel()
        }
    }

    observer.post(observeAction)

    return observeAction
}

@JvmOverloads
fun Lambda.runWithDelay(delay: Long = 1000): AsyncLoop {
    val observer = android.os.Handler()
    val self = this

    val observeAction = object : AsyncLoop {
        private var canceled = false

        override fun run() {
            if (canceled) return

            self()
        }

        override fun cancel() {
            canceled = true
        }
    }

    observer.postDelayed(observeAction, delay)

    return observeAction
}

@JvmOverloads
fun Lambda.observeOnce(predicate: AsyncLoopPredicate, checkPeriod: Long = 40, timeout: Long = 1000): AsyncLoop {
    val observer = android.os.Handler()
    val self = this

    // Enable this to detect neverending observers
    //        final Exception e = new RuntimeException("WTF");

    val observeAction = object : AsyncLoop {
        private var canceled = false
        private var timeElapsed = 0

        override fun run() {
            if (timeElapsed + checkPeriod > timeout) cancel()
            timeElapsed += 40

            if (canceled) return

            var readyToProceed: Boolean? = null
            try {
                readyToProceed = predicate.isReadyToProceed()
            } catch (e: Exception) {
                Log.v("Util", "Observing", e)
                observer.postDelayed(this, checkPeriod)
                return
            }

            if (readyToProceed != null && readyToProceed) {
                Log.v("Util", "Observed")
                self()
            } else {
                // Enable this to detect neverending observers
                //                    Log.v("Util", "Observing", e);
                Log.v("Util", "Observing")
                observer.postDelayed(this, checkPeriod)
            }
        }

        override fun cancel() {
            canceled = true
        }
    }

    observer.post(observeAction)

    return observeAction
}

@JvmOverloads
fun Lambda.observe(predicate: AsyncLoopPredicate, checkPeriod: Long = 40, timeout: Long = 1000): AsyncLoop {
    val observer = android.os.Handler()
    val self = this

    val observeAction = object : AsyncLoop {
        private var canceled = false
        private var timeElapsed = 0L

        override fun run() {
            if (timeElapsed + checkPeriod > timeout) cancel()
            timeElapsed += checkPeriod

            if (canceled) return

            var readyToProceed: Boolean? = null
            try {
                readyToProceed = predicate.isReadyToProceed()
            } catch (e: Exception) {
                Log.v("Util", "Observing");
                observer.postDelayed(this, checkPeriod)
                return
            }

            if (readyToProceed != null && readyToProceed) {
                Log.v("Util", "Observed");
                self()
                observer.postDelayed(this, checkPeriod)
            } else {
                Log.v("Util", "Observing");
                observer.postDelayed(this, checkPeriod)
            }
        }

        override fun cancel() {
            canceled = true
        }
    }

    observer.post(observeAction)

    return observeAction
}