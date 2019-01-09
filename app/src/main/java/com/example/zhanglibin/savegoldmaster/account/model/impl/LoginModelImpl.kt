package com.example.zhanglibin.savegoldmaster.account.model.impl

import com.example.zhanglibin.savegoldmaster.account.model.LoginModel
import com.example.zhanglibin.savegoldmaster.account.model.bean.LoginBean
import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.home.model.service.LoginService
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import com.example.zhanglibin.savegoldmaster.utils.retrofit.Requester
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
        return loginService.fasterLogin(telephone,code,invitedBy,activityId,source)
    }
}