package com.savegoldmaster.base

import android.app.Application
import android.webkit.DownloadListener
import com.elvishew.xlog.LogConfiguration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.example.zhanglibin.savegoldmaster.R
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import io.objectbox.BoxStore
import com.savegoldmaster.home.view.MainActivity
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.tencent.bugly.beta.download.DownloadTask
import com.tencent.bugly.crashreport.CrashReport
import com.example.zhanglibin.savegoldmaster.R.id.tv
import com.savegoldmaster.utils.BuglyUtils


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
        initBugly()
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
}