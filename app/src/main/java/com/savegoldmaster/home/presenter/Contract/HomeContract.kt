package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.base.view.BaseView
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.bean.NoticeBean

interface HomeContract {

    interface HomeView : BaseView {
        fun getBannerData(bean: BannerBean)
        fun getMessageTips()
        fun getRecycleGold()
        fun getGoldPrice()
        fun getNewInformation()
        fun getNearbyShop()
        fun getNotice(noticeBean:NoticeBean)

    }
    interface HomePresenter {
        fun getBanner(position: Int, number: Int, isIOS: Int)
        fun getMessageTips()
        fun getRecycleGold()
        fun getGoldPrice()
        fun getNewInformation()
        fun getNearbyShop(lat: String, lng: String)
        fun getNotice(pageNum: Int, pageSize: Int, type: Int)

    }
}