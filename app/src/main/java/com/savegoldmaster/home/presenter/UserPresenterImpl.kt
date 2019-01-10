package com.savegoldmaster.home.presenter

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.model.impl.UserModelImpl
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.utils.retrofit.HttpResult
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer

class UserPresenterImpl : BasePresenterImpl<UserContract.UserView>(), UserContract.UserPresenter {
    private var loginModelImpl: UserModelImpl = UserModelImpl()


    override fun getUserDetail() {
        loginModelImpl.getUserDetail()
            .compose(ThreadTransformer<UserBean>())
            .subscribe(object :RespondObserver<UserBean>(){
                override fun onSuccess(result: UserBean?) {
                    super.onSuccess(result)
                    mView?.getUserDetail(result!!)
                }
            })
    }
}
