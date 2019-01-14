package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.base.view.BaseView
import com.savegoldmaster.home.model.bean.*

interface HomeContract {

    interface HomeView : BaseView {
        fun getBannerData(bean: BannerBean)
        fun getMessageTips()
        fun getRecycleGold(recyclerGoldBean: RecyclerGoldBean)
        fun getGoldPrice(goldPriceBean: GoldPriceBean)
        fun getNewInformation(informationBean: InformationBean)
        fun getNearbyShop(nearbyShopBean: NearbyShopBean)
        fun getNotice(noticeBean:NoticeBean)
        fun getGoldNewOder(userOderBean: UserOderBean)

    }
    interface HomePresenter {
        fun getBanner(position: Int, number: Int, isIOS: Int)
        fun getMessageTips()
        fun getRecycleGold()
        fun getGoldPrice()
        fun getNewInformation()
        fun getNearbyShop(lat: String, lng: String)
        fun getNotice(pageNum: Int, pageSize: Int, type: Int)
        fun getGoldNewOder()

    }
}