@file:Suppress("NOTHING_TO_INLINE")

package com.app.newsfeed.utils

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

//returns dip(dp) dimension value in pixels
inline fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

inline fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()
inline fun Context.dipf(value: Int): Float = (value * resources.displayMetrics.density)

inline fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

inline fun Context.px2dip(px: Int): Float = px.toFloat() / resources.displayMetrics.density
inline fun Context.px2sp(px: Int): Float = px.toFloat() / resources.displayMetrics.scaledDensity

inline fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        .toInt()
}

inline fun Context.scrimBackground(): Int {
    return themeAttributeToColor(com.google.android.material.R.attr.scrimBackground)
}

inline fun Context.textColorPrimary(): Int {
    return themeAttributeToColor(android.R.attr.textColorPrimary)
}

inline fun Context.textColorSecondary(): Int {
    return themeAttributeToColor(android.R.attr.textColorSecondary)
}

inline fun Context.colorSurface(): Int {
    return themeAttributeToColor(com.google.android.material.R.attr.colorSurface)
}

inline fun Context.colorBackground(): Int {
    return themeAttributeToColor(android.R.attr.colorBackground)
}

inline fun Context.colorPrimary(): Int {
    return themeAttributeToColor(com.google.android.material.R.attr.colorPrimary)
}

inline fun Context.colorAccent(): Int {
    return themeAttributeToColor(com.google.android.material.R.attr.colorAccent)
}

inline fun Context.colorControlNormal(): Int {
    return themeAttributeToColor(com.google.android.material.R.attr.colorControlNormal)
}

inline fun Context.colorPrimaryId(): Int {
    return themeAttributeToResId(com.google.android.material.R.attr.colorPrimary)
}


fun Context.themeAttributeToColor(themeAttributeId: Int, fallbackColor: Int = Color.WHITE): Int {
    val outValue = TypedValue()
    val theme = this.theme
    val resolved = theme.resolveAttribute(themeAttributeId, outValue, true)
    if (resolved) {
        return ContextCompat.getColor(this, outValue.resourceId)
    }
    return fallbackColor
}

fun Context.themeAttributeToResId(themeAttributeId: Int): Int {
    val outValue = TypedValue()
    val theme = this.theme
    val resolved = theme.resolveAttribute(themeAttributeId, outValue, true)
    if (resolved) {
        return outValue.resourceId
    }
    return -1
}