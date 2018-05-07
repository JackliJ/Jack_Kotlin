package com.project.Jack.kotlin.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import com.project.Jack.kotlin.R
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.note_editor_layout.*

/**
 * Create by www.lijin@foxmail.com on 2018/5/7 0007.
 * <br/>
 *  编辑备忘录的页面
 */
class NoteEditorActivity : Activity(){

    var vBack : LinearLayout? = null
    var vTvSave : TextView? = null
    var vEdit : RichEditor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_editor_layout)
        initView()

    }

    //初始化组件
    fun initView(){
        vBack = edit_li_memo
        vTvSave = edit_tv_save
        vEdit = edit_editor
        initEdit()
    }

    //初始化富文本编辑器
    fun initEdit(){
        vEdit?.setEditorHeight(200)
        //设置字体大小
        vEdit?.setEditorFontSize(16)
        //设置字体颜色
        vEdit?.setEditorFontColor(Color.BLACK)
        //初始化内边距
        vEdit?.setPadding(5,5,5,5)
        //设置默认显示语句
        vEdit?.setPlaceholder("Insert text here...")
        //设置编辑器是否可用
        vEdit?.setInputEnabled(true)
    }
}