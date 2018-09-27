package org.store.android.rw.haoduoyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.GetUserInfoData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.GlideUtils;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 实名认证
 */
public class RealNameActivity extends BaseActivity {
    @BindView(R.id.et_real_name)
    EditText mEtRealName;
    @BindView(R.id.et_card_id)
    EditText mEtCardId;
    @BindView(R.id.iv_add_ico)
    ImageView mIvAddIco;
    @BindView(R.id.iv_card_id_pic)
    ImageView mIvCardIdPic;
    @BindView(R.id.rl_card_id_pic)
    RelativeLayout mRlCardIdPic;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private ImageItem mImageItem;
    private GetUserInfoData mGetUserInfoData;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_real_name;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.real_name_activity_title));

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        mGetUserInfoData = new Gson().fromJson(SPUtils.getInstance().getString(Constant.SP_USER_INFO), GetUserInfoData.class);
        if (mGetUserInfoData.getIsValid() == 1  ) {
            mBtnSubmit.setVisibility(View.GONE);
        }else{
            mBtnSubmit.setVisibility(View.VISIBLE);
        }

        if (mGetUserInfoData.getIsValid() != 0) {
            GlideUtils.loadImage(this, mGetUserInfoData.getCardIDPic(), mIvCardIdPic);
            mEtRealName.setText(mGetUserInfoData.getRealName());
            mEtRealName.setEnabled(false);
            mEtCardId.setText(mGetUserInfoData.getCardId());
            mEtCardId.setEnabled(false);

        }


    }

    @OnClick({R.id.rl_card_id_pic, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_card_id_pic:
                if (mGetUserInfoData.getIsValid() == 0)
                    ActivityUtils.startActivityForResult(this, ImageGridActivity.class, Constant.REAL_NAME_REQUEST_CODE);
                break;
            case R.id.btn_submit:
                if (StringUtils.isEmpty(mEtRealName.getText().toString().trim())) {
                    ToastUtils.showShort("请填写真实姓名");
                    return;
                }
                String mCardId = mEtCardId.getText().toString().trim();
                if (mCardId.length() == 15 || mCardId.length() == 18) {
                    if (mCardId.length() == 15) {
                        if (!RegexUtils.isIDCard15(mCardId)) {
                            ToastUtils.showShort("身份证号码不正确");
                            return;
                        }

                    } else {
                        if (!RegexUtils.isIDCard18(mCardId)) {
                            ToastUtils.showShort("身份证号码不正确");
                            return;
                        }
                    }
                } else {
                    ToastUtils.showShort("身份证号码不正确");
                    return;
                }

                if (mImageItem == null) {
                    ToastUtils.showShort("请上传身份证照");
                    return;
                }

                BasePostData tokenData = UIUtils.getTokenData();
                Map<String, String> map = new HashMap<>();
                map.put("UserID", SPUtils.getInstance().getString(Constant.SP_USER_ID));
                map.put("CardId", mCardId);
                map.put("RealName", mEtRealName.getText().toString().trim());
                map.put("CardIDPic", UIUtils.imageToBase64(mImageItem.path));
                tokenData.setData(map);
                AppActionImpl.getInstance().ValidInfo(this, tokenData, new BaseHttpCallbackListener<Void>() {
                    @Override
                    public Void onSuccess(Void data) {
                        finish();
                        return null;
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.REAL_NAME_REQUEST_CODE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mImageItem = images.get(0);
                GlideUtils.loadImage(this, mImageItem.path, mIvCardIdPic);
            }
        }
    }
}
