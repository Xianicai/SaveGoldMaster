package com.example.zhanglibin.savegoldmaster.home.presenter

import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.base.presenter.BasePresenterImpl
import com.example.zhanglibin.savegoldmaster.home.model.impl.UserModelImpl
import com.example.zhanglibin.savegoldmaster.home.presenter.Contract.UserContract
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import com.example.zhanglibin.savegoldmaster.utils.retrofit.RespondObserver
import com.example.zhanglibin.savegoldmaster.utils.retrofit.ThreadTransformer

class UserPresenterImpl : BasePresenterImpl<UserContract.UserView>(), UserContract.UserPresenter {
    private var loginModelImpl: UserModelImpl = UserModelImpl()


    override fun getUserDetail(authorization: String) {
        loginModelImpl.getUserDetail(authorization)
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object :RespondObserver<BaseBean>(){
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getUserDetail()
                }
            })
    }
}
