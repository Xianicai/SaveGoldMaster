package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.base.view.BaseView
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.bean.UserBean

interface AppStartContract {

    interface AppStartView : BaseView {
        fun getAppAd(adBean: BannerBean)
    }

    interface AppStartPresenter {
        fun getAppAd(position: Int, number: Int, isIOS: Int)
    }
}