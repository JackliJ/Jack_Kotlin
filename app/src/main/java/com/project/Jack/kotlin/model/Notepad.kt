package com.project.Jack.kotlin.model

/**
 * Create by www.lijin@foxmail.com on 2018/5/4 0004.
 * <br/>
 * 文本标题
 */
class Notepad{
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }



    constructor(NTiTle: String, NName: String, NTime: String, NAddress: String, NContent: String) {
        this.NTiTle = NTiTle
        this.NName = NName
        this.NTime = NTime
        this.NAddress = NAddress
        this.NContent = NContent
    }

    constructor()



    //文本标题
    var NTiTle : String = ""
    //创建者名称
    var NName : String = ""
    //创建时间
    var NTime : String = ""
    //创建地址
    var NAddress : String = ""
    //文本内容
    var NContent : String = "";

}