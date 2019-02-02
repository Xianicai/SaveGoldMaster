package com.savegoldmaster.base.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import com.savegoldmaster.base.presenter.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import kotlin.jvm.internal.Intrinsics;

/**
 * ZY:基础的Aty,实现简单的接口，方法
 * Created by zhanglibin.
 */
public abstract class BaseMVPActivity<T extends BasePresenter> extends RxFragmentActivity implements BaseView {
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutId());
//        setWindowStatusBarColor(this);
        initPresenter();
        //初始化控件
        initViews(savedInstanceState);
    }

    public void initWindow() {
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

    @SuppressLint("ResourceType")
    public final void setWindowStatusBarColor(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(Integer.MIN_VALUE);
                Intrinsics.checkExpressionValueIsNotNull(window, "window");
                window.setStatusBarColor(activity.getResources().getColor(window.getNavigationBarColor()));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
