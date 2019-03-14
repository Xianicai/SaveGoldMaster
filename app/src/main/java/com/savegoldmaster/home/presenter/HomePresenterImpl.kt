package com.savegoldmaster.home.presenter

import com.savegoldmaster.base.presenter.BasePresenterImpl
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.home.model.impl.HomeModelImpl
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.utils.retrofit.RespondObserver
import com.savegoldmaster.utils.retrofit.ThreadTransformer

class HomePresenterImpl : BasePresenterImpl<HomeContract.HomeView>(), HomeContract.HomePresenter {
    override fun getPushNotice(position: Int, number: Int, isIOS: Int) {
        homeModelImpl.getBannerData(position, number, isIOS)
            .compose(ThreadTransformer<BannerBean>())
            .subscribe(object : RespondObserver<BannerBean>() {
                override fun onSuccess(result: BannerBean?) {
                    super.onSuccess(result)
                    mView?.getPushNotice(result!!)
                }
            })
    }

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

    override fun getNotice() {
        homeModelImpl.getNotice()
            .compose(ThreadTransformer<NoticeBean>())
            .subscribe(object : RespondObserver<NoticeBean>() {
                override fun onSuccess(result: NoticeBean?) {
                    super.onSuccess(result)
                    if (result?.code == 100){
                        mView?.getNotice(result)
                    }
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
