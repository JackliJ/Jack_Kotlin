package com.project.Jack.kotlin.extension

import android.content.Context
import androidx.core.content.ContextCompat
import com.project.Jack.kotlin.sqldata.DatabaseOpenHelper

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.instance
