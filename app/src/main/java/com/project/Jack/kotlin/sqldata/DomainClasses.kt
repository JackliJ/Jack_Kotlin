package com.project.Jack.kotlin.sqldata

/**
 * Create by www.lijin@foxmail.com on 2018/5/7 0007.
 * <br/>
 *
 */
data class Company(val map: MutableMap<String, Any?>) {
    var _id: Long by map
    var name: String by map
    var address: String by map
    var time: String by map
    var content: String by map
    var title: String by map

    constructor() : this(HashMap()) {

    }

    constructor(id:Long,name: String,address:String,time:String,content:String,title :String) : this(HashMap()) {
        this._id = id
        this.name = name
        this.address = address
        this.time = time
        this.content = content
        this.title = title
    }

}