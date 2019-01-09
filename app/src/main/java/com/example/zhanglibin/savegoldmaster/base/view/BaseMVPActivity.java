package com.example.zhanglibin.savegoldmaster.base.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.zhanglibin.savegoldmaster.base.presenter.BasePresenter;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

}
