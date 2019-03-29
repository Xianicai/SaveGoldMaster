package com.savegoldmaster.base

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.savegoldmaster.push.HMSAgent
import com.savegoldmaster.utils.view.BuglyUtils
import io.objectbox.BoxStore


/**
 * ZY:
 * Created by zhanglibin on 2018/9/2.
 */
class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
        var boxStore: BoxStore? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
        initObjectBox()
        initBugly()
        if ("HUAWEI" == android.os.Build.MANUFACTURER) {
            initHuaWei()
        } else {
            initJPush()
        }

    }

    private fun initHuaWei() {
        HMSAgent.init(this)
    }


    private fun initBugly() {
        BuglyUtils.initBugly(instance!!, "da0f8f9fef", true)

    }

    private fun initLog() {
        XLog.init(LogLevel.ALL, LogConfiguration.Builder().b().build())
    }

    private fun initObjectBox() {
//        boxStore = MyObjectBox.builder().androidContext(instance).build()
//        if (BuildConfig.DEBUG) {
//            boxStore?.let {
//                //可以理解为初始化连接浏览器(可以在浏览器中查看数据，下面再说)
//                val started = AndroidObjectBrowser(boxStore).start(this)
//            }
//        }
    }

    private fun initJPush() {
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
    }
}