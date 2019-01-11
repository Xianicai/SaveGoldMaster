package com.savegoldmaster.base

import android.app.Application
import com.elvishew.xlog.BuildConfig
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser


/**
 * ZY:
 * Created by zhanglibin on 2018/9/2.
 */
class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
        var boxStore: BoxStore? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
        initObjectBox()
    }

    private fun initLog() {
        XLog.init(LogLevel.ALL, LogConfiguration.Builder().b().build())
    }

    private fun initObjectBox() {
        boxStore = MyObjectBox.builder().androidContext(instance).build()
        if (BuildConfig.DEBUG) {
            boxStore?.let {
                //可以理解为初始化连接浏览器(可以在浏览器中查看数据，下面再说)
                val started = AndroidObjectBrowser(boxStore).start(this)
            }
        }
    }
}