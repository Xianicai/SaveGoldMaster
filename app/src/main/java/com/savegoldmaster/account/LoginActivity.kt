package com.savegoldmaster.account

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.meituan.android.walle.WalleChannelReader
import com.savegoldmaster.R
import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.account.presenter.LoginPresenterImpl
import com.savegoldmaster.base.view.BaseMVPActivity
import com.savegoldmaster.common.WebUrls
import com.savegoldmaster.home.presenter.Contract.LoginContract
import com.savegoldmaster.utils.ConfirmDialog
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import com.savegoldmaster.utils.webutil.OutWebActivity
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern


class LoginActivity : BaseMVPActivity<LoginPresenterImpl>(), LoginContract.LoginView, View.OnClickListener {


    companion object {
        var ACCOUNT_LOGIN = 1
        var FASTER_LOGIN = 2
        fun start(context: Context, url: String) {
            context.startActivity(
                Intent(context, LoginActivity::class.java)
                    .putExtra("URL", url)
            )
        }
    }


    private var loginType = FASTER_LOGIN
    private var loginPresenterImpl: LoginPresenterImpl? = null
    private var countDownTimer: CountDownTimer? = null
    private var loginBean: LoginBean? = null
    private var forgetPasw = 0
    private var url = ""
    private var dialog: ConfirmDialog? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initWindow() {
        super.initWindow()
        setTheme(R.style.TranslucentTheme)
        setWindowStatusBarColors(this)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        url = intent.getStringExtra("URL")
        changeLoginStatus(mTvFasterLogin, mViewFasterLogin, loginType)
        mImageClose.setOnClickListener(this)
        mLayoutAccountLogin.setOnClickListener(this)
        mLayoutFasterLogin.setOnClickListener(this)
        mImageClearPhoneNum.setOnClickListener(this)
        mTvGetCode.setOnClickListener(this)
        mTvLogin.setOnClickListener(this)
        mTvUserAgreement.setOnClickListener(this)
        mTvForgetPassword.setOnClickListener(this)
        mImageHiddenPassword.setOnClickListener(this)
        mImageHiddenPassword.setImageResource(R.mipmap.ic_hidden_password)
        mEdPassword.transformationMethod = PasswordTransformationMethod.getInstance()

        mEdPhoneNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mImageClearPhoneNum.visibility =
                        if (mEdPhoneNum.text.toString().trim().isNotEmpty()) View.VISIBLE else View.GONE
                if (mEdPhoneNum.text.toString().trim().length == 11 && !isCellphone(mEdPhoneNum.text.toString().trim())) {
                    ToastUtil.showMessage("请检查您的手机号是否正确")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mEdPhoneNum.text.toString().trim().length == 11 && mEdPassword.text.toString().trim().length > 5) {
                    mTvLogin.setMBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.color_DDC899))
                } else {
                    mTvLogin.setMBackgroundColor(Color.parseColor("#80DDC899"))
                }
            }

        })
        mEdPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mEdPhoneNum.text.toString().trim().length == 11 && mEdPassword.text.toString().trim().length > 5) {
                    mTvLogin.setMBackgroundColor(ContextCompat.getColor(this@LoginActivity, R.color.color_DDC899))
                } else {
                    mTvLogin.setMBackgroundColor(Color.parseColor("#80DDC899"))
                }
            }

        })
    }

    override fun createPresenter(): LoginPresenterImpl {
        loginPresenterImpl = LoginPresenterImpl()
        return loginPresenterImpl!!
    }


    override fun onClick(v: View?) {
        when (v) {
            mImageClose -> {
                finish()
            }
            mLayoutAccountLogin -> {
                loginType = ACCOUNT_LOGIN
                changeLoginStatus(mTvAccountLogin, mViewAccountLogin, loginType)
            }
            mLayoutFasterLogin -> {
                loginType = FASTER_LOGIN
                changeLoginStatus(mTvFasterLogin, mViewFasterLogin, loginType)
            }
            mImageClearPhoneNum -> {
                mEdPhoneNum.setText("")
            }
            mTvGetCode -> {
                if (isCellphone(mEdPhoneNum.text.toString().trim())) {
                    loginPresenterImpl?.getPhoneCode(mEdPhoneNum.text.toString().trim())
                    setTime()
                } else {
                    ToastUtil.showMessage("请检查您的手机号是否正确")
                }
            }
            mTvLogin -> {
                val phoneNum = mEdPhoneNum.text.toString().trim()
                val password = mEdPassword.text.toString().trim()
                if (phoneNum.isEmpty()) {
                    ToastUtil.showMessage("请输入的手机号")
                    return
                }
                if (!isCellphone(phoneNum)) {
                    ToastUtil.showMessage("请检查您的手机号是否正确")
                    return
                }
                if (loginType == FASTER_LOGIN && password.length == 6) {
                    val source = "ZYPT_#_ANDROID_appstore-${WalleChannelReader.getChannel(applicationContext)}"
                    loginPresenterImpl?.fasterLogin(phoneNum, password, "", "", source)
                } else if (loginType == ACCOUNT_LOGIN && password.length >= 6) {
                    loginPresenterImpl?.accountLogin(phoneNum, password)
                } else {
                    return
                }
            }
            mImageHiddenPassword -> {
                if (loginType == ACCOUNT_LOGIN) {
                    if (mEdPassword.transformationMethod is HideReturnsTransformationMethod) {
                        mImageHiddenPassword.setImageResource(R.mipmap.ic_hidden_password)
                        mEdPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    } else {
                        mImageHiddenPassword.setImageResource(R.mipmap.ic_open_eyes)
                        mEdPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    }
                }


            }
            mTvForgetPassword -> {
                OutWebActivity.start(this, WebUrls.FIND_PASSWORD)
            }
            mTvUserAgreement -> {
                OutWebActivity.start(this, WebUrls.USERAGREEMENT)
            }
        }
    }

    override fun getPhoneCode() {
        ToastUtil.showMessage("验证码已发送至您的手机，请注意查收")
    }

    override fun fasterLoginSuccess(loginBean: LoginBean) {
        this.loginBean = loginBean
        loginSuccess(loginBean)
    }


    override fun fasterLoginFail() {
    }

    override fun accountLoginSuccess(loginBean: LoginBean) {
        loginSuccess(loginBean)

    }

    override fun accountLoginFail(result: LoginBean?) {
        when (result?.code) {
            1002 -> {
                showBannedTips()
            }
            1003 -> {
                forgetPasw += 1
                if (forgetPasw > 2) {
                    showTips()
                } else {
                    Toast.makeText(applicationContext, result.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loginSuccess(loginBean: LoginBean?) {
        loginBean ?: return
        SharedPreferencesHelper(this@LoginActivity, "UserBean").apply {
            put("token", loginBean.content.token)
            put("userId", loginBean.content.userId)
            put("authorization", "${loginBean.content.userId}_${loginBean.content.token}")
        }
        ToastUtil.showMessage("登录成功")
        RxBus.getDefault().post(RxEvent(EventConstant.USER_LOGIN, loginType))
        if (url.isNotEmpty()) {
            OutWebActivity.start(this, WebUrls.BASE_URL + url)
        }
        finish()

    }

    private fun changeLoginStatus(textView: TextView?, v: View?, loginType: Int) {
        //设置头部文字切换
        textView ?: return
        v ?: return
        mTvAccountLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.color_333333))
        mViewAccountLogin.visibility = View.GONE
        mTvFasterLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.color_333333))
        mViewFasterLogin.visibility = View.GONE
        textView.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.color_C09C60))
        v.visibility = View.VISIBLE
        mEdPassword.setText("")
        //设置不同状态下文字的显示隐藏
        if (loginType == ACCOUNT_LOGIN) {
            mTvTime.visibility = View.GONE
            mImageHiddenPassword.visibility = View.VISIBLE
            mTvGetCode.visibility = View.GONE
            mTvTips.visibility = View.GONE
            mTvForgetPassword.visibility = View.VISIBLE
            mImageHiddenPassword.setImageResource(R.mipmap.ic_hidden_password)
            mEdPassword.apply {
                transformationMethod = PasswordTransformationMethod.getInstance()
                hint = "请输入密码"
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
            }
            if (countDownTimer != null) {
                countDownTimer!!.cancel()
            }
        } else {
            mEdPassword.apply {
                transformationMethod = HideReturnsTransformationMethod.getInstance()
                mImageHiddenPassword.visibility = View.GONE
                mTvGetCode.visibility = View.VISIBLE
                mTvTips.visibility = View.VISIBLE
                mTvForgetPassword.visibility = View.GONE
                hint = "请输入验证码"
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
            }
        }
    }

    private fun setTime() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTvGetCode.visibility = View.GONE
                mTvTime.run {
                    visibility = View.VISIBLE
                    text = "${millisUntilFinished / 1000}s"
                }
            }

            override fun onFinish() {
                mTvGetCode.run {
                    visibility = View.VISIBLE
                    text = "重新获取"
                }
                mTvTime.visibility = View.GONE
            }
        }.start()
    }

    /**
     * 检查手机号是否合法
     * */
    fun isCellphone(str: String): Boolean {
        val pattern = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$")
        val matcher = pattern.matcher(str)
        return matcher.matches()
    }

    override fun onDestroy() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        super.onDestroy()
    }

    private fun showTips() {
        ConfirmDialog(this)
            .setTitle("提示")
            .setMessage("是否找回密码？")
            .setTwoButtonListener("取消",
                { dialog, v ->
                    dialog.dismiss()
                }, "前往", { dialog, v ->
                    OutWebActivity.start(this, WebUrls.FIND_PASSWORD)
                    dialog.dismiss()
                }).show()
    }

    fun setWindowStatusBarColors(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                window.statusBarColor = activity.resources.getColor(statusBarColor)
                window.statusBarColor = window.navigationBarColor
                //底部导航栏
//                window.navigationBarColor = activity.resources.getColor(navigationBarColor)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showBannedTips() {

        dialog = ConfirmDialog(this)
            .setTitle("提示")
            .setMessage(
                "很抱歉哦~您暂时被我们禁用了，禁用期\n" +
                        "间您无法执行相关操作，若有问题请联系\n" +
                        "客服: 4008-196-199"
            )
            .isDoubleButton(false)
            .setSingleButtonListener(object : ConfirmDialog.OnConfirmDialogClickListener {
                override fun onClick(dialog: ConfirmDialog?, v: View?) {
                    dialog?.dismiss()
                }

            })
        dialog?.show()
    }
}
