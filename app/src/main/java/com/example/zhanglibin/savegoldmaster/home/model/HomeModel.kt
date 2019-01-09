package com.example.zhanglibin.savegoldmaster.home.model

import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.home.model.bean.BannerBean
import io.reactivex.Observable

interface HomeModel {
    fun getBannerData(position: Int, number: Int, isIOS: Int): Observable<BannerBean>
}