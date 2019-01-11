package com.savegoldmaster.home.model

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.bean.NoticeBean
import io.reactivex.Observable

interface HomeModel {
    fun getBannerData(position: Int, number: Int, isIOS: Int): Observable<BannerBean>
    fun getMessageTips(): Observable<BaseBean>
    fun getRecycleGold(): Observable<BaseBean>
    fun getGoldPrice(): Observable<BaseBean>
    fun getNewInformation(): Observable<BaseBean>
    fun getNearbyShop(lat: String, lng: String): Observable<BaseBean>
    fun getNotice(pageNum: Int, pageSize: Int, type: Int): Observable<NoticeBean>

}