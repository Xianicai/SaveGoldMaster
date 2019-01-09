package com.example.zhanglibin.savegoldmaster.home.view

import android.view.View
import com.example.zhanglibin.savegoldmaster.R
import com.example.zhanglibin.savegoldmaster.account.LoginActivity
import com.example.zhanglibin.savegoldmaster.base.view.BaseMVPFragment
import com.example.zhanglibin.savegoldmaster.home.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseMVPFragment<UserPresenterImpl>(), View.OnClickListener {


    companion object {
        fun newInstance(authorization: String): MineFragment {
            return MineFragment()
        }
    }

    var userPresenterImpl: UserPresenterImpl? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        mButtonLogin.setOnClickListener(this)
//        userPresenterImpl?.getUserDetail()

    }

    override fun createPresenter(): UserPresenterImpl {
        userPresenterImpl = UserPresenterImpl()
        return userPresenterImpl as UserPresenterImpl

    }

    override fun onClick(v: View?) {
        when (v) {
            mButtonLogin -> {
                LoginActivity.start(activity!!)
//                loginPresenterImpl?.getBanner(1, 3, 2)
            }
        }
    }

}