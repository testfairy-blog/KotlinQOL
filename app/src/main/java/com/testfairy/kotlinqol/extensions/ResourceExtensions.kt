package com.testfairy.kotlinqol.extensions

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat


fun Int?.drawableByResourceId(context: Context): Drawable? {
    this?.let {
        return@drawableByResourceId ContextCompat.getDrawable(context, it)
    }

    return null
}

fun String?.drawableByName(context: Context): Drawable? {
    this?.let {
        val resourceId = context.resources.getIdentifier(it, "drawable", context.packageName)
        return@drawableByName resourceId.drawableByResourceId(context)
    }

    return null
}

@ColorInt
fun Int?.colorById(context: Context): Int? {
    this?.let {
        return@colorById ContextCompat.getColor(context, it)
    }

    return null
}

fun Int?.colorDrawableByResourceId(context: Context): Drawable? {
    this?.let {
        it.colorById(context)?.let {
            return@colorDrawableByResourceId ColorDrawable(it)
        }
    }

    return null
}


