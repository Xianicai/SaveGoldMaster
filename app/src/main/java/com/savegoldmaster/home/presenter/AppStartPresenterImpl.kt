package com.savegoldmaster.home.presenter

import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.model.impl.HomeModelImpl
import com.savegoldmaster.home.model.impl.UserModelImpl
import com.savegoldmaster.home.presenter.Contract.AppStartContract
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.utils.retrofit.HttpResult
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer

class AppStartPresenterImpl : BasePresenterImpl<AppStartContract.AppStartView>(), AppStartContract.AppStartPresenter {
    private var homeModelImpl: HomeModelImpl = HomeModelImpl()

    override fun getAppAd(position: Int, number: Int, isIOS: Int) {
        homeModelImpl.getBannerData(position, number, isIOS)
            .compose(ThreadTransformer<BannerBean>())
            .subscribe(object : RespondObserver<BannerBean>() {
                override fun onSuccess(result: BannerBean?) {
                    super.onSuccess(result)
                    mView?.getAppAd(result!!)
                }
            })
    }

}
