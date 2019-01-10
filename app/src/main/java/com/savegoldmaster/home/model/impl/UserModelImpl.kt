package com.savegoldmaster.home.model.impl

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.UserModel
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.model.service.HomeService
import com.savegoldmaster.utils.retrofit.Requester
import io.reactivex.Observable

class UserModelImpl : UserModel {
    private var userService: HomeService = Requester.get().create(HomeService::class.java)
    override fun getUserDetail(): Observable<UserBean> {
        return userService.getUserDetail()
    }
}