package com.testfairy.kotlinqol

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.testfairy.kotlinqol.extensions.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test_AnyExtensions()
        test_NumberExtensions()
        test_ResourceExtensions()
        test_ViewExtensions()
        test_FunctionExtensions()
    }

    fun test_AnyExtensions() {
        // Regular logging of a variable
        val aNotNull = "Hello"
        aNotNull.LogD()

        // Nulls can be logged too
        val aNull:Object? = null
        aNull?.LogV()

        // Logs can be tagged
        1.LogTaggedI("A one")

        // Logs can include custom messages
        this.LogW("I'm logging my Activity")

        // Logs can include stack traces
        this.LogE("OMG, an exception", RuntimeException("Huge error"))

        // Combined null check of multiple variables
        if (notNull(this, aNotNull, aNull))
        {
            "All is not null.".LogD()
        } else {
            "Some nulls creep there.".LogD()
        }
    }

    fun test_NumberExtensions() {
        // Float format
        123.456f.format(1).LogTaggedD("Formatted float")

        // Double format
        123.456.format(1).LogTaggedD("Formatted double")
    }

    fun test_ResourceExtensions() {
        // Drawable id -> Drawable
        R.drawable.ic_launcher_background.drawableByResourceId(this).LogTaggedD("A drawable by id")

        // Drawable name -> Drawable
        "ic_launcher_background".drawableByName(this).LogTaggedD("A drawable by name")

        // Color id -> Int (as in RGBA 32 bit integer)
        R.color.colorPrimary.colorById(this).LogTaggedD("A color Int by id")

        // Color id -> Drawable
        R.color.colorPrimary.colorDrawableByResourceId(this).LogTaggedD("A color Drawable by id")
    }

    fun test_ViewExtensions() {
        // Show / Hide
        helloWorldView.hide()
        helloWorldView.show()

        // Update layout params
        helloWorldView.updateWidthHeight(100, 100)

        // Set surrounding drawables
        helloWorldView.setDrawableLeft(R.drawable.abc_btn_check_material)
        helloWorldView.setDrawableTop(R.drawable.abc_btn_check_material)
        helloWorldView.setDrawableRight(R.drawable.abc_btn_check_material)
        helloWorldView.setDrawableBottom(R.drawable.abc_btn_check_material)

        // Load image from URL
        helloImageView.loadImage("http://placekitten.com/200/300")

        // Load image from assets
        helloImageView.setImageDrawable(R.drawable.ic_launcher_background.drawableByResourceId(this))

        // Remove from View tree
//        helloWorldView.removeFromParent()
    }

    fun test_FunctionExtensions() {
        val random  = Random()

        // This function will be called every 1 second until it is canceled
        val repeatingJob = {
            "Hello World always".LogD()
        }.runWithPeriod(1000)
//        repeatingJob.cancel()

        // This function will be called after 5 seconds passes if remains uncanceled
        val oneTimeJob = {
            "Hellow World with delay".LogD()
        }.runWithDelay(5000)
//        oneTimeJob.cancel()

        // This function will be called every 40 miliseconds if and only if the predicate is true
        val repeatedCheck = {
            "Sometimes I win!".LogD()
        }.observe(object : AsyncLoopPredicate {
            override fun isReadyToProceed(): Boolean? {
                return random.nextBoolean()
            }
        })
//        repeatedCheck.cancel()

        // This function will be called only once and only when the first time predicate returns true
        val onceCheck = {
            "When I win, it ends!".LogD()
        }.observeOnce(object : AsyncLoopPredicate {
            override fun isReadyToProceed(): Boolean? {
                return random.nextBoolean()
            }
        })
//        onceCheck.cancel()
    }
}
