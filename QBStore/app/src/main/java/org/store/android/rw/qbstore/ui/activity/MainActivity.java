package org.store.android.rw.qbstore.ui.activity;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OSUtils;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.base.AppManager;
import org.store.android.rw.qbstore.ui.fragment.MainFragment;
import org.store.android.rw.qbstore.widget.ThreadPoolManager;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    private ImmersionBar mImmersionBar;
    private static final String NAVIGATIONBAR_IS_MIN = "navigationbar_is_min";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();

        if (OSUtils.isEMUI3_1()) {
            //第一种
            getContentResolver().registerContentObserver(Settings.System.getUriFor
                    (NAVIGATIONBAR_IS_MIN), true, mNavigationStatusObserver);
        }
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_content, MainFragment.newInstance());
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }


    private ContentObserver mNavigationStatusObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            int navigationBarIsMin = Settings.System.getInt(getContentResolver(),
                    NAVIGATIONBAR_IS_MIN, 0);
            if (navigationBarIsMin == 1) {
                //导航键隐藏了
                mImmersionBar.transparentNavigationBar().init();
            } else {
                //导航键显示了
                mImmersionBar.navigationBarColor(android.R.color.black) //隐藏前导航栏的颜色
                        .fullScreen(false)
                        .init();
            }
        }
    };


    private int back_count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ThreadPoolManager.getLongRunThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    if (back_count == 0) {
                        ToastUtils.showShort("再点击一次退出程序");
                    }
                    back_count++;
                    if (back_count == 2) {
                        AppManager.getAppManager().AppExit(MainActivity.this);
                        finish();
                    }
                    SystemClock.sleep(2000);
                    back_count = 0;
                }
            });
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
