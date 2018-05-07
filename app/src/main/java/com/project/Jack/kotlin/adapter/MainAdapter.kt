package com.project.Jack.kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.project.Jack.kotlin.R
import com.project.Jack.kotlin.model.Notepad

/**
 * 主页数据适配器
 * Create by www.lijin@foxmail.com on 2018/5/4 0004.
 * <br/>
 *
 */
class MainAdapter constructor(context:Context,mdata:List<Notepad>) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    //上下文
    private var mContext:Context? = null
    //数据实体
    private var mData:List<Notepad>? = null

    init {
        //初始化
        this.mContext = context
        this.mData = mdata
    }

    override fun getItemCount(): Int {
        return mData?.size as Int
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.main_adapter_layout, parent,
                false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mNote.text = mData?.get(position)?.NTiTle
        holder.mTime.text = mData?.get(position)?.NTime
        holder.mNote.text = mData?.get(position)?.NAddress
    }



    class MyViewHolder : RecyclerView.ViewHolder{
        var mTitle : TextView
        var mTime : TextView
        var mNote : TextView

        constructor(view: View) : super(view) {
            mTitle = view.findViewById(R.id.adapter_tv_title)
            mTime = view.findViewById(R.id.adapter_tv_time)
            mNote = view.findViewById(R.id.adapter_tv_note)
        }

    }

}