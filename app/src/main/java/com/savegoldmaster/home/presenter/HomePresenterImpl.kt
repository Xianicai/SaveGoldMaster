package com.savegoldmaster.home.presenter

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.account.model.impl.LoginModelImpl
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.model.bean.NoticeBean
import com.savegoldmaster.home.model.impl.HomeModelImpl
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.Contract.LoginContract
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.retrofit.HttpResult
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer
import kotlin.math.ln

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

    override fun getMessageTips() {
        homeModelImpl.getMessageTips()
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getMessageTips()
                }
            })
    }

    override fun getRecycleGold() {
        homeModelImpl.getRecycleGold()
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getRecycleGold()
                }
            })
    }

    override fun getGoldPrice() {
        homeModelImpl.getGoldPrice()
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getGoldPrice()
                }
            })
    }

    override fun getNewInformation() {
        homeModelImpl.getNewInformation()
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getNewInformation()
                }
            })
    }

    override fun getNearbyShop(lat: String, lng: String) {
        homeModelImpl.getNearbyShop(lat, lng)
            .compose(ThreadTransformer<BaseBean>())
            .subscribe(object : RespondObserver<BaseBean>() {
                override fun onSuccess(result: BaseBean?) {
                    super.onSuccess(result)
                    mView?.getNearbyShop()
                }
            })
    }

    override fun getNotice(pageNum: Int, pageSize: Int, type: Int) {
        homeModelImpl.getNotice(pageNum, pageSize, type)
            .compose(ThreadTransformer<NoticeBean>())
            .subscribe(object : RespondObserver<NoticeBean>() {
                override fun onSuccess(result: NoticeBean?) {
                    super.onSuccess(result)
                    mView?.getNotice(result!!)
                }
            })
    }
}
