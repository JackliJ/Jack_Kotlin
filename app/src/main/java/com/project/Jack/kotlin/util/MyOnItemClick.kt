package com.project.Jack.kotlin.util

import android.view.View

/**
 * 外部点击事件接口
 * Create by www.lijin@foxmail.com on 2018/6/6 0006.
 * <br></br>
 */

interface MyOnItemClick {

    fun onItemClick(view: View, postion: Int)

    fun onLongClick(view: View, position: Int)
}
