package com.savegoldmaster.home.model

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.home.model.bean.UserBean
import io.reactivex.Observable


interface UserModel {
    fun getUserDetail(): Observable<UserBean>
}