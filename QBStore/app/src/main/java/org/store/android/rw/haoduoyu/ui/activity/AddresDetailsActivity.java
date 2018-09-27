package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.GetUserInfoData;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddresDetailsActivity extends BaseActivity {
    @BindView(R.id.et_receiver_name)
    EditText mEtReceiverName;
    @BindView(R.id.et_receiver_phone)
    EditText mEtReceiverPhone;
    @BindView(R.id.et_province)
    EditText mEtProvince;
    @BindView(R.id.et_city)
    EditText mEtCity;
    @BindView(R.id.et_address)
    EditText mEtAddress;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private String mPhone;
    private GetUserInfoData mData;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_addres_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.addres_details_activity_title));

    }

    @Override
    public void initData() {
        mData = new Gson().fromJson(SPUtils.getInstance().getString(Constant.SP_USER_INFO), GetUserInfoData.class);
        mEtReceiverName.setText(mData.getReceiver());
        mEtReceiverPhone.setText(mData.getReceiverPhone());
        mEtProvince.setText(mData.getProvince());
        mEtCity.setText(mData.getCity());
        mEtAddress.setText(mData.getADDRESS());
    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtReceiverName.getText().toString().trim()) ||
                TextUtils.isEmpty(mEtProvince.getText().toString().trim()) ||
                TextUtils.isEmpty(mEtCity.getText().toString().trim()) ||
                TextUtils.isEmpty(mEtAddress.getText().toString().trim())) {
            ToastUtils.showShort("请填写完整后重试！");
            return;
        }

        mPhone = mEtReceiverPhone.getText().toString().trim();
        if (!RegexUtils.isMobileExact(mPhone)) {
            ToastUtils.showShort("手机号码不正确！");
            return;
        }
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("Receiver", mEtReceiverName.getText().toString().trim());
        map.put("Province", mEtProvince.getText().toString().trim());
        map.put("City", mEtCity.getText().toString().trim());
        map.put("ReceiverPhone", mPhone);
        map.put("ADDRESS", mEtAddress.getText().toString().trim());
        map.put("UserID", SPUtils.getInstance().getString(Constant.SP_USER_ID));
        tokenData.setData(map);
        AppActionImpl.getInstance().ChgAddress(this, tokenData, new BaseHttpCallbackListener<Void>() {
            @Override
            public Void onSuccess(Void data) {
                finish();
                return null;
            }
        });

    }
}
