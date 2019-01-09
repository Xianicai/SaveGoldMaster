package com.example.zhanglibin.savegoldmaster.account.model

import com.example.zhanglibin.savegoldmaster.account.model.bean.LoginBean
import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import io.reactivex.Observable


interface LoginModel {
    fun getPhoneCode(phone: String): Observable<BaseBean>
    fun fasterLogin(telephone: String,code: String,invitedBy: String,activityId: String,source: String): Observable<LoginBean>
}