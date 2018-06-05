package com.project.Jack.kotlin.app

import android.app.Application
import com.project.Jack.kotlin.sqldata.DatabaseOpenHelper
import kotlin.properties.Delegates

/**
 * Create by www.lijin@foxmail.com on 2018/5/7 0007.
 * <br/>
 *  全局的BaseAppliciton
 */
class BaseAppliciton : Application(){

    //单例模式
    companion object {
        var instance: BaseAppliciton by Delegates.notNull()
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
    }


}