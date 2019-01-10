package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.base.view.BaseView
import com.savegoldmaster.home.model.bean.BannerBean

interface HomeContract {

    interface HomeView : BaseView {
        fun getBannerData(bean: BannerBean)
    }

    interface HomePresenter {
        fun getBanner(position: Int, number: Int, isIOS: Int)
    }
}