package com.example.zhanglibin.savegoldmaster.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.example.zhanglibin.savegoldmaster.R
import com.example.zhanglibin.savegoldmaster.base.view.BaseMVPActivity
import com.example.zhanglibin.savegoldmaster.home.presenter.Contract.UserContract
import com.example.zhanglibin.savegoldmaster.home.presenter.UserPresenterImpl
import android.support.v4.content.ContextCompat
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMVPActivity<UserPresenterImpl>(), UserContract.UserView,
    BottomNavigationBar.OnTabSelectedListener {
    override fun getUserDetail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private val mTitles = arrayOf("首页", "爱有金", "我的")
    private var mHomeFragment: HomeFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mLoveGoldFragment: LoveGoldFragment? = null
    private var mFragmentManager: FragmentManager = supportFragmentManager
    private var userId: String? = null

    companion object {
        fun start(context: Context, userId: String) {
            context.startActivity(
                Intent(context, MainActivity.javaClass)
                    .putExtra("userId", userId)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        userId = intent.getStringExtra("userId")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): UserPresenterImpl {
        return UserPresenterImpl()
    }

    override fun initViews(savedInstanceState: Bundle?) {
        mBottomNavigationBar
            .setActiveColor(R.color.bg_yellow)
            .setInActiveColor(R.color.black)
            .setBarBackgroundColor(R.color.white)
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
        mBottomNavigationBar
            .setBackgroundStyle(
                BottomNavigationBar.BACKGROUND_STYLE_STATIC
            )
        mBottomNavigationBar
            .addItem(
                BottomNavigationItem(R.mipmap.ic_launcher_round, mTitles[0])
                    .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round))
            )
            .addItem(
                BottomNavigationItem(R.mipmap.ic_launcher_round, mTitles[1])
                    .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round))
            )
            .addItem(
                BottomNavigationItem(R.mipmap.ic_launcher_round, mTitles[2])
                    .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round))
            )
            .setFirstSelectedPosition(0)
            .initialise()
        mBottomNavigationBar.setTabSelectedListener(this@MainActivity)
        //初始化Fragment
        setDefaultFragment()
    }

    private fun setDefaultFragment() {
        mHomeFragment = HomeFragment.newInstance()
        mFragmentManager.beginTransaction()?.apply {
            add(R.id.frameLayout, mHomeFragment)
            commit()
        }


    }

    /**
     * 隐藏当前fragment
     *
     * @param transaction
     */
    private fun hideFragment(transaction: FragmentTransaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment)
        }
        if (mLoveGoldFragment != null) {
            transaction.hide(mLoveGoldFragment)
        }
        if (mMineFragment != null) {
            transaction.hide(mMineFragment)
        }
    }

    override fun onTabReselected(position: Int) {
    }

    override fun onTabUnselected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
        //开启事务
        val transaction = mFragmentManager.beginTransaction()
        hideFragment(transaction)

        /**
         * fragment 用 add + show + hide 方式
         * 只有第一次切换会创建fragment，再次切换不创建
         *
         * fragment 用 replace 方式
         * 每次切换都会重新创建
         *
         */
        when (position) {
            0 -> {
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance()
                    transaction?.add(R.id.frameLayout, mHomeFragment)
                } else {
                    transaction?.show(mHomeFragment)
                }
            }
            1 -> {
                if (mLoveGoldFragment == null) {
                    mLoveGoldFragment = LoveGoldFragment.newInstance()
                    transaction?.add(R.id.frameLayout, mLoveGoldFragment)
                } else {
                    transaction?.show(mLoveGoldFragment)
                }
            }
            else -> {
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance("")
                    transaction?.add(R.id.frameLayout, mMineFragment)
                } else {
                    transaction?.show(mMineFragment)
                }
            }
        }
        // 事务提交
        transaction?.commit()
    }
}
