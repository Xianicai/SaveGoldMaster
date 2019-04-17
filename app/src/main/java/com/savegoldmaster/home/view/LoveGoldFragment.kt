package com.savegoldmaster.home.view

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.savegoldmaster.R
import com.savegoldmaster.base.BaseApplication
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.common.WebUrls
import com.savegoldmaster.home.presenter.UserPresenterImpl
import kotlinx.android.synthetic.main.fragment_love_gold.*

class LoveGoldFragment : BaseMVPFragment<UserPresenterImpl>() {
    companion object {
        fun newInstance(): LoveGoldFragment {
            return LoveGoldFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_love_gold
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(view: View?) {
        mWebView.loadUrl(WebUrls.AIYOUJIN)
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        var mWebSettings:WebSettings = mWebView.getSettings()
        //设置 缓存模式
        mWebSettings.cacheMode = WebSettings.LOAD_DEFAULT
        mWebSettings.pluginState = WebSettings.PluginState.ON
        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
        mWebSettings.allowFileAccess = true
        mWebSettings.defaultTextEncodingName = "UTF-8"

        // 支持js
        mWebSettings.javaScriptEnabled = true

        mWebSettings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        val appCachePath = BaseApplication.instance.cacheDir.absolutePath
        mWebSettings.setAppCachePath(appCachePath)
        // 自适应屏幕
        mWebSettings.useWideViewPort = true
        mWebSettings.loadWithOverviewMode = true

        mWebView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                } else {
                    if (progressBar.visibility == View.GONE)
                        progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                }
            }
        }
    }

    override fun createPresenter(): UserPresenterImpl {
        return UserPresenterImpl()

    }
}