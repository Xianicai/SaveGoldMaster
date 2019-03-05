package com.savegoldmaster.home.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.savegoldmaster.R
import com.savegoldmaster.account.LoginActivity
import com.savegoldmaster.account.UserUtil
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.common.WebUrls
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.home.presenter.UserPresenterImpl
import com.savegoldmaster.home.view.adapter.MineListAdapter
import com.savegoldmaster.home.view.adapter.MineListBean
import com.savegoldmaster.utils.ConfirmDialog
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.adapter.OnItemClickListener
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import com.savegoldmaster.utils.webutil.OutWebActivity
import com.tencent.bugly.crashreport.CrashReport
import io.objectbox.Box
import kotlinx.android.synthetic.main.fragment_mine.*

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
    private var dialog: ConfirmDialog? = null
    private var userBean: UserBean? = null
    private var loginType = LoginActivity.ACCOUNT_LOGIN
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        addMsgEvent()
        SharedPreferencesHelper(context, "UserBean").run {
            token = getSharedPreference("token", "").toString().trim()
            userId = getSharedPreference("userId", "").toString().trim()
        }
        mButtonLogin.setOnClickListener(this)
        mTvServiceTel.setOnClickListener(this)
        mTvRealName.setOnClickListener(this)
        mImageMsg.setOnClickListener(this)
        mTvTopUp.setOnClickListener(this)
        mTvWithdrawal.setOnClickListener(this)
        mTvSetName.setOnClickListener(this)
        mImageHead.setOnClickListener(this)
        mTvBalance.setOnClickListener(this)
        initRecyclerView()
//        mBox = BaseApplication.boxStore?.boxFor(UserBean::class.java)
        if (userId.isEmpty()) {
            mLayoutLogin.visibility = View.VISIBLE
            mImageLogin.visibility = View.VISIBLE
            mLayoutUserView.visibility = View.GONE
        } else {
            initData()
        }

    }

    private fun initRecyclerView() {
        mLayoutRecycleOrder.setOnClickListener(this)
        var listBean: MutableList<MineListBean> = arrayListOf()
        for (i in 0 until mTitles.size) {
            listBean.add(MineListBean(mTitles[i], mImages[i], mImages[i]))
        }
        mRecyclerView.layoutManager = GridLayoutManager(context, 4)
        mRecyclerView.adapter = MineListAdapter(context!!, listBean as ArrayList<MineListBean>).apply {
            setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    if (!UserUtil.isLogin()) {
                        LoginActivity.start(context!!)
                    } else {
                        when (position) {
                            0 -> {
                                OutWebActivity.start(context!!, WebUrls.COUPON)
                            }
                            1 -> {
                                OutWebActivity.start(context!!, WebUrls.DEALLIST)
                            }
                            2 -> {
                                OutWebActivity.start(context!!, WebUrls.MY_BANK)

                            }
                            3 -> {
                                OutWebActivity.start(context!!, WebUrls.ADDRESS)
                            }
                            4 -> {
                                OutWebActivity.start(context!!, WebUrls.QUESTION)
                            }
                            5 -> {
                                OutWebActivity.start(context!!, WebUrls.SERVICE)
                            }
                            6 -> {
                                OutWebActivity.start(context!!, WebUrls.ABOUTUS)
                            }
                        }
                    }
                }

            })

        }
    }

    private fun initData() {
        if (userId.isNotEmpty() && token.isNotEmpty()) {
            userPresenterImpl?.getUserDetail()
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
                //回收订单
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return@onClick
                }
                OutWebActivity.start(context!!, WebUrls.STORE_ORDER_LIST)
            }
            mTvSetName -> {
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return@onClick
                }
                //实名认证
                OutWebActivity.start(context!!, WebUrls.AUTHBANK)
            }
            mImageMsg -> {
                //消息
                if (UserUtil.isLogin()) {
                    OutWebActivity.start(context!!, WebUrls.MESSAGE_LIST)
                } else {
                    LoginActivity.start(context!!)
                }
            }
            mTvTopUp -> {
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return
                }
                if (userBean?.content?.isBoundBankCard == 0) {
                    showBankCardTips()
                } else {
                    OutWebActivity.start(context!!, WebUrls.RECHARGE)
                }

            }
            mTvWithdrawal -> {
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return
                }
                if (userBean?.content?.isBoundBankCard == 0) {
                    showBankCardTips()
                } else {
                    OutWebActivity.start(context!!, WebUrls.WITHDRAW)
                }
            }
            mTvSetName -> {
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return
                }
                OutWebActivity.start(context!!, WebUrls.AUTHBANK)
            }
            mImageHead -> {
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return
                }
                OutWebActivity.start(context!!, WebUrls.MINE)
            }
            mTvBalance -> {
                if (!UserUtil.isLogin()) {
                    LoginActivity.start(context!!)
                    return
                }
                    OutWebActivity.start(context!!, WebUrls.ACCOUNT)
            }
        }
    }

    override fun getUserDetail(userBean: UserBean) {
        this.userBean = userBean
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
            mTvOrder.visibility = View.VISIBLE
            mTvOrder.text = SpannableStringBuilder(text).apply {
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.color_EDA835)),
                    2,
                    text.length - 6,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        mTvBalance.text = userBean.content.cashBalanceStr
        if (userBean.content.isLoginPwd == 0 && loginType == LoginActivity.FASTER_LOGIN) {
            showTips()
        }
    }

    @SuppressLint("CheckResult")
    private fun addMsgEvent() {
        RxBus.getDefault().toObservable(RxEvent::class.java)
            .subscribe { t ->
                when {
                    t?.eventType == EventConstant.MINE_FRAGMENT_MSG -> {
                        if (t.`object` as Int > 0) {
                            mImageUnread?:return@subscribe
                            mImageUnread.visibility = View.VISIBLE
                        } else {
                            mImageUnread?:return@subscribe
                            mImageUnread.visibility = View.GONE
                        }
                        initData()
                    }
                    t?.eventType == EventConstant.OUT_LOGIN -> (context as Activity).runOnUiThread(object : Runnable {
                        override fun run() {
                            initView(null)
                            mTvBalance.text = "0.0"
                            mTvOrder.visibility = View.GONE
                        }
                    })
                    t?.eventType == EventConstant.USER_LOGIN -> {
                        loginType = t.`object` as Int
                        SharedPreferencesHelper(context, "UserBean").run {
                            token = getSharedPreference("token", "").toString().trim()
                            userId = getSharedPreference("userId", "").toString().trim()
                        }
                        initData()
                    }
                }
            }
    }


    private fun showTips() {
        if (dialog?.isShowing != true) {
            dialog = ConfirmDialog(activity)
                .setTitle("提示")
                .setMessage("设置密码以后就可以用密码进行登录了哦～")
                .setTwoButtonListener("取消",
                    { dialog, v ->
                        dialog.dismiss()
                    }, "前往", { dialog, v ->
                        OutWebActivity.start(activity, WebUrls.SETPASSWORD)
                        dialog.dismiss()
                    })
            dialog?.show()
        }
    }

    private fun showBankCardTips() {
        if (dialog?.isShowing != true) {
            dialog = ConfirmDialog(activity)
                .setTitle("提示")
                .setMessage("尚未绑定银行卡，请前往绑卡")
                .setTwoButtonListener("取消",
                    { dialog, v ->
                        dialog.dismiss()
                    }, "绑卡", { dialog, v ->
                        OutWebActivity.start(activity, WebUrls.AUTHBANK)
                        dialog.dismiss()
                    })
            dialog?.show()
        }
    }
}