package com.savegoldmaster.base

import android.app.Application
import com.elvishew.xlog.BuildConfig
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog


/**
 * ZY:
 * Created by zhanglibin on 2018/9/2.
 */
class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
    }

    private fun initLog() {
        XLog.init(LogLevel.ALL, LogConfiguration.Builder().b().build())
    }
}