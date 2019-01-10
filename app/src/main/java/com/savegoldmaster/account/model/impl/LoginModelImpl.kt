package com.savegoldmaster.account.model.impl

import com.savegoldmaster.account.model.LoginModel
import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.service.LoginService
import com.savegoldmaster.utils.retrofit.HttpResult
import com.savegoldmaster.utils.retrofit.Requester
import io.reactivex.Observable

class LoginModelImpl : LoginModel {
    private var loginService: LoginService = Requester.get().create(LoginService::class.java)

    override fun getPhoneCode(phone: String): Observable<BaseBean> {
        return loginService.getPhoneCode(phone)
    }

    override fun fasterLogin(
        telephone: String,
        code: String,
        invitedBy: String,
        activityId: String,
        source: String
    ): Observable<LoginBean> {
        return loginService.fasterLogin(telephone, code, invitedBy, activityId, source)
    }

    override fun accountLogin(username: String, password: String): Observable<LoginBean> {
        return loginService.accountLogin(username, password)
    }
}