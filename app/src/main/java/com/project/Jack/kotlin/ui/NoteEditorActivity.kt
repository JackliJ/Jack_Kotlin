package com.project.Jack.kotlin.ui

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.bigkoo.pickerview.OptionsPickerView
import com.bigkoo.pickerview.listener.CustomListener
import com.project.Jack.kotlin.R
import com.project.Jack.kotlin.extension.database
import com.project.Jack.kotlin.model.Notepad
import com.project.Jack.kotlin.sqldata.NotePadTable
import com.project.Jack.kotlin.util.CustomDialog
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.note_editor_layout.*
import kotlinx.android.synthetic.main.pickerview_custom_time_options.*
import org.jetbrains.anko.db.insert
import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by www.lijin@foxmail.com on 2018/5/7 0007.
 * <br/>
 * 编辑备忘录的页面
 */
class NoteEditorActivity : Activity(), View.OnClickListener {

    //返回按钮
    var vBack: LinearLayout? = null
    //保存按钮
    var vTvSave: TextView? = null
    //编辑器
    var vEdit: RichEditor? = null
    //
    var mEdString: String? = null
    //时间选择器
    var pvCustomOptions : OptionsPickerView<String>?  = null
    //时间数据集
    var mData : MutableList<String>? = ArrayList()
    var mHours : MutableList<String>? = ArrayList()
    var mMinutes : MutableList<String>? = ArrayList()
    //保存月分集合给上传时用
    var mUpDate : MutableList<String>? = ArrayList()
    //当前年月日时分
    var mYear : Int? = 0
    var mMonth : String? = null
    var mDay : String? = null
    var mHourTxt : String? = null
    var mMinuteTxt : String? = null
    //保存要上传的时间
    var uploadTime : String? =null
    //时间控件
    var vTTime : TextView? = null
    var vEdTitle : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_editor_layout)
        //初始化组件
        initView()
        //初始化时间选择器
        initDate()
    }
    //初始化时间数据集
    fun initDate(){
        //月
        mMonth = resources.getString(R.string.account_month)
        //日
        mDay = resources.getString(R.string.account_day)
        //时
        mHourTxt = resources.getString(R.string.account_hour)
        //分
        mMinuteTxt = resources.getString(R.string.account_minute)
        //初始化日期
        var cal : Calendar = Calendar.getInstance()
        cal.time = Date()
        //保存当前年，后面上传拼接用到
        mYear = cal.get(Calendar.YEAR)
        //默认显示今天的时间 如05月10 9：50
        var dd = (cal.get(Calendar.MONTH)+1).toString() +
                mMonth + cal.get(Calendar.DATE).toString() +
                mDay + " "+ cal.get(Calendar.HOUR_OF_DAY).toString() + "：" +
                forMatMinute(cal.get(Calendar.MINUTE))
        //初始化为今天当前的时间
        vTTime?.text = resources.getString(R.string.account_today) + dd
        var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        uploadTime = df.format(Date())

        //加载7天内的数据
        for (i in 0..6){
            mData?.add(getDateStr(-i)!!)
        }

        //初始化小时
        for (i in 0..23){
            if(i <= 9){
                mHours?.add("0"+i+mHourTxt)
            }else{
                mHours?.add(i.toString() + mHourTxt)
            }
        }

        //初始化分钟
        for (i in 0..59){
            if(i <= 9){
                mMinutes?.add("0"+i+mMinuteTxt)
            }else{
                mMinutes?.add(i.toString()+mMinuteTxt)
            }
        }

        //初始化时间dialog
        pvCustomOptions = OptionsPickerView.Builder(
                this, OptionsPickerView.OnOptionsSelectListener { options1, options2, options3, v ->
            //拼接并替换成如 05月09日 19:30
            var str = mData?.get(options1) + " " + mHours?.get(options2)?.replace(mHourTxt!!,":")+mMinutes!!.get(options3).replace(mMinuteTxt!!,"")
            //表示选择的是今天
            if(options1 == 0){
                vTTime?.text = resources.getString(R.string.account_today) +  str
            }else{
                vTTime?.text = str
            }
            //拼接上传的时间为 "yyyy-MM-dd HH:mm:ss"
            var date = mUpDate?.get(options1)
            var hour = mHours?.get(options2)?.substring(0,2)
            var minute = mMinutes?.get(options3)?.substring(0,2)
            uploadTime = mYear.toString() + "-" + date +" " + hour + ":" + minute
        }
        ).setLayoutRes(R.layout.pickerview_custom_time_options,object : CustomListener{
            override fun customLayout(v: View) {
                var mfinish : TextView = v.findViewById(R.id.tv_finish)
                var mCancel : TextView = v.findViewById(R.id.iv_cancel)
                mfinish.setOnClickListener({v->
                    pvCustomOptions!!.returnData()
                    pvCustomOptions!!.dismiss()
                })
                mCancel.setOnClickListener({v->
                    pvCustomOptions!!.dismiss()
                })
            }
        }).build() as OptionsPickerView<String>?

        //添加数据
        pvCustomOptions?.setNPicker(mData,mHours,mMinutes)
    }

    //获取格式化后的拼接日期 如：05月10
    fun getDateStr(distanceDay : Int) : String{
        var beginDate = Date()
        var dft = SimpleDateFormat("MM-dd")
        var cal = Calendar.getInstance()
        cal.time = beginDate
        cal.add(Calendar.DATE, distanceDay)
        mUpDate?.add(dft.format(cal.getTime()))
        return dft.format(cal.time).replace("-",mMonth!!) + mDay
    }

    //格式化分钟，让其显示显示两个 如09
    fun forMatMinute(minute : Int) : String{
        var m = ""
        if(minute < 10){
            m = "0" + minute
        }else{
            m = "" + minute
        }
        return m
    }

    //初始化组件
    fun initView() {
        vBack = edit_li_memo
        vTvSave = edit_tv_save
        vEdit = edit_editor
        vTTime = dialog_tv_time
        vEdTitle = dialog_ed_title
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
        vTTime?.setOnClickListener(this)
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
            R.id.dialog_tv_time ->{
                pvCustomOptions?.show()
            }


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

        //实时获取文本
        vEdit?.setOnTextChangeListener(object : RichEditor.OnTextChangeListener {

            override fun onTextChange(text: String?) {
                mEdString = text
            }
        })

        //保存点击事件
        vTvSave?.setOnClickListener({ v ->
            //弹出保存dialog
            if(!TextUtils.isEmpty(vEdTitle?.text.toString())){
                startDialog()
            }else{
                Toast.makeText(this,resources.getString(R.string.note_edit_title),Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun startDialog(){
        //赋值dialog布局
        var v : View = LayoutInflater.from(this).inflate(R.layout.dialog_save,null)
        //赋值组件
        var vImageClose : ImageView = v.findViewById(R.id.iv_close)
        var vTvSave : TextView = v.findViewById(R.id.tv_save)
        var vEdauth : EditText = v.findViewById(R.id.dialog_ed_author)
        var vEdAddress : EditText = v.findViewById(R.id.dialog_ed_address)

        var vCustomViewDialog : CustomDialog = CustomDialog.Builder(this, CustomDialog.MODE_CUSTOM)
                .setView(v).setBackground(R.drawable.bg_customdialog_language)
                .build()

        //关闭按钮
        vImageClose.setOnClickListener({ v ->
            if(vCustomViewDialog != null && vCustomViewDialog.isShowing()){
                vCustomViewDialog.dismiss()
            }
        })

        //做数据库保存操作
        vTvSave.setOnClickListener({v ->
            if(TextUtils.isEmpty(vEdauth.text.trim())){
                vEdauth.setText(" ")
            }
            if(TextUtils.isEmpty(vEdAddress.text.trim())){
                vEdAddress.setText(" ")
            }
            var mNotepad = Notepad(vEdTitle?.text?.trim().toString(),vEdauth?.text?.trim().toString(),
                    uploadTime!!,vEdAddress?.text?.trim().toString(), mEdString!!)
            database.use {
                insert(NotePadTable.TABLE_NAME,
                        NotePadTable.TITLE to mNotepad.NTiTle,
                        NotePadTable.NAME to mNotepad.NName,
                        NotePadTable.TIME to mNotepad.NTime,
                        NotePadTable.ADDRESS to mNotepad.NAddress,
                        NotePadTable.CONTENT to mNotepad.NContent)
            }
            finish()
        })

        //弹出选择框
        vCustomViewDialog?.show()
    }
}