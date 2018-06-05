package com.project.Jack.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.project.Jack.kotlin.adapter.MainAdapter
import com.project.Jack.kotlin.extension.database
import com.project.Jack.kotlin.extension.parseList
import com.project.Jack.kotlin.model.Notepad
import com.project.Jack.kotlin.sqldata.Company
import com.project.Jack.kotlin.sqldata.NotePadTable
import com.project.Jack.kotlin.ui.NoteEditorActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.db.select
import java.util.HashMap

/**
 * 首页
 */
class MainActivity : Activity(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    //RecyclerView
    private var vRecyclerView: RecyclerView? = null
    //下拉刷新
    private var vSwipeRefresh: SwipeRefreshLayout? = null
    //新建备忘按钮
    private var vImgEdit: ImageView? = null
    //上下文
    private var mContext: Context? = null
    //LinearLayoutManager
    private var linearLayoutManager: LinearLayoutManager? = null
    //数据构造器
    private var adapter: MainAdapter? = null
    //数据源
    private  var mistus: MutableList<Notepad>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        initView()
    }

    /**
     * 初始化组件
     */
    fun initView() {
        //赋值组件
        vRecyclerView = main_rv
        vImgEdit = main_img_editor
        vImgEdit?.setOnClickListener(this)
        //设置数据加载方式
        linearLayoutManager = LinearLayoutManager(mContext)
        vRecyclerView?.layoutManager = linearLayoutManager
        //查询数据库数据
        mistus = selectData()
        //填充数据
        adapter = MainAdapter(mContext!!, mistus!!)
        vRecyclerView?.adapter = adapter
        //设置备忘录数据数量
        if (mistus != null) {
            var mFormat: String? = String.format(resources.getString(R.string.Memorandum_number), mistus?.size)
            main_tv_mum.text = mFormat
        } else {
            var mFormat: String? = String.format(resources.getString(R.string.Memorandum_number), 0)
            main_tv_mum.text = mFormat
        }
        vSwipeRefresh = main_swipe
        //为SwipeRefreshLayout设置监听事件
        vSwipeRefresh?.setOnRefreshListener(this)
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        vSwipeRefresh?.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    /**
     * 下拉刷新模块
     */
    override fun onRefresh() {
        //去查询数据库
        if(mistus != null){
            mistus!!.clear()
            var s = selectData()
            s?.let { mistus!!.addAll(it) }
        }else{
            var s = selectData()
            s?.let { mistus!!.addAll(it) }
        }
        //刷新数据
        adapter!!.notifyDataSetChanged()
        //重新赋值底部数量状态
        if (mistus != null) {
            var mFormat: String? = String.format(resources.getString(R.string.Memorandum_number), mistus!!.size)
            main_tv_mum.text = mFormat
        } else {
            var mFormat: String? = String.format(resources.getString(R.string.Memorandum_number), 0)
            main_tv_mum.text = mFormat
        }
        //关闭刷新状态
        if(vSwipeRefresh != null){
            vSwipeRefresh!!.isRefreshing = false
        }
    }

    /**
     * 查询数据库数据
     */
    fun selectData(): MutableList<Notepad>? {
        var ds: MutableList<Notepad>? = ArrayList()
        database.use {
            val dsp = select(NotePadTable.TABLE_NAME).parseList { Company(HashMap(it)) }
            async() {
                for (index in 0..(dsp!!.size - 1)) {
                    var sde: Notepad? = Notepad()
                    var i = index
                    sde?.NTiTle = dsp[i].title
                    sde?.NName = dsp[i].name
                    sde?.NAddress = dsp[i].address
                    sde?.NTime = dsp[i].time
                    sde?.NContent = dsp[i].content
                    ds?.add(sde!!)

                }
            }
        }
        return ds
    }

    //全局点击事件
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.main_img_editor -> {
                startIntent()
//                var mistus: MutableList<Notepad>? = selectData()
////                for (index in 0..(mistus!!.size - 1)){
////                    Toast.makeText(mContext,mistus[index].NContent,Toast.LENGTH_SHORT).show()
////                }
//                mistus?.forEachIndexed { index, notepad
//                    ->Toast.makeText(mContext,notepad.NContent,Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }

    fun startIntent() {
        val intents = Intent()
        intents.setClass(this, NoteEditorActivity::class.java)
        startActivity(intents)
    }
}
