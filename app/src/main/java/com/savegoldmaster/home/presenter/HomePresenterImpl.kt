package com.savegoldmaster.home.presenter

import com.savegoldmaster.account.model.bean.LoginBean
import com.savegoldmaster.account.model.impl.LoginModelImpl
import com.savegoldmaster.base.BaseBean
import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.*
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
            .compose(ThreadTransformer<RecyclerGoldBean>())
            .subscribe(object : RespondObserver<RecyclerGoldBean>() {
                override fun onSuccess(result: RecyclerGoldBean?) {
                    super.onSuccess(result)
                    mView?.getRecycleGold(result!!)
                }
            })
    }

    override fun getGoldPrice() {
        homeModelImpl.getGoldPrice()
            .compose(ThreadTransformer<GoldPriceBean>())
            .subscribe(object : RespondObserver<GoldPriceBean>() {
                override fun onSuccess(result: GoldPriceBean?) {
                    super.onSuccess(result)
                    mView?.getGoldPrice(result!!)
                }
            })
    }

    override fun getNewInformation() {
        homeModelImpl.getNewInformation()
            .compose(ThreadTransformer<InformationBean>())
            .subscribe(object : RespondObserver<InformationBean>() {
                override fun onSuccess(result: InformationBean?) {
                    super.onSuccess(result)
                    mView?.getNewInformation(result!!)
                }
            })
    }

    override fun getNearbyShop(lat: String, lng: String) {
        homeModelImpl.getNearbyShop(lat, lng)
            .compose(ThreadTransformer<NearbyShopBean>())
            .subscribe(object : RespondObserver<NearbyShopBean>() {
                override fun onSuccess(result: NearbyShopBean?) {
                    super.onSuccess(result)
                    mView?.getNearbyShop(result!!)
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

    override fun getGoldNewOder() {
        homeModelImpl.getGoldNewOder()
            .compose(ThreadTransformer<UserOderBean>())
            .subscribe(object : RespondObserver<UserOderBean>() {
                override fun onSuccess(result: UserOderBean?) {
                    super.onSuccess(result)
                    mView?.getGoldNewOder(result!!)
                }
            })
    }
}
