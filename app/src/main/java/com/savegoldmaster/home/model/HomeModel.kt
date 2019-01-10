package com.savegoldmaster.home.model

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.bean.BannerBean
import io.reactivex.Observable

interface HomeModel {
    fun getBannerData(position: Int, number: Int, isIOS: Int): Observable<BannerBean>
}