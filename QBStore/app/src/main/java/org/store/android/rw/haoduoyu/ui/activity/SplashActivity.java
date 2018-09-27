package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ActivityUtils;

public class SplashActivity extends BaseActivity {
    @Override
    public int getContentViewResId() {
        return 0;
    }

    @Override
    public void initData() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityUtils.startActivity(SplashActivity.this, WelcomeActivity.class);
                finish();

            }
        }, 1500);

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //navigation透明

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}