package com.example.zhanglibin.savegoldmaster.home.model

import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import io.reactivex.Observable


interface UserModel {
    fun getUserDetail(authorization: String): Observable<BaseBean>
}