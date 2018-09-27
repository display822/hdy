package org.store.android.rw.qbstore.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.TimeCount;
import org.store.android.rw.qbstore.widget.utils.MD5Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.iv_phone_ico)
    ImageView mIvPhoneIco;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.iv_smscode_psw)
    ImageView mIvSmscodePsw;
    @BindView(R.id.et_smscode)
    EditText mEtSmscode;
    @BindView(R.id.bt_smscode)
    Button mBtSmscode;
    @BindView(R.id.iv_phone_psw)
    ImageView mIvPhonePsw;
    @BindView(R.id.et_psw)
    EditText mEtPsw;
    @BindView(R.id.iv_phone_new_psw)
    ImageView mIvPhoneNewPsw;
    @BindView(R.id.et_new_psw)
    EditText mEtNewPsw;
    @BindView(R.id.iv_user_name)
    ImageView mIvUserName;
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.iv_recommend_phone)
    ImageView mIvRecommendPhone;
    @BindView(R.id.et_recommend_phone)
    EditText mEtRecommendPhone;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.tv_agreement)
    TextView mTvAgreement;
    private String mPhone;
    private TimeCount mTimeCount;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbar().setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        mImmersionBar.statusBarColor(R.color.transparent).init();
        mTimeCount = new TimeCount(60000, 1000, mBtSmscode);

    }


    @OnClick({R.id.bt_smscode, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_smscode:
                mPhone = mEtPhone.getText().toString().trim();
                if (!RegexUtils.isMobileExact(mPhone)) {
                    ToastUtils.showShort("手机号码不正确！");
                    return;
                }
                AppActionImpl.getInstance().getsmsValidCode(this, mPhone + "", new BaseHttpCallbackListener<Void>() {
                    @Override
                    public Void onSuccess(Void data) {
                        mTimeCount.start();
                        return null;
                    }
                });


                break;
            case R.id.btn_submit:
                if (!RegexUtils.isMobileExact(mEtPhone.getText().toString().trim())) {
                    ToastUtils.showShort("手机号码不正确！");
                    return;
                }

                if (StringUtils.isEmpty(mEtSmscode.getText().toString().trim())) {
                    ToastUtils.showShort("验证码不正确！");
                    return;
                }

                if (StringUtils.isEmpty(mEtPsw.getText().toString().trim())) {
                    ToastUtils.showShort("密码不正确！");
                    return;
                }

                if (StringUtils.isEmpty(mEtNewPsw.getText().toString().trim())) {
                    ToastUtils.showShort("密码不正确！");
                    return;
                }

                if (!mEtPsw.getText().toString().trim().equals(mEtNewPsw.getText().toString().trim())) {
                    ToastUtils.showShort("两次密码不一致！");
                    return;
                }


                if (StringUtils.isEmpty(mEtUserName.getText().toString().trim())) {
                    ToastUtils.showShort("真实姓名不正确！");
                    return;
                }


                if (StringUtils.isEmpty(mEtRecommendPhone.getText().toString().trim())) {
                    ToastUtils.showShort("推广人不正确！");
                    return;
                }


//                AppActionImpl.getInstance().validCode(this, mEtSmscode.getText().toString().trim(), new BaseHttpCallbackListener<Void>() {
//                    @Override
//                    public Void onSuccess(Void data) {
//
//
//                        return null;
//                    }
//                });
                AppActionImpl.getInstance().register(
                        RegisterActivity.this,
                        mEtPhone.getText().toString().trim(),
                        MD5Util.encrypt(mEtPsw.getText().toString()),
                        mEtUserName.getText().toString().trim(),
                        mEtRecommendPhone.getText().toString().trim() ,
                        mEtSmscode.getText().toString().trim() , new BaseHttpCallbackListener<Void>() {
                            @Override
                            public Void onSuccess(Void data) {
                                finish();
                                return null;
                            }
                        });
                break;
        }
    }


}
