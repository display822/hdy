package org.store.android.rw.qbstore.net;


import android.app.Activity;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.store.android.rw.qbstore.MyApplication;
import org.store.android.rw.qbstore.base.Constant;
import org.store.android.rw.qbstore.ui.activity.LoginActivity;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

/**
 * @author：Sun
 * @时间：2015/10/26 0026
 * @描述：TODO
 */
public abstract class BaseHttpCallbackListener<T> {

    public abstract Void onSuccess(T data);

    public Void onFailure(String resultCode, String msg) {
        try {
            LogUtils.i(resultCode);
            if (resultCode.equals("007")) {
                LogUtils.i(resultCode);
                if (!TextUtils.isEmpty(SPUtils.getInstance().getString(Constant.SP_TOKEN))) {
                    LogUtils.i(resultCode);
                    SPUtils.getInstance().clear();
                    Activity topActivity = ActivityUtils.getTopActivity();
                    ActivityUtils.startActivity(topActivity, LoginActivity.class);
                    topActivity.finish();
                    ActivityUtils.finishAllActivitiesExceptNewest();
                }

            }
            if (!TextUtils.isEmpty(msg))
                ToastUtils.showShort(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
