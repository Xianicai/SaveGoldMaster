package com.example.zhanglibin.savegoldmaster.home.model.impl

import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.home.model.UserModel
import com.example.zhanglibin.savegoldmaster.home.model.service.HomeService
import com.example.zhanglibin.savegoldmaster.utils.retrofit.Requester
import io.reactivex.Observable

class UserModelImpl : UserModel {
    private var userService: HomeService = Requester.get().create(HomeService::class.java)
    override fun getUserDetail(authorization: String): Observable<BaseBean> {
        return userService.getUserDetail(authorization)
    }
}