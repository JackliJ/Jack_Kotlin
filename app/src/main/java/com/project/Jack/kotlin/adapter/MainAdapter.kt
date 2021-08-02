package com.project.Jack.kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.Jack.kotlin.R
import com.project.Jack.kotlin.extension.database
import com.project.Jack.kotlin.model.Notepad
import com.project.Jack.kotlin.sqldata.NotePadTable
import com.project.Jack.kotlin.util.MyOnItemClick
import org.jetbrains.anko.db.delete

/**
 * 主页数据适配器
 * Create by www.lijin@foxmail.com on 2018/5/4 0004.
 * <br/>
 *
 */
class MainAdapter constructor(context: Context, mdata: MutableList<Notepad>) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    //上下文
    private var mContext: Context? = null
    //数据实体
    private var mData: MutableList<Notepad>? = null

    private var mListener: MyOnItemClick? = null

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
                false),mListener!!)
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    fun setOnItemClickListener(listener: MyOnItemClick) {
        this.mListener = listener
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mTitle.text = mData?.get(position)?.NTiTle
        holder.mTime.text = mData?.get(position)?.NTime
        holder.mNote.text = mData?.get(position)?.NAddress
        holder.mItem.setOnClickListener({v ->
            if(mListener != null){
                mListener?.onItemClick(v,position)
            }
        })
        //侧滑删除
        holder.mTDelete.text = mContext?.resources?.getString(R.string.delete)
        holder.mSDelete.setOnClickListener({ v ->
            if (holder.mTDelete.text.toString().equals(mContext?.resources?.getString(R.string.delete_ok))) {
                //移除数据库
                var time = mData?.get(position)?.NID
                mContext?.database?.use {
                    delete(NotePadTable.TABLE_NAME, "_id = $time")
                }
                mData?.removeAt(holder.adapterPosition)
                if (mData?.size == 1) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRemoved(holder.adapterPosition)
                    notifyItemRangeChanged(holder.adapterPosition, mData?.size!!)
                }
            } else {
                holder.mTDelete.text = mContext?.resources?.getString(R.string.delete_ok)
            }
        })
    }


    class MyViewHolder : RecyclerView.ViewHolder {
        var mTitle: TextView
        var mTime: TextView
        var mNote: TextView
        var mSDelete: LinearLayout
        var mTDelete: TextView
        var mItem : LinearLayout

        var mListener: MyOnItemClick? = null

        constructor(view: View, mListener: MyOnItemClick) : super(view) {
            mTitle = view.findViewById(R.id.adapter_tv_title)
            mTime = view.findViewById(R.id.adapter_tv_time)
            mNote = view.findViewById(R.id.adapter_tv_note)
            mSDelete = view.findViewById(R.id.chat_del)
            mTDelete = view.findViewById(R.id.chat_message_delete_tv)
            mItem = view.findViewById(R.id.lin_adapter_item)
            this.mListener = mListener
        }

    }

}