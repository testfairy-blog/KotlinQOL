//  Created by Diego Perini, TestFairy
//  License: Public Domain
package com.testfairy.kotlinqol.extensions

import android.util.Log


fun notNull(vararg os: Any?): Boolean {
    var result = true

    for (o in os) {
        result = result && o != null
        if (!result) break
    }

    return result
}

fun Any?.LogTaggedV(tag: String) {
    if (this != null) {
        Log.v(tag, this.toString())
    } else {
        Log.d("Unexpected Null " + tag, "error: ", NullPointerException())
    }
}

fun Any?.LogTaggedW(tag: String) {
    if (this != null) {
        Log.w(tag, this.toString())
    } else {
        Log.d("Unexpected Null " + tag, "error: ", NullPointerException())
    }
}

fun Any?.LogTaggedE(tag: String) {
    if (this != null) {
        Log.e(tag, this.toString())
    } else {
        Log.d("Unexpected Nul l" + tag, "error: ", NullPointerException())
    }
}

fun Any?.LogTaggedD(tag: String) {
    if (this != null) {
        Log.d(tag, this.toString())
    } else {
        Log.d("Unexpected Null " + tag, "error: ", NullPointerException())
    }
}

fun Any?.LogTaggedI(tag: String) {
    if (this != null) {
        Log.i(tag, this.toString())
    } else {
        Log.d("Unexpected Null " + tag, "error: ", NullPointerException())
    }
}


fun Any?.LogV() {
    if (this != null) {
        Log.v(this.javaClass.simpleName, this.toString())
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogW() {
    if (this != null) {
        Log.w(this.javaClass.simpleName, this.toString())
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogE() {
    if (this != null) {
        Log.e(this.javaClass.simpleName, this.toString())
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogD() {
    if (this != null) {
        Log.d(this.javaClass.simpleName, this.toString())
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogI() {
    if (this != null) {
        Log.i(this.javaClass.simpleName, this.toString())
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}


fun Any?.LogV(msg: String) {
    if (this != null) {
        Log.v(this.javaClass.simpleName, msg)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogW(msg: String) {
    if (this != null) {
        Log.w(this.javaClass.simpleName, msg)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogE(msg: String) {
    if (this != null) {
        Log.e(this.javaClass.simpleName, msg)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogD(msg: String) {
    if (this != null) {
        Log.d(this.javaClass.simpleName, msg)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogI(msg: String) {
    if (this != null) {
        Log.i(this.javaClass.simpleName, msg)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}


fun Any?.LogV(msg: String, e: Throwable) {
    if (this != null) {
        Log.v(this.javaClass.simpleName, msg, e)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogW(msg: String, e: Throwable) {
    if (this != null) {
        Log.w(this.javaClass.simpleName, msg, e)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogE(msg: String, e: Throwable) {
    if (this != null) {
        Log.e(this.javaClass.simpleName, msg, e)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogD(msg: String, e: Throwable) {
    if (this != null) {
        Log.d(this.javaClass.simpleName, msg, e)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

fun Any?.LogI(msg: String, e: Throwable) {
    if (this != null) {
        Log.i(this.javaClass.simpleName, msg, e)
    } else {
        Log.d("Unexpected Null", "error: ", NullPointerException())
    }
}

inline fun <T, R> T?.let(_then: (T)->R?, _else: ()->R?): R? {
    if (this != null) {
        return _then.invoke(this)
    } else {
        return _else.invoke()
    }
}
