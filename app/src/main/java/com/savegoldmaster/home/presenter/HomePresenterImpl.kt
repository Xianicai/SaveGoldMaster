package com.savegoldmaster.home.presenter

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.account.model.impl.LoginModelImpl
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.impl.HomeModelImpl
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.Contract.LoginContract
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.retrofit.HttpResult
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer

class HomePresenterImpl : BasePresenterImpl<HomeContract.HomeView>(), HomeContract.HomePresenter {
    private var homeModelImpl: HomeModelImpl = HomeModelImpl()
    override fun getBanner(position: Int, number: Int, isIOS: Int) {
        homeModelImpl.getBannerData(position, number, isIOS)
            .compose(ThreadTransformer<BannerBean>())
            .subscribe(object : RespondObserver<BannerBean>() {
                override fun onSuccess(result: BannerBean?) {
                    super.onSuccess(result)
                    mView?.getBannerData(result!!)
                }
            })
    }


}