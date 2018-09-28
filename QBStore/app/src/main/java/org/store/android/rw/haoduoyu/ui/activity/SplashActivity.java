package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;

import org.store.android.rw.haoduoyu.R;

public class SplashActivity extends BaseActivity {
    @Override
    public int getContentViewResId() {
        //navigation透明
        View decorView = getWindow().getDecorView();// Hide both the navigation bar and the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
		//decorView.setSystemUiVisibility(uiOptions);
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.splash);
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

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
