package com.example.zhanglibin.savegoldmaster.home.model.impl

import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.home.model.HomeModel
import com.example.zhanglibin.savegoldmaster.home.model.UserModel
import com.example.zhanglibin.savegoldmaster.home.model.bean.BannerBean
import com.example.zhanglibin.savegoldmaster.home.model.service.HomeService
import com.example.zhanglibin.savegoldmaster.utils.retrofit.Requester
import io.reactivex.Observable

class HomeModelImpl : HomeModel {
    private var homeService: HomeService = Requester.get().create(HomeService::class.java)
    override fun getBannerData(position: Int, number: Int, isIOS: Int): Observable<BannerBean> {
        return homeService.getBanner(position, number, isIOS)
    }
}