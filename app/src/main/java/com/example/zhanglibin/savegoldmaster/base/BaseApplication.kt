package com.example.zhanglibin.savegoldmaster.base

import android.app.Application
import com.orhanobut.logger.*

/**
 * ZY:
 * Created by zhanglibin on 2018/9/2.
 */
class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLog()
    }

    private fun initLog() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)  //（可选）是否显示线程信息。 默认值为true
            .methodCount(1)         // （可选）要显示的方法行数。 默认2
            .methodOffset(5)        // （可选）隐藏内部方法调用到偏移量。 默认5
            .tag("doShare")//（可选）每个日志的全局标记。 默认PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object :AndroidLogAdapter(formatStrategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true
            }
        })//根据上面的格式设置logger相应的适配器
        Logger.addLogAdapter(DiskLogAdapter())//保存到文件
    }
}