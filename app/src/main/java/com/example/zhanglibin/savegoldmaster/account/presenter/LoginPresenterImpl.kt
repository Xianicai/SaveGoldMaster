package com.example.zhanglibin.savegoldmaster.home.presenter

import com.example.zhanglibin.savegoldmaster.account.model.bean.LoginBean
import com.example.zhanglibin.savegoldmaster.account.model.impl.LoginModelImpl
import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.base.presenter.BasePresenterImpl
import com.example.zhanglibin.savegoldmaster.home.presenter.Contract.LoginContract
import com.example.zhanglibin.savegoldmaster.utils.ToastUtil
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import com.example.zhanglibin.savegoldmaster.utils.retrofit.RespondObserver
import com.example.zhanglibin.savegoldmaster.utils.retrofit.ThreadTransformer

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
                    mView?.fasterLoginSuccess(result!!)
                }
            })
    }

    override fun accountLogin() {
    }
}