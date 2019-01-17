package com.savegoldmaster.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.account.LoginActivity
import com.savegoldmaster.base.BaseApplication
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.home.presenter.UserPresenterImpl
import com.savegoldmaster.home.view.adapter.MineListAdapter
import com.savegoldmaster.home.view.adapter.MineListBean
import com.savegoldmaster.utils.LocationUtils
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.adapter.OnItemClickListener
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import com.tencent.bugly.crashreport.CrashReport
import io.objectbox.Box
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_mine.view.*

class MineFragment : BaseMVPFragment<UserPresenterImpl>(), UserContract.UserView, View.OnClickListener {
    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    private val mTitles = arrayOf("福利券", "交易记录", "银行卡管理", "地址管理", "常见问题", "在线客服", "关于我们")
    private val mImages = arrayOf(
        R.mipmap.ic_mine_coupon, R.mipmap.ic_mine_deal_order, R.mipmap.ic_mine_bank_card,
        R.mipmap.ic_mine_address, R.mipmap.ic_mine_questions, R.mipmap.ic_mine_service, R.mipmap.ic_mine_about
    )

    private var userPresenterImpl: UserPresenterImpl? = null
    private var token: String = ""
    private var userId: String = ""
    private var mBox: Box<UserBean>? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        SharedPreferencesHelper(context, "UserBean").run {
            token = getSharedPreference("token", "").toString().trim()
            userId = getSharedPreference("userId", "").toString().trim()
        }
        mButtonLogin.setOnClickListener(this)
        mTvServiceTel.setOnClickListener(this)
        mTvRealName.setOnClickListener(this)
        mImageMsg.setOnClickListener(this)
        initRecyclerView()
//        mBox = BaseApplication.boxStore?.boxFor(UserBean::class.java)
        if (userId.isEmpty()) {
            mLayoutLogin.visibility = View.VISIBLE
            mImageLogin.visibility = View.VISIBLE
            mLayoutUserView.visibility = View.GONE
        } else {
            initData()
        }
        addEvent()

    }

    private fun initRecyclerView() {
        mLayoutRecycleOrder.setOnClickListener(this)
        var listBean: MutableList<MineListBean> = arrayListOf()
        for (i in 0 until mTitles.size) {
            listBean.add(MineListBean(mTitles[i], mImages[i], mImages[i]))
        }
        mRecyclerView.layoutManager = GridLayoutManager(context, 4)
        mRecyclerView.adapter = MineListAdapter(context!!, listBean as ArrayList<MineListBean>).apply {
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> {
                        }
                        1 -> {
                        }
                        2 -> {
                        }
                        3 -> {
                        }
                        4 -> {
                        }
                        5 -> {
                        }
                        6 -> {
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        if (userId.isNotEmpty() && token.isNotEmpty()) {
            userPresenterImpl?.getUserDetail()
        }

    }

    @SuppressLint("CheckResult")
    private fun addEvent() {
        RxBus.getDefault().toObservable(RxEvent::class.java)
            .subscribe { t ->
                if (t?.eventType == EventConstant.GET_USER_DETAIL) {
                    SharedPreferencesHelper(context, "UserBean").run {
                        token = getSharedPreference("token", "").toString().trim()
                        userId = getSharedPreference("userId", "").toString().trim()
                    }
                    initData()
                }
            }
    }


    override fun createPresenter(): UserPresenterImpl {
        userPresenterImpl = UserPresenterImpl()
        return userPresenterImpl as UserPresenterImpl

    }

    override fun onClick(v: View?) {
        when (v) {
            mButtonLogin -> {
                LoginActivity.start(activity!!)
            }
            mTvServiceTel -> {

                startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:" + 4008196199)
                    ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
            mLayoutRecycleOrder -> {
                //我的订单
            }
            mTvRealName -> {
                //实名认证
            }
            mImageMsg -> {
                //消息
            }
        }
    }

    override fun getUserDetail(userBean: UserBean) {
        //bugly上报userId
        CrashReport.setUserId(userBean.content.telephone)
        SharedPreferencesHelper(context, "UserBean").apply {
            put("telephone", userBean.content.telephone)
        }
        mLayoutLogin.visibility = View.GONE
        mImageLogin.visibility = View.GONE
        mLayoutUserView.visibility = View.VISIBLE
        mImageHead.setRoundedImage(userBean.content.avatar)
        if (userBean.content.name != null && userBean.content.name.isNotEmpty()) {
            mTvRealName.apply {
                text = userBean.content.name
                visibility = View.VISIBLE
            }
            mTvSetName.visibility = View.GONE
        } else {
            mTvRealName.visibility = View.GONE
            mTvSetName.visibility = View.VISIBLE
        }
        mTvPhoneNum.apply {
            visibility = View.VISIBLE
            text = userBean.content.userName
        }
        if (userBean.content.recycleOrderTBC > 0) {
            var text = "你有${userBean.content.recycleOrderTBC}笔订单待确认"
            mTvOrder.text = SpannableStringBuilder(text).apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.color_EDA835)),
                    2,
                    text.length - 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            mTvBalance.text = userBean.content.cashBalanceStr

        }
    }
}