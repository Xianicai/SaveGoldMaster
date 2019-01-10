package com.savegoldmaster.account.model

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.utils.retrofit.HttpResult
import io.reactivex.Observable


interface LoginModel {
    fun getPhoneCode(phone: String): Observable<BaseBean>
    fun fasterLogin(telephone: String,code: String,invitedBy: String,activityId: String,source: String): Observable<LoginBean>
    fun accountLogin(username: String,password: String): Observable<LoginBean>
}