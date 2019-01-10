package com.savegoldmaster.home.model.service

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.common.Urls
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.utils.retrofit.HttpResult
import io.reactivex.Observable
import retrofit2.http.*


interface HomeService {
    /**
     * 获取个人信息
     *
     * @return
     */
    @GET(Urls.GET_USER_DETAIL)
    fun getUserDetail(): Observable<UserBean>

    /**
     * 获取首页banner
     */
    @FormUrlEncoded
    @POST(Urls.GET_BANNER_LIST)
    fun getBanner(
        @Field("position") position: Int,
        @Field("number") number: Int,
        @Field("isIOS") isIOS: Int
    ): Observable<BannerBean>

}