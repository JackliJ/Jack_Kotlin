package com.project.Jack.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.project.Jack.kotlin.ui.NoteEditorActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(),View.OnClickListener {


    private var vRecyclerView : RecyclerView? = null
    private var vImgEdit : ImageView? = null

    private var mContext : Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        initView()

    }

    /**
     * 初始化组件
     */
    fun initView(){
        vRecyclerView = main_rv
        vImgEdit = main_img_editor

        vImgEdit?.setOnClickListener(this)
    }

    //全局点击事件
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.main_img_editor ->
                startIntent()
        }
    }

    fun startIntent(){
        val intents = Intent()
        intents.setClass(this,NoteEditorActivity::class.java)
        startActivity(intents)
    }
}
