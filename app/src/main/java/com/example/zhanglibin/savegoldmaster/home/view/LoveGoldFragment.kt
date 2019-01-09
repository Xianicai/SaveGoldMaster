package com.example.zhanglibin.savegoldmaster.home.view

import android.view.View
import com.example.zhanglibin.savegoldmaster.R
import com.example.zhanglibin.savegoldmaster.base.view.BaseMVPFragment
import com.example.zhanglibin.savegoldmaster.home.presenter.UserPresenterImpl

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