package com.savegoldmaster.home.model.impl

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.HomeModel
import com.savegoldmaster.home.model.UserModel
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.home.model.service.HomeService
import com.savegoldmaster.utils.retrofit.Requester
import io.reactivex.Observable

class HomeModelImpl : HomeModel {
    private var homeService: HomeService = Requester.get().create(HomeService::class.java)

    override fun getBannerData(position: Int, number: Int, isIOS: Int): Observable<BannerBean> {
        return homeService.getBanner(position, number, isIOS)
    }

    override fun getMessageTips(): Observable<BaseBean> {
        return homeService.getMessageTips()
    }

    override fun getRecycleGold(): Observable<RecyclerGoldBean> {
        return homeService.getRecycleGold()
    }

    override fun getGoldPrice(): Observable<GoldPriceBean> {
        return homeService.getGoldPrice()
    }

    override fun getNewInformation(): Observable<InformationBean> {
        return homeService.getNewInformation(1, 2)
    }

    override fun getNearbyShop(lat: String, lng: String): Observable<NearbyShopBean> {
        return homeService.getNearbyShop(lat, lng)
    }

    override fun getNotice(pageNum: Int, pageSize: Int, type: Int): Observable<NoticeBean> {
        return homeService.getNotice(pageNum, pageSize, type)

    }

    override fun getGoldNewOder(): Observable<UserOderBean> {
        return homeService.getGoldNewOder()
    }
}

