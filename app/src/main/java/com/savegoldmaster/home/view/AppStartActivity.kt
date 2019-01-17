package com.savegoldmaster.home.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.base.BaseApplication
import com.savegoldmaster.base.view.BaseMVPActivity
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.presenter.AppStartPresenterImpl
import com.savegoldmaster.home.presenter.Contract.AppStartContract
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.StringUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_start_app.*

class AppStartActivity : BaseMVPActivity<AppStartPresenterImpl>(), AppStartContract.AppStartView, View.OnClickListener {


    private var adImageUrl: String? = null
    private var url: String? = null
    private var start: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesHelper(BaseApplication.instance!!, "UserBean").apply {
            getSharedPreference("adImageUrl", adImageUrl)
            getSharedPreference("url", url)
        }
    }

    private var presenter: AppStartPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_start_app

    }

    override fun createPresenter(): AppStartPresenterImpl {
        presenter = AppStartPresenterImpl()
        return presenter!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        if (StringUtil.isNotEmpty(adImageUrl)) {
            mImageAd.visibility = View.VISIBLE
            mViewNext.visibility = View.VISIBLE
            mImageAd.setImage(adImageUrl)
            setAdTime()
        } else {
            mImageAd.visibility = View.GONE
            mViewNext.visibility = View.GONE
            MainActivity.start(this@AppStartActivity)
            finish()
        }

        mViewNext.setOnClickListener(this)
        mImageAd.setOnClickListener(this)
        presenter?.getAppAd(6, 1, 2)
    }

    override fun onClick(v: View?) {
        when (v) {
            mViewNext -> {
                MainActivity.start(this@AppStartActivity)
                finish()
            }
            mImageAd -> {
                if (StringUtil.isNotEmpty(url)) {
                }
            }
        }
    }

    override fun getAppAd(adBean: BannerBean) {
        if (adBean.content.isNotEmpty()) {
            SharedPreferencesHelper(this@AppStartActivity, "AdBean").apply {
                put("adImageUrl", adBean.content[0].imgUrl)
                put("url", adBean.content[0].hrefUrl)
            }
        }
    }

    private fun setAdTime() {
        start = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mViewNext.run {
                    visibility = View.VISIBLE
                    setText("跳过 ${millisUntilFinished / 1000}s")
                }
            }

            override fun onFinish() {
                mViewNext.run {
                    setText("跳过 0s")
                }
                MainActivity.start(this@AppStartActivity)
                finish()
            }
        }.start()
    }

    override fun onDestroy() {
        start = null
        super.onDestroy()
    }
}
