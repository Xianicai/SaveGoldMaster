package com.savegoldmaster.utils.webutil;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.webkit.*;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.savegoldmaster.R;
import com.savegoldmaster.account.LoginActivity;
import com.savegoldmaster.base.BaseApplication;
import com.savegoldmaster.utils.ChannelUtil;
import com.savegoldmaster.utils.LocationUtils;
import com.savegoldmaster.utils.SharedPreferencesHelper;
import com.savegoldmaster.utils.rxbus.EventConstant;
import com.savegoldmaster.utils.rxbus.RxBus;
import com.savegoldmaster.utils.rxbus.RxEvent;
import com.savegoldmaster.utils.view.LoadingDialog;

import java.io.File;

public class OutWebActivity extends AppCompatActivity {

    private ValueCallback<Uri> mUploadMessage;// 表单的数据信息
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;// 表单的结果回调
    private Uri imageUri;
    private Uri[] results;

    WebView mWebview;
    WebSettings mWebSettings;
    TextView beginLoading, endLoading, loading, mtitle;
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private LoadingDialog dialog;
    private ProgressBar progressBar;

    public static void start(Context context, String url) {
        context.startActivity(new Intent(context, OutWebActivity.class)
                .putExtra("url", url));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTheme(R.style.TranslucentTheme);
        setContentView(R.layout.activity_out_web);
        setWindowStatusBarColors(this);
        String url = getIntent().getStringExtra("url");

        mWebview = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        beginLoading = (TextView) findViewById(R.id.text_beginLoading);
        endLoading = (TextView) findViewById(R.id.text_endLoading);
        loading = (TextView) findViewById(R.id.text_Loading);
        mtitle = (TextView) findViewById(R.id.title);
        mWebview.loadUrl(url);
        //设置不用系统浏览器打开,直接显示在当前Webview
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebSettings = mWebview.getSettings();
        mWebview.addJavascriptInterface(new JSInterface(), "Android");
        //设置 缓存模式
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebSettings.setPluginState(WebSettings.PluginState.ON);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setDefaultTextEncodingName("UTF-8");

        // 支持js
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setGeolocationEnabled(true);
        mWebSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
        // 自适应屏幕
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);

        mWebSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        mWebview.setWebChromeClient(new WebChromeClient() {
            //配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });
        //设置WebViewClient类
        mWebview.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                beginLoading.setText("开始加载了");
//                if (dialog == null) {
//                    dialog = new LoadingDialog(OutWebActivity.this);
//                }
//                dialog.show();

            }

            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                endLoading.setText("结束加载了");
//                if (dialog != null) {
//                    dialog.dismiss();
//                }

            }
        });
//
        //处理h5调用相机相册
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                imageUri = AppUtils.takePhoto(OutWebActivity.this, FILECHOOSER_RESULTCODE);
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                imageUri = AppUtils.takePhoto(OutWebActivity.this, FILECHOOSER_RESULTCODE);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                imageUri = AppUtils.takePhoto(OutWebActivity.this, FILECHOOSER_RESULTCODE);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                imageUri = AppUtils.takePhoto(OutWebActivity.this, FILECHOOSER_RESULTCODE);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
                //return true;
            }
        });
        //禁止长按
        mWebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();

            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }


    private final class JSInterface {
        /**
         * 注意这里的@JavascriptInterface注解， target是4.2以上都需要添加这个注解，否则无法调用
         */
        @JavascriptInterface
        public String getUserId() {
            return new SharedPreferencesHelper(BaseApplication.Companion.getInstance(), "UserBean").getSharedPreference("userId", "").toString().trim();

        }

        @JavascriptInterface
        public String getToken() {
            return new SharedPreferencesHelper(BaseApplication.Companion.getInstance(), "UserBean").getSharedPreference("token", "").toString().trim();

        }

        @JavascriptInterface
        public void closeOutWeb() {
            RxBus.getDefault().post(new RxEvent(EventConstant.USER_LOGIN, LoginActivity.Companion.getACCOUNT_LOGIN()));
            finish();
        }

        @JavascriptInterface
        public void telephone(String telephone) {
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


        @JavascriptInterface
        public void logout() {
            RxBus.getDefault().post(new RxEvent(EventConstant.OUT_LOGIN, ""));
            finish();
        }

        @JavascriptInterface
        public String getLocation() {
            Location location = LocationUtils.getInstance(OutWebActivity.this).showLocation();
            return new Gson().toJson(location);
        }

        @JavascriptInterface
        public String getSource() {
//            return WalleChannelReader.getChannel(getApplicationContext());
            return ChannelUtil.getChannelName(BaseApplication.Companion.getInstance());
        }

        @JavascriptInterface
        public void toNative(int type, String url) {
            switch (type) {
                case 1001:
                    LoginActivity.Companion.start(OutWebActivity.this, url);
                    break;
                case 1002:
                    RxBus.getDefault().post(new RxEvent(EventConstant.USER_LOGIN, LoginActivity.Companion.getACCOUNT_LOGIN()));
                    break;
                case 1003:
                    RxBus.getDefault().post(new RxEvent(EventConstant.REFRESH_MSG, LoginActivity.Companion.getACCOUNT_LOGIN()));
                    break;


            }

        }

        @JavascriptInterface
        public String backPerInfo() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(OutWebActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(OutWebActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    return "OK";
                } else {
                    return "NO";
                }
            }
            return "OK";
        }

        @JavascriptInterface
        public void openPhotoPermession() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(OutWebActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OutWebActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                }
                if (ContextCompat.checkSelfPermission(OutWebActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OutWebActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 400);
                }
            }
        }
    }


    private void setWindowStatusBarColors(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(window.getNavigationBarColor());
                //底部导航栏
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (grantResults != null && grantResults.length > 0) {
                doNext(requestCode, grantResults);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(OutWebActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OutWebActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 400);
                }
            } else {
                //MyToast.makeText(getApplicationContext(), "请在应用权限管理中打开“相机”访问权限！", MyToast.LENGTH_LONG).show();
            }
        } else if (1 == requestCode) {
            /*if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateManager.showDownloadDialog("1");
            } else {
                MyToast.makeText(getApplicationContext(), "请在应用权限管理中打开“读写手机存储”访问权限！", MyToast.LENGTH_LONG).show();
            }*/
        } else if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(getApplicationContext(), "请在应用权限管理中打开“电话”访问权限！", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == 400) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(OutWebActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(OutWebActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
                }
            } else {
                //MyToast.makeText(getApplicationContext(), "请在应用权限管理中打开“读写手机存储”访问权限！", MyToast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage && null == mUploadCallbackAboveL)
                return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (mUploadMessage != null) {
                if (result != null) {
                    String path = AppUtils.getPath(getApplicationContext(),
                            result);
                    Uri uri = Uri.fromFile(new File(path));
                    mUploadMessage
                            .onReceiveValue(uri);
                } else {
                    mUploadMessage.onReceiveValue(imageUri);
                }
                mUploadMessage = null;
            }
        }
    }

    @SuppressWarnings("null")
    @TargetApi(Build.VERSION_CODES.BASE)
    public void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode != FILECHOOSER_RESULTCODE
                || mUploadCallbackAboveL == null) {
            return;
        }
        results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                results = new Uri[]{imageUri};
            } else {
                String dataString = data.getDataString();
                ClipData clipData = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    clipData = data.getClipData();
                }
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        if (results != null) {
            mUploadCallbackAboveL.onReceiveValue(results);
            mUploadCallbackAboveL = null;
        } else {
            //results = new Uri[]{imageUri};
            mUploadCallbackAboveL.onReceiveValue(null);
            mUploadCallbackAboveL = null;
        }
        return;
    }
}
