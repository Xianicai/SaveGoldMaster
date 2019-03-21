package com.savegoldmaster.account.presenter

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.account.model.impl.LoginModelImpl
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.presenter.Contract.LoginContract
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer

class LoginPresenterImpl : BasePresenterImpl<LoginContract.LoginView>(), LoginContract.LoginPresenter {



    private var loginModelImpl: LoginModelImpl = LoginModelImpl()
    override fun getPhoneCode(phone: String) {
        loginModelImpl.getPhoneCode(phone)
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getPhoneCode()
                }
            })
    }

    override fun fasterLogin(telephone: String, code: String, invitedBy: String, activityId: String, source: String) {
        loginModelImpl.fasterLogin(telephone, code, invitedBy, activityId, source)
            .compose(ThreadTransformer<LoginBean>())
            .subscribe(object : RespondObserver<LoginBean>() {
                override fun onSuccess(result: LoginBean?) {
                    super.onSuccess(result)
                    if (result?.code == 1002){
                        mView?.accountLoginFail(result)
                    }else{
                        mView?.fasterLoginSuccess(result!!)
                    }

                }
            })
    }

    override fun accountLogin(username: String, password: String) {
        loginModelImpl.accountLogin(username,password)
            .compose(ThreadTransformer<LoginBean>())
            .subscribe(object : RespondObserver<LoginBean>() {
                override fun onSuccess(result: LoginBean?) {
                    super.onSuccess(result)
                    if (result?.code == 1003||result?.code == 1002){
                        mView?.accountLoginFail(result)
                    }else{
                        mView?.accountLoginSuccess(result!!)
                    }
                }
            })
    }
}