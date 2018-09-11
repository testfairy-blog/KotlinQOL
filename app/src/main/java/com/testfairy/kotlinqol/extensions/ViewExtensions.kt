//  Created by Diego Perini, TestFairy
//  License: Public Domain
package com.testfairy.kotlinqol.extensions

import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


// All Views
fun View?.hide() {
    this?.let {
        alpha = 0f
        visibility = View.GONE
    }
}

fun View?.show() {
    this?.let {
        alpha = 1f
        visibility = View.VISIBLE
    }
}

fun View?.updateWidthHeight(w: Int? = null, h: Int? = null) {
    this?.let {
        val lp = it.layoutParams
        if (lp != null) {
            lp.width = if (w != null) w else lp.width
            lp.height = if (h != null) h else lp.height
            it.layoutParams = lp
            it.requestLayout()
            it.invalidate()
        }
    }
}

fun View?.removeFromParent() {
    this?.let {
        val parent: ViewGroup? = it.parent as ViewGroup?
        parent?.removeView(it)
    }
}

// Image View
@JvmOverloads
fun ImageView?.loadImage(picturePath: String?, placeholder: Drawable? = null, error: Drawable? = null) {
    this?.let {
        Glide.with(context)
                .load(picturePath)
                .apply {
                    var options = RequestOptions()
                            .centerCrop()

                    placeholder?.let { options.placeholder(placeholder) }
                    error?.let { options.error(error) }
                }
                .into(this)
    }
}

fun ImageView?.setImageDrawable(@DrawableRes resource: Int) {
    this?.setImageDrawable(resource.drawableByResourceId(context))
}


// Text View
fun TextView?.setDrawableLeft(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(resource.drawableByResourceId(context), null, null, null)
}

fun TextView?.setDrawableRight(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, resource.drawableByResourceId(context), null)
}

fun TextView?.setDrawableTop(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, resource.drawableByResourceId(context), null, null)
}

fun TextView?.setDrawableBottom(@DrawableRes resource: Int) {
    this?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, resource.drawableByResourceId(context))
}
