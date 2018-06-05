package com.project.Jack.kotlin.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ListView

/**
 * 自定义可适应ScrollView的ListView
 */

class ListViewForScrollView : ListView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet,
                defStyle: Int) : super(context, attrs, defStyle) {
    }

    override
            /**
             * 重写该方法，达到使ListView适应ScrollView的效果
             */
    fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
                View.MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }


}
