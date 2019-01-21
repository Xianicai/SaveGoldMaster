package com.savegoldmaster.utils.view

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.savegoldmaster.R
import com.savegoldmaster.R.id.viewpager
import kotlinx.android.synthetic.main.guide_view_item.view.*
import kotlinx.android.synthetic.main.layout_app_guide.view.*
import java.util.ArrayList

class GuildView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr) {
    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private var layoutAppGuide: RelativeLayout? = null
    private val viewList = ArrayList<View>()
    private val indicatorImgs = ArrayList<ImageView>()
    private var imgResArr =
        intArrayOf(R.mipmap.ic_360, R.mipmap.ic_bocuishan, R.mipmap.ic_lanchi, R.mipmap.ic_zhongxing)



    fun initData() {
        layoutAppGuide = LayoutInflater.from(context).inflate(R.layout.layout_app_guide, this) as RelativeLayout?

        for (i in imgResArr.indices) {
            val view = LayoutInflater.from(context).inflate(R.layout.guide_view_item, this)
            view.setBackgroundResource(R.color.white)
            (view.findViewById<View>(R.id.guide_image) as ImageView).setBackgroundResource(imgResArr[i])
            viewList.add(view)
            indicatorImgs.add(ImageView(context))
            if (i == 0) {
                indicatorImgs[i].setBackgroundResource(R.drawable.banner_select)
            } else {
                indicatorImgs[i].setBackgroundResource(R.drawable.banner_normal)
                val layoutParams = LinearLayout.LayoutParams(-2, -2)
                layoutParams.setMargins(20, 0, 0, 0)
                indicatorImgs[i].layoutParams = layoutParams
            }
            layoutAppGuide?.indicator?.addView(indicatorImgs[i])
        }
    }

    fun initView() {
        layoutAppGuide?.viewpager?.adapter = object : PagerAdapter() {
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
        layoutAppGuide?.viewpager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

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

    fun setImages(imgResArr: IntArray) {
        this.imgResArr = imgResArr
        initData()
        initView()
    }
}