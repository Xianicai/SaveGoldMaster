package com.savegoldmaster.account

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.TextView
import com.savegoldmaster.R
import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.base.view.BaseMVPActivity
import com.savegoldmaster.home.presenter.Contract.LoginContract
import com.savegoldmaster.home.presenter.LoginPresenterImpl
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern


class LoginActivity : BaseMVPActivity<LoginPresenterImpl>(), LoginContract.LoginView, View.OnClickListener {


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private var ACCOUNT_LOGIN = 1
    private var FASTER_LOGIN = 2
    private var loginType = ACCOUNT_LOGIN
    private var loginPresenterImpl: LoginPresenterImpl? = null
    private var start: CountDownTimer? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews(savedInstanceState: Bundle?) {
        changeLoginStatus(mTvAccountLogin, mViewAccountLogin, loginType)
        mImageClose.setOnClickListener(this)
        mLayoutAccountLogin.setOnClickListener(this)
        mLayoutFasterLogin.setOnClickListener(this)
        mImageClearPhoneNum.setOnClickListener(this)
        mTvGetCode.setOnClickListener(this)
        mTvLogin.setOnClickListener(this)
        mImageHiddenPassword.setOnClickListener(this)
        if (mEdPassword.transformationMethod is HideReturnsTransformationMethod) {
            mImageHiddenPassword.setImageResource(R.mipmap.ic_hidden_password)
            mEdPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            mImageHiddenPassword.setImageResource(R.mipmap.ic_open_eyes)
            mEdPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
        mEdPhoneNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mImageClearPhoneNum.visibility =
                        if (mEdPhoneNum.text.toString().trim().isNotEmpty()) View.VISIBLE else View.GONE
                if (mEdPhoneNum.text.toString().trim().length == 11 && !isCellphone(mEdPhoneNum.text.toString().trim())) {
                    ToastUtil.showMessage("请输入合法的手机号")
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
                    ToastUtil.showMessage("您输入的手机号不正确")
                }
            }
            mTvLogin -> {
                val phoneNum = mEdPhoneNum.text.toString().trim()
                val password = mEdPassword.text.toString().trim()
                if (phoneNum.isEmpty()){
                    ToastUtil.showMessage("请输入的手机号")
                    return
                }
                if (!isCellphone(phoneNum)){
                    ToastUtil.showMessage("您输入的手机号不正确")
                    return
                }
                if (loginType == FASTER_LOGIN  && password.length == 6) {
                    loginPresenterImpl?.fasterLogin(phoneNum, password, "", "", "")
                } else if (loginType == ACCOUNT_LOGIN  && password.length >= 6) {
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
        }
    }

    override fun getPhoneCode() {
        ToastUtil.showMessage("验证码发送成功，请注意查收")
    }

    override fun fasterLoginSuccess(loginBean: LoginBean) {
        loginSuccess(loginBean)
    }


    override fun fasterLoginFail() {
    }

    override fun accountLoginSuccess(loginBean: LoginBean) {
        loginSuccess(loginBean)

    }

    override fun accountLoginFail() {
    }

    private fun loginSuccess(loginBean: LoginBean) {
        SharedPreferencesHelper(this@LoginActivity, "UserBean").apply {
            put("token", loginBean.content.token)
            put("userId", loginBean.content.userId)
            put("authorization", "${loginBean.content.userId}_${loginBean.content.token}")
        }
        ToastUtil.showMessage("登录成功")
        RxBus.getDefault().post(RxEvent(EventConstant.USER_LOGIN, loginBean.content.userId))
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
            mImageHiddenPassword.visibility = View.VISIBLE
            mTvGetCode.visibility = View.GONE
            mTvTips.visibility = View.GONE
            mTvForgetPassword.visibility = View.VISIBLE
            mEdPassword.apply {
                hint = "请输入密码"
//                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))
            }
        } else {
            mEdPassword.apply {
                mImageHiddenPassword.visibility = View.GONE
                mTvGetCode.visibility = View.VISIBLE
                mTvTips.visibility = View.VISIBLE
                mTvForgetPassword.visibility = View.GONE
                hint = "请输入验证码"
//                inputType = InputType.TYPE_CLASS_NUMBER
                filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6))
            }
        }
    }

    private fun setTime() {
        start = object : CountDownTimer(60000, 1000) {
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
                    text = "重新获取验证码"
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
        start = null
        super.onDestroy()
    }

}
