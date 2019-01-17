package com.savegoldmaster.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.webkit.DownloadListener
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.zhanglibin.savegoldmaster.R
import com.example.zhanglibin.savegoldmaster.R.id.tv
import com.savegoldmaster.base.BaseApplication
import com.savegoldmaster.home.view.MainActivity
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.download.DownloadTask
import com.tencent.bugly.beta.ui.UILifecycleListener
import com.tencent.bugly.crashreport.CrashReport

class BuglyUtils {
    companion object {
        fun initBugly(context: Context, appId: String, debug: Boolean) {
//            Bugly.init(BaseApplication.instance, "da0f8f9fef", true)

            Beta.canShowUpgradeActs.add(MainActivity::class.java)
            Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
            Beta.strUpgradeDialogInstallBtn = "立即更新"
            Beta.strUpgradeDialogCancelBtn = ""

            val telephone =
                SharedPreferencesHelper(context, "UserBean").getSharedPreference("telephone", "").toString().trim()
            CrashReport.setUserId(telephone)
            addUpgradeDialogListener()
            Bugly.init(context, appId, debug)

        }

        /**
         * 设置升级对话框生命周期回调接口
         */
        fun addUpgradeDialogListener() {

            Beta.upgradeDialogLifecycleListener = object : UILifecycleListener<UpgradeInfo> {


                override fun onCreate(context: Context, view: View, upgradeInfo: UpgradeInfo) {


                val  mProgressBar = view.findViewById(R.id. mProgressBar) as ProgressBar
//                if (info != null) {
//                    info.visibility = if (Beta.canShowApkInfo) View.VISIBLE else View.GONE
//                }
//                val parent = view.findViewById(R.id.layout_parent)
//                parent.setOnClickListener(View.OnClickListener { view -> onDestroy(context, view, upgradeInfo) })
//                // 初始化Dialog的宽度，屏幕的6/7
//                val dialogWidth = Mobile.SCREEN_WIDTH * 6 / 7
//                val layout = view.findViewById(R.id.layout_upgrade_contain) as RelativeLayout
//                val params = RelativeLayout.LayoutParams(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
//                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
//                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
//                layout.layoutParams = params
                    /*注册下载监听，监听下载事件*/
                    Beta.registerDownloadListener(object : com.tencent.bugly.beta.download.DownloadListener {
                        override fun onFailed(p0: DownloadTask?, p1: Int, p2: String?) {
                            ToastUtil.showMessage("下载失败")
                        }

                        override fun onReceive(p0: DownloadTask?) {
                            ToastUtil.showMessage("下载onReceive")

                        }

                        override fun onCompleted(p0: DownloadTask?) {
                            mProgressBar.progress = p0?.savedLength?.toInt()!!
                        }
                    })
                }

                override fun onResume(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onPause(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onStart(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onStop(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }

                override fun onDestroy(p0: Context?, p1: View?, p2: UpgradeInfo?) {
                }
            }



        }
    }

}