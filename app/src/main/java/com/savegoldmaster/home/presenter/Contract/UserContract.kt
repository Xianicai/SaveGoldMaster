package com.savegoldmaster.home.presenter.Contract

import com.savegoldmaster.base.view.BaseView
import com.savegoldmaster.home.model.bean.UserBean

interface UserContract {

    interface UserView : BaseView {
        fun getUserDetail(userBean: UserBean)
        fun getUserDetailFailure(userBean: UserBean)
    }

    interface UserPresenter {
        fun getUserDetail()
    }
}