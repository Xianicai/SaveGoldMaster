package com.savegoldmaster.home.model.service

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.common.Urls
import com.savegoldmaster.home.model.bean.*
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

    /**
     * 获取首页消息红点
     *
     * @return
     */
    @GET(Urls.GET_MESSAGE_TIPS)
    fun getMessageTips(): Observable<BaseBean>

    /**
     * 回收黄金总量
     *
     * @return
     */
    @GET(Urls.GET_RECYCLE_GOLD)
    fun getRecycleGold(): Observable<RecyclerGoldBean>

    /**
     * 获取金价
     *
     * @return
     */
    @GET(Urls.GET_GOLD_PRICE)
    fun getGoldPrice(): Observable<GoldPriceBean>

    /**
     * 获取资讯
     *
     * @return
     */
    @GET(Urls.GET_NEW_INFORMATION)
    fun getNewInformation(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<InformationBean>

    /**
     * 获取附近店铺
     *@param   lat 纬度  lng 经度
     * @return
     */
    @GET(Urls.GET_NEARBY_SHOP)
    fun getNearbyShop(
        @Query("lat") lat: String,
        @Query("lng") lng: String
    ): Observable<NearbyShopBean>

    /**
     * 获取首页公告
     * @param   type  0黄金资讯，1系统公告，2营销活动，3用户消息
     * @return
     */
    @GET(Urls.GET_NOTICE)
    fun getNotice(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int,
        @Query("type") type: Int
    ): Observable<NoticeBean>

    /**
     * 获取首页滚动黄金订单
     */
    @GET(Urls.GET_GOLD_NEW_ODER)
    fun getGoldNewOder(): Observable<UserOderBean>
}