package org.store.android.rw.haoduoyu.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.base.AppManager;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.LoginData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.ThreadPoolManager;
import org.store.android.rw.haoduoyu.widget.utils.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_welcome_text)
    TextView mTvWelcomeText;
    @BindView(R.id.iv_phone_ico)
    ImageView mIvPhoneIco;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.iv_phone_psw)
    ImageView mIvPhonePsw;
    @BindView(R.id.et_psw)
    EditText mEtPsw;
    @BindView(R.id.btn_submit)
    Button mBtSubmit;
    private DisplayMetrics mDm;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbar().setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        mImmersionBar.statusBarColor(R.color.transparent).init();
        mDm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDm);
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
//        SpannableString spannableString = new SpannableString("欢迎来好多鱼，选择 注册");
//        spannableString.setSpan(new AgreementClickText(LoginActivity.this, new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityUtils.startActivity(LoginActivity.this, RegisterActivity.class);
//            }
//        }), 12, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTvWelcomeText.setText(spannableString);
        mTvWelcomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        });
        mTvWelcomeText.setMovementMethod(LinkMovementMethod.getInstance());
        mTvWelcomeText.setHighlightColor(Color.TRANSPARENT);
    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String wh = mDm.widthPixels + "x" + mDm.heightPixels;
        AppActionImpl.getInstance().login(this, mEtPhone.getText().toString().trim(), MD5Util.encrypt(mEtPsw.getText().toString().trim()), wh, new BaseHttpCallbackListener<LoginData>() {
            @Override
            public Void onSuccess(LoginData data) {
                SPUtils instance = SPUtils.getInstance();
                instance.put(Constant.SP_TOKEN, data.getToken());
                instance.put(Constant.SP_USER_ID, data.getUserID());
                instance.put(Constant.SP_USER_CODE, data.getUserCode());
                ActivityUtils.startActivity(LoginActivity.this, MainActivity.class);
                finish();
                return null;
            }
        });

    }

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
                        AppManager.getAppManager().AppExit(LoginActivity.this);
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
