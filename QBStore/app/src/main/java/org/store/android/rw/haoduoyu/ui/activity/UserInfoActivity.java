package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;

import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.R;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.tv_user_code)
    TextView mTvUserCode;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.tv_create_date)
    TextView mTvCreateDate;
    @BindView(R.id.tv_Level)
    TextView mTvLevel;
    @BindView(R.id.tv_receiver_phone)
    TextView mTvReceiverPhone;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    @BindView(R.id.view_amount)
    View mViewAmount;
    private String mUserCode;
    private String mUserID;
    private String mRealName;
    private String mCreateDate;
    private String mLevel;
    private String mReceiverPhone;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        super.initData();
        mUserCode = getIntent().getStringExtra("UserCode");
        mUserID = getIntent().getStringExtra("UserID");
        mRealName = getIntent().getStringExtra("RealName");
        mCreateDate = getIntent().getStringExtra("CreateDate");
        mLevel = getIntent().getStringExtra("Level");
        mReceiverPhone = getIntent().getStringExtra("ReceiverPhone");
        mTvUserCode.setText(mUserCode + "");
        mTvRealName.setText(mRealName + "");
        mTvCreateDate.setText(mCreateDate + "");
        mTvLevel.setText(mLevel + "");
        mTvReceiverPhone.setText(mReceiverPhone + "");
        if (SPUtils.getInstance().getString(Constant.SP_USER_ID).equals(mUserID)) {
            mLlAmount.setVisibility(View.GONE);
            mViewAmount.setVisibility(View.GONE);
        } else {
            mTvAmount.setText(getIntent().getStringExtra("Amount"));
        }
        if (getIntent().getIntExtra("Status", 1) == 1) {
            mBtnSubmit.setVisibility(View.GONE);

        } else {
            mBtnSubmit.setVisibility(View.VISIBLE);

        }
//
//
//
//
//
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.user_info_activity_title));
    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(mUserID)) {
            BasePostData tokenData = UIUtils.getTokenData();
            Map<String, String> map = new HashMap<>();
            map.put("MemberUserID", mUserID);
            tokenData.setData(map);
            AppActionImpl.getInstance().UserActivation(this, tokenData, new BaseHttpCallbackListener<Void>() {
                @Override
                public Void onSuccess(Void data) {
                    finish();
                    return null;
                }
            });
        }
    }


}
