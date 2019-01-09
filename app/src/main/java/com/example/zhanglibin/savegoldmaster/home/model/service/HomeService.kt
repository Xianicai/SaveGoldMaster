package com.example.zhanglibin.savegoldmaster.home.model.service

import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.common.Urls
import com.example.zhanglibin.savegoldmaster.home.model.bean.BannerBean
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import io.reactivex.Observable
import retrofit2.http.*


interface HomeService {
    /**
     * 获取个人信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Urls.GET_USER_DETAIL)
    fun getUserDetail(
        @Field("authorization") authorization: String
    ): Observable<BaseBean>

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