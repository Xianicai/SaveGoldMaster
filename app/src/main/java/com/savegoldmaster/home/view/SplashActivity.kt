package com.savegoldmaster.home.view

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPActivity
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.presenter.AppStartPresenterImpl
import com.savegoldmaster.home.presenter.Contract.AppStartContract
import com.savegoldmaster.push.HMSAgent
import com.savegoldmaster.push.handler.GetTokenHandler
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.StringUtil
import kotlinx.android.synthetic.main.activity_start_app.*
import kotlinx.android.synthetic.main.layout_app_guide.*
import java.util.*




class SplashActivity : BaseMVPActivity<AppStartPresenterImpl>(), AppStartContract.AppStartView, View.OnClickListener {


    private var adImageUrl: String? = null
    private var url: String? = null
    private var countDownTimer: CountDownTimer? = null
    private var imgResArr =
        intArrayOf(R.mipmap.pg_guide_one, R.mipmap.pg_guide_two, R.mipmap.pg_guide_there)
    private val viewList = ArrayList<View>()
    private val indicatorImgs = ArrayList<ImageView>()
    private var firstOpened: Boolean = false

    private var presenter: AppStartPresenterImpl? = null


    override fun getLayoutId(): Int {
        SharedPreferencesHelper(this, "AdBean").apply {
            adImageUrl = getSharedPreference("adImageUrl", "") as String?
            url = getSharedPreference("url", "") as String?
            firstOpened = getSharedPreference("firstOpened", false) as Boolean
        }
        return if (firstOpened) {
            R.layout.activity_start_app
        } else {
            R.layout.layout_app_guide
        }

    }

    override fun createPresenter(): AppStartPresenterImpl {
        presenter = AppStartPresenterImpl()
        return presenter!!
    }

    override fun initViews(savedInstanceState: Bundle?) {
        connectHuaWei()
        getToken()
//        initWindows()
        presenter?.getAppAd(6, 1, 2)
        if (firstOpened) {
            if (StringUtil.isNotEmpty(adImageUrl)) {
                mImageAd.visibility = View.VISIBLE
                mViewNext.visibility = View.VISIBLE
                mImageAd.setImage(adImageUrl)
                setAdTime()
            } else {
                mImageAd.visibility = View.GONE
                mViewNext.visibility = View.GONE
                MainActivity.start(this@SplashActivity)
                finish()
            }

            mViewNext.setOnClickListener(this)
            mImageAd.setOnClickListener(this)
        } else {
            SharedPreferencesHelper(this@SplashActivity, "AdBean").apply {
                put("firstOpened", true)
            }
            finshGuide.setOnClickListener(this)
            initGuideData()
            initViewPager()
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            mViewNext -> {
                MainActivity.start(this@SplashActivity)
                finish()
            }
            mImageAd -> {
                if (StringUtil.isNotEmpty(url)) {
                }
            }
            finshGuide -> {
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }
    }

    override fun getAppAd(adBean: BannerBean) {
        if (adBean.content.isNotEmpty()) {
            SharedPreferencesHelper(this@SplashActivity, "AdBean").apply {
                put("adImageUrl", adBean.content[0].imgUrl)
                put("url", adBean.content[0].hrefUrl)
            }
        }
    }

    private fun setAdTime() {
        countDownTimer = object : CountDownTimer(4000, 1000) {
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
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }.start()
    }

    fun initGuideData() {
        for (i in imgResArr.indices) {
            val view = LayoutInflater.from(this).inflate(R.layout.guide_view_item, null)
            view.setBackgroundResource(R.color.white)
            (view.findViewById<View>(R.id.guide_image) as ImageView).setBackgroundResource(imgResArr[i])
            viewList.add(view)
            indicatorImgs.add(ImageView(this))
            if (i == 0) {
                indicatorImgs[i].setBackgroundResource(R.drawable.banner_select)
            } else {
                indicatorImgs[i].setBackgroundResource(R.drawable.banner_normal)
                val layoutParams = LinearLayout.LayoutParams(-2, -2)
                layoutParams.setMargins(20, 0, 0, 0)
                indicatorImgs[i].layoutParams = layoutParams
            }
            indicator.addView(indicatorImgs[i])
        }
    }


    fun initViewPager() {
        viewpager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return viewList.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(viewList[position])
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                container.addView(viewList[position])
                return viewList[position]
            }
        }
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position == imgResArr.size - 1) {
                    MainActivity.start(this@SplashActivity)
                    finish()
                }

            }

            override fun onPageSelected(position: Int) {
                setIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    fun setIndicator(targetIndex: Int) {
        for (i in indicatorImgs.indices) {
            indicatorImgs[i].setBackgroundResource(R.drawable.banner_select)
            if (targetIndex != i) {
                indicatorImgs[i].setBackgroundResource(R.drawable.banner_normal)
            }
        }
    }

    override fun onDestroy() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        super.onDestroy()
    }

    override fun initWindow() {
        super.initWindow()
        hideBottomUIMenu()

    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }

    /*
* 连接华为服务器
* */
    fun connectHuaWei() {
        HMSAgent.connect(this) {
        }
    }


    private fun getToken() {
        HMSAgent.Push.getToken(object : GetTokenHandler {
            override fun onResult(rst: Int) {
            }
        })
    }
}
