package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.GetUserInfoData;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.widget.utils.CodeUtils;

import butterknife.BindView;

public class QRCodeActivity extends BaseActivity {
    @BindView(R.id.iv_my_code)
    ImageView mIvMyCode;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_qr_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.qr_code_activity_title));
        GetUserInfoData data = new Gson().fromJson(SPUtils.getInstance().getString(Constant.SP_USER_INFO), GetUserInfoData.class);
        if (!TextUtils.isEmpty(data.getRegisterLink())){
            int wh = (int) getResources().getDimension(R.dimen.y140);
            mIvMyCode.setImageBitmap(CodeUtils.createImage( data.getRegisterLink(), wh, wh, null));


        }

    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        super.initData();
    }


}
