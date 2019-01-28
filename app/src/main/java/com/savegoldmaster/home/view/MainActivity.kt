package com.savegoldmaster.home.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationBar.*
import com.savegoldmaster.R
import com.savegoldmaster.utils.permissionUtil.PermissionInterface
import kotlinx.android.synthetic.main.activity_main.*
import com.savegoldmaster.utils.permissionUtil.PermissionHelper
import android.view.*
import android.util.DisplayMetrics
import com.elvishew.xlog.XLog
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import android.app.Activity


class MainActivity : AppCompatActivity(), BottomNavigationBar.OnTabSelectedListener, PermissionInterface {

    private val mTitles = arrayOf("首页", "爱有金", "我的")
    private var mHomeFragment: HomeFragment? = null
    private var mMineFragment: MineFragment? = null
    private var mLoveGoldFragment: LoveGoldFragment? = null
    private var mFragmentManager: FragmentManager = supportFragmentManager
    private var userId: String? = null
    private var mPermissionHelper: PermissionHelper? = null


    companion object {
        fun start(context: Context) {
            context.startActivity(
                Intent(context, MainActivity::class.java)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.TranslucentTheme)
        setContentView(R.layout.activity_main)
        setWindowStatusBarColor(this, R.color.transparent, R.color.black)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        initViews()
        initWindows()
    }

    private fun initWindows() {
        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val height = wm.defaultDisplay.height
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        val layoutParams = mParent.layoutParams
        layoutParams.height = height
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        mParent.layoutParams = layoutParams

    }

    private fun initViews() {
        XLog.d("getScreenHeight" + getScreenHeight(this))
        XLog.d("getStatusBarHeight" + getStatusBarHeight())
        XLog.d("getNavigationBarHeight" + getNavigationBarHeight())
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
                BottomNavigationItem(R.mipmap.ic_home_gold_select, mTitles[1])
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

    //    获取顶部statusBar高度
    private fun getStatusBarHeight(): Int {
        val resources = this.getResources()
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    //获取底部navigationBar高度
    private fun getNavigationBarHeight(): Int {
        val resources = this.getResources()
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    //    获取屏幕高度
    fun getScreenHeight(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.heightPixels
    }

    //获取设备是否存在NavigationBar
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.resources
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {
            //do something
        }

        return hasNavigationBar
    }

    fun setWindowStatusBarColor(activity: Activity, statusBarColor: Int, navigationBarColor: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(statusBarColor)
                //底部导航栏
                window.navigationBarColor = activity.resources.getColor(navigationBarColor)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
