package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import org.store.android.rw.haoduoyu.R;


public class MyCodeActivity extends BaseActivity {
    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText("我的二维码");
    }
}
