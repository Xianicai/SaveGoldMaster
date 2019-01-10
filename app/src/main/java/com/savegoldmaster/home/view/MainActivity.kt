package com.savegoldmaster.home.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.savegoldmaster.base.view.BaseMVPActivity
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.home.presenter.UserPresenterImpl
import android.support.v4.content.ContextCompat
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationBar.*
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.utils.permissionUtil.PermissionInterface
import kotlinx.android.synthetic.main.activity_main.*
import com.savegoldmaster.utils.permissionUtil.PermissionHelper


class MainActivity : BaseMVPActivity<UserPresenterImpl>(), UserContract.UserView,
    BottomNavigationBar.OnTabSelectedListener, PermissionInterface {
    override fun getUserDetail(userBean: UserBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private val mTitles = arrayOf("首页", "爱有金", "我的")
    private var mHomeFragment: HomeFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mLoveGoldFragment: LoveGoldFragment? = null
    private var mFragmentManager: FragmentManager = supportFragmentManager
    private var userId: String? = null
    private var mPermissionHelper: PermissionHelper? = null


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
        //初始化并发起权限申请
        mPermissionHelper = PermissionHelper(this, this)
        mPermissionHelper?.requestPermissions()

        mBottomNavigationBar
            .setActiveColor(R.color.bg_yellow)
            .setInActiveColor(R.color.black)
            .setBarBackgroundColor(R.color.white)
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
        mBottomNavigationBar.setBackgroundStyle(BACKGROUND_STYLE_STATIC)
        mBottomNavigationBar
            .setBackgroundStyle(
                BottomNavigationBar.BACKGROUND_STYLE_STATIC
            )
        mBottomNavigationBar
            .addItem(
                BottomNavigationItem(R.mipmap.ic_home_home_select, mTitles[0])
                    .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_home_home_normal))
            )
            .addItem(
                BottomNavigationItem(R.mipmap.ic_home_gold_normal, mTitles[1])
                    .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_home_gold_normal))
            )
            .addItem(
                BottomNavigationItem(R.mipmap.ic_home_mine_select, mTitles[2])
                    .setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.ic_home_mine_normal))
            )
            .setFirstSelectedPosition(0)
            .initialise()
        mBottomNavigationBar.hide(false)
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
                    mMineFragment = MineFragment.newInstance()
                    transaction?.add(R.id.frameLayout, mMineFragment)
                } else {
                    transaction?.show(mMineFragment)
                }
            }
        }
        // 事务提交
        transaction?.commit()
    }

    override fun getPermissionsRequestCode(): Int {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000

    }

    override fun getPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    }

    override fun requestPermissionsSuccess() {
        //权限请求用户已经全部允许
    }

    override fun requestPermissionsFail() {
        //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。

    }


}
