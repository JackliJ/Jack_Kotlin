package com.project.Jack.kotlin.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.project.Jack.kotlin.R
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.note_editor_layout.*

/**
 * Create by www.lijin@foxmail.com on 2018/5/7 0007.
 * <br/>
 *  编辑备忘录的页面
 */
class NoteEditorActivity : Activity(), View.OnClickListener {

    //返回按钮
    var vBack: LinearLayout? = null
    //保存按钮
    var vTvSave: TextView? = null
    //编辑器
    var vEdit: RichEditor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_editor_layout)
        initView()

    }

    //初始化组件
    fun initView() {
        vBack = edit_li_memo
        vTvSave = edit_tv_save
        vEdit = edit_editor
        //初始化编辑器按钮   开始
        action_undo.setOnClickListener(this)
        action_redo.setOnClickListener(this)
        action_bold.setOnClickListener(this)
        action_italic.setOnClickListener(this)
        action_subscript.setOnClickListener(this)
        action_superscript.setOnClickListener(this)
        action_strikethrough.setOnClickListener(this)
        action_underline.setOnClickListener(this)
        action_heading1.setOnClickListener(this)
        action_heading2.setOnClickListener(this)
        action_heading3.setOnClickListener(this)
        action_heading4.setOnClickListener(this)
        action_heading5.setOnClickListener(this)
        action_heading6.setOnClickListener(this)
        action_txt_color.setOnClickListener(this)
        action_bg_color.setOnClickListener(this)
        action_indent.setOnClickListener(this)
        action_outdent.setOnClickListener(this)
        action_align_left.setOnClickListener(this)
        action_align_center.setOnClickListener(this)
        action_align_right.setOnClickListener(this)
        action_insert_bullets.setOnClickListener(this)
        action_insert_numbers.setOnClickListener(this)
        action_blockquote.setOnClickListener(this)
        action_insert_image.setOnClickListener(this)
        action_insert_link.setOnClickListener(this)
        action_insert_checkbox.setOnClickListener(this)
        //初始化编辑器按钮   结束
        initEdit()
    }


    /**
     * 点击事件
     */
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.action_undo -> //往前返回编辑器
                vEdit?.undo()
            R.id.action_redo -> //往后返回编辑器
                vEdit?.redo()
            R.id.action_bold ->//是否加粗
                vEdit?.setBold()
            R.id.action_italic ->
                vEdit?.setItalic()
            R.id.action_subscript ->
                vEdit?.setSubscript()
            R.id.action_superscript ->
                vEdit?.setSubscript()
            R.id.action_strikethrough ->
                vEdit?.setStrikeThrough()
            R.id.action_underline ->
                vEdit?.setUnderline()
            R.id.action_heading1 ->
                vEdit?.setHeading(1)
            R.id.action_heading2 ->
                vEdit?.setHeading(2)
            R.id.action_heading3 ->
                vEdit?.setHeading(3)
            R.id.action_heading4 ->
                vEdit?.setHeading(4)
            R.id.action_heading5 ->
                vEdit?.setHeading(5)
            R.id.action_heading6 ->
                vEdit?.setHeading(6)
            R.id.action_txt_color ->
                setEditTextColor()
            R.id.action_bg_color ->
                setTextBackgroundColor()
            R.id.action_indent ->
                vEdit?.setIndent()
            R.id.action_outdent ->
                vEdit?.setOutdent()
            R.id.action_align_left ->
                vEdit?.setAlignLeft()
            R.id.action_align_center ->
                vEdit?.setAlignCenter()
            R.id.action_align_right ->
                vEdit?.setAlignRight()
            R.id.action_insert_bullets ->
//                    vEdit?.insertImage()
                Toast.makeText(this, "insert bullets", Toast.LENGTH_SHORT).show()
            R.id.action_insert_numbers ->
                Toast.makeText(this, "insert numbers", Toast.LENGTH_SHORT).show()
            R.id.action_blockquote ->
                vEdit?.setBlockquote()
            R.id.action_insert_image ->

                Toast.makeText(this, "insert image", Toast.LENGTH_SHORT).show()
            R.id.action_insert_link ->
                Toast.makeText(this, "insert link", Toast.LENGTH_SHORT).show()
            R.id.action_insert_checkbox ->
                vEdit?.insertTodo()


        }
    }

    //用于判断字体的当前颜色
    var mTextConlorChecked = false
    var mIsBackChecked = false
    //设置字体颜色
    fun setEditTextColor() {
        if (mTextConlorChecked) {
            vEdit?.setTextColor(Color.BLACK)
            mTextConlorChecked = true
        } else {
            vEdit?.setTextColor(Color.RED)
            mTextConlorChecked = false
        }
    }
    //设置字体背景颜色
    fun setTextBackgroundColor() {
        if (mIsBackChecked) {
            vEdit?.setTextBackgroundColor(Color.WHITE)
            mIsBackChecked = true
        } else {
            vEdit?.setTextBackgroundColor(Color.YELLOW)
            mIsBackChecked = false
        }
    }

    //初始化富文本编辑器
    fun initEdit() {
        vEdit?.setEditorHeight(200)
        //设置字体大小
        vEdit?.setEditorFontSize(16)
        //设置字体颜色
        vEdit?.setEditorFontColor(Color.BLACK)
        //初始化内边距
        vEdit?.setPadding(5, 5, 5, 5)
        //设置编辑器是否可用
        vEdit?.setInputEnabled(true)
    }
}