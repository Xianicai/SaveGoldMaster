package com.example.zhanglibin.savegoldmaster.home.presenter

import com.example.zhanglibin.savegoldmaster.account.model.bean.LoginBean
import com.example.zhanglibin.savegoldmaster.account.model.impl.LoginModelImpl
import com.example.zhanglibin.savegoldmaster.base.BaseBean
import com.example.zhanglibin.savegoldmaster.base.presenter.BasePresenterImpl
import com.example.zhanglibin.savegoldmaster.home.model.bean.BannerBean
import com.example.zhanglibin.savegoldmaster.home.model.impl.HomeModelImpl
import com.example.zhanglibin.savegoldmaster.home.presenter.Contract.HomeContract
import com.example.zhanglibin.savegoldmaster.home.presenter.Contract.LoginContract
import com.example.zhanglibin.savegoldmaster.utils.ToastUtil
import com.example.zhanglibin.savegoldmaster.utils.retrofit.HttpResult
import com.example.zhanglibin.savegoldmaster.utils.retrofit.RespondObserver
import com.example.zhanglibin.savegoldmaster.utils.retrofit.ThreadTransformer

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