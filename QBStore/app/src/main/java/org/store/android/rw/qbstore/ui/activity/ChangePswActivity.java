package org.store.android.rw.qbstore.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.base.Constant;
import org.store.android.rw.qbstore.data.BasePostData;
import org.store.android.rw.qbstore.data.ChangePswData;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.utils.MD5Util;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePswActivity extends BaseActivity {
    @BindView(R.id.et_old_psw)
    EditText mEtOldPsw;
    @BindView(R.id.et_new_psw)
    EditText mEtNewPsw;
    @BindView(R.id.et_psw)
    EditText mEtPsw;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_change_psw;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.change_psw_activity_title));

    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (StringUtils.isEmpty(mEtOldPsw.getText().toString().trim()) || StringUtils.isEmpty(mEtNewPsw.getText().toString().trim()) || StringUtils.isEmpty(mEtPsw.getText().toString().trim())) {
            ToastUtils.showShort("密码不能为空！");
            return;
        }

        if (!mEtNewPsw.getText().toString().trim().equals(mEtPsw.getText().toString().trim())) {
            ToastUtils.showShort("两次密码不一致！");
            return;
        }

        BasePostData tokenData = UIUtils.getTokenData();
        ChangePswData pswData = new ChangePswData();
        pswData.setUserCode(SPUtils.getInstance().getString(Constant.SP_USER_CODE));
        pswData.setOldPassword(MD5Util.encrypt(mEtOldPsw.getText().toString().trim()));
        pswData.setNewPassword(MD5Util.encrypt(mEtNewPsw.getText().toString().trim()));
        tokenData.setData(pswData);
        AppActionImpl.getInstance().chgPassWord(ChangePswActivity.this, tokenData, new BaseHttpCallbackListener<Void>() {
            @Override
            public Void onSuccess(Void data) {
                finish();
                return null;
            }

        });
    }
}
