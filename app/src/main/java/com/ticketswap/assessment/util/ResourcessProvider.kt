package com.ticketswap.assessment.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

interface ResourcesProvider {

    fun getString(@StringRes stringId: Int): String

    fun getString(@StringRes stringId: Int, vararg args: Any): String

    fun getColor(@ColorRes colorId: Int): Int

    fun getDimen(@DimenRes dimenId: Int): Int
}

class ResourcesProviderImpl(private val context: Context) : ResourcesProvider {

    override fun getString(stringId: Int): String = context.getString(stringId)

    override fun getString(stringId: Int, vararg args: Any): String =
        context.getString(stringId, *args)

    override fun getColor(colorId: Int): Int =
        ResourcesCompat.getColor(context.resources, colorId, null)

    override fun getDimen(dimenId: Int) = context.resources.getDimensionPixelSize(dimenId)
}