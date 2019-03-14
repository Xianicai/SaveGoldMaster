package com.savegoldmaster.home.presenter

import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.NoticeBean
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.model.impl.UserModelImpl
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer

class UserPresenterImpl : BasePresenterImpl<UserContract.UserView>(), UserContract.UserPresenter {


    private var loginModelImpl: UserModelImpl = UserModelImpl()


    override fun getUserDetail() {
        loginModelImpl.getUserDetail()
            .compose(ThreadTransformer<UserBean>())
            .subscribe(object : RespondObserver<UserBean>() {
                override fun onSuccess(result: UserBean?) {
                    super.onSuccess(result)
                    if (result?.code == 401) {
                        mView?.getUserDetailFailure(result)
                    } else {
                        mView?.getUserDetail(result!!)
                    }
                }
            })
    }

    override fun getNotice() {
        loginModelImpl.getNotice()
            .compose(ThreadTransformer<NoticeBean>())
            .subscribe(object : RespondObserver<NoticeBean>() {
                override fun onSuccess(result: NoticeBean?) {
                    super.onSuccess(result)
                    if (result?.code == 100) {
                        mView?.getNotice(result)
                    }

                }
            })
    }
}
