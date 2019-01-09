package com.example.zhanglibin.savegoldmaster.home.presenter.Contract

import com.example.zhanglibin.savegoldmaster.base.view.BaseView
import com.example.zhanglibin.savegoldmaster.home.model.bean.BannerBean

interface HomeContract {

    interface HomeView : BaseView {
        fun getBannerData(bean: BannerBean)
    }

    interface HomePresenter {
        fun getBanner(position: Int, number: Int, isIOS: Int)
    }
}