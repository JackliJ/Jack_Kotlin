package com.project.Jack.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.Jack.kotlin.adapter.MainAdapter
import com.project.Jack.kotlin.extension.database
import com.project.Jack.kotlin.extension.parseList
import com.project.Jack.kotlin.model.Notepad
import com.project.Jack.kotlin.sqldata.Company
import com.project.Jack.kotlin.sqldata.NotePadTable
import com.project.Jack.kotlin.ui.NoteEditorActivity
import com.project.Jack.kotlin.util.MyOnItemClick
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.db.select
import java.util.HashMap

/**
 * 首页
 */
class MainActivity : Activity(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,MyOnItemClick {

    //RecyclerView
    private var vRecyclerView: RecyclerView? = null
    //下拉刷新
    private var vSwipeRefresh: SwipeRefreshLayout? = null
    //新建备忘按钮
    private var vImgEdit: LinearLayout? = null
    private var vImgShare : LinearLayout? = null
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
    private fun initView() {
        //赋值组件
        vRecyclerView = main_rv
        vImgEdit = main_img_editor
        vImgShare = main_img_home
        vImgEdit?.setOnClickListener(this)
        vImgShare?.setOnClickListener(this)
        //设置数据加载方式
        linearLayoutManager = LinearLayoutManager(mContext)
        vRecyclerView?.layoutManager = linearLayoutManager
        //查询数据库数据
        mistus = selectData()
        //填充数据
        adapter = MainAdapter(mContext!!, mistus!!)
        adapter?.setOnItemClickListener(this)
        vRecyclerView?.adapter = adapter
        vSwipeRefresh = main_swipe
        //为SwipeRefreshLayout设置监听事件
        vSwipeRefresh?.setOnRefreshListener(this)
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        vSwipeRefresh?.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        //设置备忘录数据数量
        if (mistus != null) {
            var mFormat: String? = String.format(resources.getString(R.string.Memorandum_number), mistus?.size)
            main_tv_mum.text = mFormat
        } else {
            var mFormat: String? = String.format(resources.getString(R.string.Memorandum_number), 0)
            main_tv_mum.text = mFormat
        }
    }

    //点击事件
    override fun onItemClick(view: View, postion: Int) {
        //携带数据跳转到编辑页面
        val intents = Intent()
        var b = Bundle()
        b.putSerializable("notepad",mistus?.get(postion))
        b.putInt("type",1)
        intents.putExtra("bundle",b)
        intents.setClass(this, NoteEditorActivity::class.java)
        startActivity(intents)
    }

    //长按事件
    override fun onLongClick(view: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
    private fun selectData(): MutableList<Notepad>? {
        var ds: MutableList<Notepad>? = ArrayList()
        database.use {
            val dsp = select(NotePadTable.TABLE_NAME).parseList { Company(HashMap(it)) }
            async() {
                for (index in 0..(dsp!!.size - 1)) {
                    var sde: Notepad? = Notepad()
                    var i = index
                    sde?.NID = dsp[i]._id.toString()
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
            R.id.main_img_home ->{
                Toast.makeText(this,"功能暂时未开放",Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 跳转到编辑页面
     */
    fun startIntent() {
        val intents = Intent()
        var b = Bundle()
        b.putInt("type",0)
        intents.putExtra("bundle",b)
        intents.setClass(this, NoteEditorActivity::class.java)
        startActivity(intents)
    }
}
