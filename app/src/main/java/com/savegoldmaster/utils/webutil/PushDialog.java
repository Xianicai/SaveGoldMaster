package com.savegoldmaster.utils.webutil;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.savegoldmaster.R;
import com.savegoldmaster.home.model.bean.BannerBean;
import com.savegoldmaster.utils.StringUtil;
import com.savegoldmaster.utils.glide.GlideImageView;

public class PushDialog {
    private Activity mContext;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mDialog;
    private View mView;
    private GlideImageView mImageView;
    private ImageView mImageClose;
    private BannerBean bean;

    /**
     * 按钮数量
     */
    private int mBtnNum = 1;
    /**
     * 是否已经初始化
     */
    private boolean mHasInit = false;

    private boolean mCancelable = true;

    private int mDialogWidth;

    public PushDialog(final Activity context) {
        this.mContext = context;
        if (mContext != null && !mContext.isFinishing()) {
            mBuilder = new AlertDialog.Builder(context);
            mView = LayoutInflater.from(context).inflate(R.layout.push_layout, null);
            mImageView = (GlideImageView) mView.findViewById(R.id.mPushView);
            mImageClose = (ImageView) mView.findViewById(R.id.mImageClose);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (StringUtil.INSTANCE.isNotEmpty(bean.getContent().get(0)
                            .getHrefUrl())) {
                        OutWebActivity.start(context, bean.getContent().get(0)
                                .getHrefUrl());
                    }
                    dismiss();
                }

            });
            mImageClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            mBuilder.setView(mView);

        }
    }


    /**
     * 显示Dialog
     */
    public void show() {
        // 有可能创建ConfirmDialog的时候，Activity已经被销毁，mView就会为null
        // 这个时候调用show直接return即可
        if (mView == null) {
            return;
        }
        if (!mHasInit) {
            mDialog = mBuilder.create();
            mDialog.setCancelable(mCancelable);
            mDialog.setCanceledOnTouchOutside(mCancelable);
            mHasInit = true;
        }
        try {
            if (mContext != null && !mContext.isFinishing()) {
                mDialog.show();
                // 设置Dialog宽度

                WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
//                lp.dimAmount = 1.0f;
//                lp.width = UIUtil.dip2px(mContext, 186);
//                lp.height = UIUtil.dip2px(mContext, 158);
                mDialog.getWindow().setAttributes(lp);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PushDialog setDate(BannerBean bean) {
        this.bean = bean;
        mImageView.setImage(bean.getContent().get(0).getImgUrl());
        return this;
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }

    /**
     * 隐藏Dialog
     */
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
