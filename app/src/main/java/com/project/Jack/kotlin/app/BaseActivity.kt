package com.project.Jack.kotlin.app

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

/**
 *
 * @ProjectName:    Jack_Kotlin
 * @Package:        com.project.Jack.kotlin.app
 * @ClassName:      BaseActivity
 * @Description:     MVVM Base Activity 基类
 * @Author:         www.lijin@foxmail.com
 * @CreateDate:     2021/7/27 8:03 PM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2021/7/27 8:03 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {
}