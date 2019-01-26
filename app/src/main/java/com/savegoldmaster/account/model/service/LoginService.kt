package com.savegoldmaster.home.model.service

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.common.Urls
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LoginService {
    /**
     * 获取短信验证码
     */
    @FormUrlEncoded
    @POST(Urls.GET_PHONE_CODE)
    fun getPhoneCode(
        @Field("phone") phone: String
    ): Observable<BaseBean>

    /**
     * 快捷登录
     */
    @FormUrlEncoded
    @POST(Urls.FASTER_LOGIN)
    fun fasterLogin(
        @Field("telephone") telephone: String,
        @Field("code") code: String,
        @Field("invitedBy") invitedBy: String,
        @Field("activityId") activityId: String,
        @Field("source") source: String
    ): Observable<LoginBean>

    /**
     * 账号登录
     */
    @FormUrlEncoded
    @POST(Urls.ACCOUNT_LOGIN)
    fun accountLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Observable<LoginBean>
}