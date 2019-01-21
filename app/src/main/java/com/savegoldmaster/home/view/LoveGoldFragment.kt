package com.savegoldmaster.home.view

import android.view.View
import com.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.presenter.UserPresenterImpl

class LoveGoldFragment: BaseMVPFragment<UserPresenterImpl>() {
    companion object {
        fun newInstance(): LoveGoldFragment {
            return LoveGoldFragment()
        }
    }
    override fun getLayoutId(): Int {
        return  R.layout.fragment_love_gold
    }

    override fun initView(view: View?) {
    }

    override fun createPresenter(): UserPresenterImpl {
        return UserPresenterImpl()

    }
}