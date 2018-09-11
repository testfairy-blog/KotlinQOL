//  Created by Diego Perini, TestFairy
//  License: Public Domain
package com.testfairy.kotlinqol.extensions


fun Float.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)