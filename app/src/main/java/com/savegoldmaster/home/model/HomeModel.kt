package com.savegoldmaster.home.model

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.bean.*
import io.reactivex.Observable

interface HomeModel {
    fun getBannerData(position: Int, number: Int, isIOS: Int): Observable<BannerBean>
    fun getRecycleGold(): Observable<RecyclerGoldBean>
    fun getGoldPrice(): Observable<GoldPriceBean>
    fun getNewInformation(): Observable<InformationBean>
    fun getNearbyShop(lat: String, lng: String): Observable<NearbyShopBean>
    fun getNotice(): Observable<NoticeBean>
    fun getGoldNewOder(): Observable<UserOderBean>

}