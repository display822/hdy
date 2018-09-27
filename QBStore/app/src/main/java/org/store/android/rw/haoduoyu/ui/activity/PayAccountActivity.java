package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PayAccountActivity extends BaseActivity {
    @BindView(R.id.et_pay_account)
    EditText mEtPayAccount;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_pay_account;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.pay_account_activity_title));
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        mEtPayAccount.setText(getIntent().getStringExtra("Account"));
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mEtPayAccount.getText().toString().trim())) {
            ToastUtils.showShort("请输入支付宝账号");
            return;
        }
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("Account", mEtPayAccount.getText().toString().trim());
        tokenData.setData(map);
        AppActionImpl.getInstance().ChgAccount(this, tokenData, new BaseHttpCallbackListener<Void>() {
            @Override
            public Void onSuccess(Void data) {
                finish();
                return null;
            }
        });
    }
}
