package org.store.android.rw.qbstore.widget.utils;


import android.app.Activity;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;

import org.store.android.rw.qbstore.base.AppManager;


/**
 * @author：Sun
 * @类名：DialogUtils.java
 * @时间：2015-8-19 下午4:52:09
 * @描述：Dialog工具
 */
public class LoadingDialogUtils {

    public static MaterialDialog mDialog;


    public static void showLoadingDialog(Context context, String msg) {
        if (context == null) {
            context = AppManager.getAppManager().currentActivity();
        }

        try {
            if (mDialog == null && context != null) {
                mDialog = new MaterialDialog.Builder(context)
                        .content(msg)
                        .progress(true, 0)
                        .cancelable(false)
                        .progressIndeterminateStyle(false).build();
            }
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
                mDialog.setCancelable(false);
            }
        } catch (Exception e) {

        }

    }

    public static void hideLoadingDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                LogUtils.e("dialog error" + e.toString());
            }
            mDialog = null;
        }
    }
}
