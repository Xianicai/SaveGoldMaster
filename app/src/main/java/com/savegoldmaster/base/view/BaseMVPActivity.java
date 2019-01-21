package com.savegoldmaster.base.view;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.savegoldmaster.base.presenter.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

/**
 * ZY:基础的Aty,实现简单的接口，方法
 * Created by zhanglibin.
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends RxFragmentActivity implements BaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        initPresenter();
        //初始化控件
        initViews(savedInstanceState);
    }

    protected void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    public abstract int getLayoutId();

    /**
     * 创建一个Presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    public abstract void initViews(Bundle savedInstanceState);

    @Override
    public void showProgress() {
    }

    @Override
    public void showProgress(String message) {

    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showMsg(String msg) {

    }


    /**
     * 全透状态栏
     */
    public void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 半透明状态栏
     */
    public void setHalfTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 如果需要内容紧贴着StatusBar
     * 应该在对应的xml布局文件中，设置根布局fitsSystemWindows=true。
     */
    private View contentViewGroup;

    public void setFitSystemWindow(boolean fitSystemWindow) {
        if (contentViewGroup == null) {
            contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        }
        contentViewGroup.setFitsSystemWindows(fitSystemWindow);
    }

    /**
     * 为了兼容4.4的抽屉布局->透明状态栏
     */
//    public void setDrawerLayoutFitSystemWindow() {
//        if (Build.VERSION.SDK_INT == 19) {//19表示4.4
//            int statusBarHeight = getStatusHeight(this);
//            if (contentViewGroup == null) {
//                contentViewGroup = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//            }
//            if (contentViewGroup instanceof DrawerLayout) {
//                DrawerLayout drawerLayout = (DrawerLayout) contentViewGroup;
//                drawerLayout.setClipToPadding(true);
//                drawerLayout.setFitsSystemWindows(false);
//                for (int i = 0; i < drawerLayout.getChildCount(); i++) {
//                    View child = drawerLayout.getChildAt(i);
//                    child.setFitsSystemWindows(false);
//                    child.setPadding(0,statusBarHeight, 0, 0);
//                }
//
//            }
//        }
//    }

}
