package com.savegoldmaster.home.view

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import com.elvishew.xlog.XLog
import com.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPActivity
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.presenter.AppStartPresenterImpl
import com.savegoldmaster.home.presenter.Contract.AppStartContract
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.StringUtil
import kotlinx.android.synthetic.main.activity_start_app.*
import kotlinx.android.synthetic.main.layout_app_guide.*
import java.util.ArrayList

class SplashActivity : BaseMVPActivity<AppStartPresenterImpl>(), AppStartContract.AppStartView, View.OnClickListener {


    private var adImageUrl: String? = null
    private var url: String? = null
    private var countDownTimer: CountDownTimer? = null
    private var imgResArr =
        intArrayOf(R.mipmap.ic_360, R.mipmap.ic_bocuishan, R.mipmap.ic_lanchi, R.mipmap.ic_zhongxing)
    private val viewList = ArrayList<View>()
    private val indicatorImgs = ArrayList<ImageView>()
    private var firstOpened: Boolean = false

    private var presenter: AppStartPresenterImpl? = null
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

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
        initWindows()
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

    private fun initWindows() {
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val height = wm.defaultDisplay.height
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        if (firstOpened) {
            val layoutParams = mParent.layoutParams
            layoutParams.height = height
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            mParent.layoutParams = layoutParams
        } else {
            val layoutParams = mParentV2.layoutParams
            layoutParams.height = height
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            mParentV2.layoutParams = layoutParams

        }

    }
}
