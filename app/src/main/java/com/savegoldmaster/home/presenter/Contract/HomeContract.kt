package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.base.view.BaseView
import com.savegoldmaster.home.model.bean.*

interface HomeContract {

    interface HomeView : BaseView {
        fun getBannerData(bean: BannerBean)
        fun getPushNotice(bean: BannerBean)
        fun getRecycleGold(recyclerGoldBean: RecyclerGoldBean)
        fun getGoldPrice(goldPriceBean: GoldPriceBean)
        fun getNewInformation(informationBean: InformationBean)
        fun getNearbyShop(nearbyShopBean: NearbyShopBean)
        fun getNotice(noticeBean:NoticeBean)
        fun getGoldNewOder(userOderBean: UserOderBean)

    }
    interface HomePresenter {
        fun getPushNotice(position: Int, number: Int, isIOS: Int)
        fun getBanner(position: Int, number: Int, isIOS: Int)
        fun getRecycleGold()
        fun getGoldPrice()
        fun getNewInformation()
        fun getNearbyShop(lat: String, lng: String)
        fun getNotice()
        fun getGoldNewOder()

    }
}