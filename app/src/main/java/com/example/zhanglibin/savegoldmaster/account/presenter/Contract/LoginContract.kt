package com.example.zhanglibin.savegoldmaster.home.presenter.Contract

import com.example.zhanglibin.savegoldmaster.account.model.bean.LoginBean
import com.example.zhanglibin.savegoldmaster.base.view.BaseView

interface LoginContract {

    interface LoginView : BaseView {
        fun fasterLoginSuccess(loginBean: LoginBean)
        fun fasterLoginFail()
        fun accountLoginSuccess()
        fun accountLoginFail()
        fun getPhoneCode()
    }

    interface LoginPresenter {
        fun getPhoneCode(phone: String)
        fun fasterLogin(telephone: String,code: String,invitedBy: String,activityId: String,source: String)
        fun accountLogin()
    }
}