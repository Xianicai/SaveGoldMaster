package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.base.view.BaseView

interface LoginContract {

    interface LoginView : BaseView {
        fun fasterLoginSuccess(loginBean: LoginBean)
        fun fasterLoginFail()
        fun accountLoginSuccess(loginBean: LoginBean)
        fun accountLoginFail(result: LoginBean?)
        fun getPhoneCode()
    }

    interface LoginPresenter {
        fun getPhoneCode(phone: String)
        fun fasterLogin(telephone: String,code: String,invitedBy: String,activityId: String,source: String)
        fun accountLogin(username: String, password: String)
    }
}