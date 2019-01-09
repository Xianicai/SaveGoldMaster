package com.example.zhanglibin.savegoldmaster.home.presenter.Contract

import com.example.zhanglibin.savegoldmaster.base.view.BaseView

interface UserContract {

    interface UserView : BaseView {
        fun getUserDetail()
    }

    interface UserPresenter {
        fun getUserDetail(authorization: String)
    }
}