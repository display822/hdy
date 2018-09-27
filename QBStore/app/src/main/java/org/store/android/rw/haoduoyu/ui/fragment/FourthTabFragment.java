package org.store.android.rw.haoduoyu.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.GetUserInfoData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.ui.activity.AddresDetailsActivity;
import org.store.android.rw.haoduoyu.ui.activity.ChangePswActivity;
import org.store.android.rw.haoduoyu.ui.activity.LoginActivity;
import org.store.android.rw.haoduoyu.ui.activity.MyWalletActivity;
import org.store.android.rw.haoduoyu.ui.activity.NotifyListActivity;
import org.store.android.rw.haoduoyu.ui.activity.PayAccountActivity;
import org.store.android.rw.haoduoyu.ui.activity.QRCodeActivity;
import org.store.android.rw.haoduoyu.ui.activity.RealNameActivity;
import org.store.android.rw.haoduoyu.ui.activity.UserInfoActivity;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;
import org.store.android.rw.haoduoyu.R;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Sun on 2018/7/7.
 */

public class FourthTabFragment extends BaseFragment {

    @BindView(R.id.tv_real_type)
    TextView mTvRealType;
    @BindView(R.id.rl_real_name)
    RelativeLayout mRlRealName;
    @BindView(R.id.rl_address)
    RelativeLayout mRlAddress;
    @BindView(R.id.rl_bonus)
    RelativeLayout mRlBonus;
    @BindView(R.id.rl_question)
    RelativeLayout mRlQuestion;
    @BindView(R.id.rl_change_psw)
    RelativeLayout mRlChangePsw;
    @BindView(R.id.rl_qrcode)
    RelativeLayout mRlQrcode;
    @BindView(R.id.rl_quit)
    RelativeLayout mRlQuit;
    @BindView(R.id.tv_leve)
    TextView mTvLeve;
    @BindView(R.id.tv_real_name)
    TextView mTvRealName;
    @BindView(R.id.tv_user_code)
    TextView mTvUserCode;
    @BindView(R.id.tv_create_date)
    TextView mTvCreateDate;
    @BindView(R.id.tv_signnum)
    TextView mTvSignNum;
    @BindView(R.id.rl_pay_address)
    RelativeLayout mRlPayAddress;

    private int mIsValid;
    private Intent mIntent;
    private GetUserInfoData mData;

    public static FourthTabFragment newInstance() {
        Bundle args = new Bundle();
        FourthTabFragment fragment = new FourthTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_fourth_tab;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("UserID", SPUtils.getInstance().getString(Constant.SP_USER_ID));
        tokenData.setData(map);
        AppActionImpl.getInstance().getUserInfo(getActivity(), tokenData, new BaseHttpCallbackListener<GetUserInfoData>() {

            @Override
            public Void onSuccess(GetUserInfoData data) {
                mData = data;
                SPUtils instance = SPUtils.getInstance();
                instance.put(Constant.SP_USER_INFO, new Gson().toJson(data));
                mIsValid = data.getIsValid();
                mTvLeve.setText(data.getLevel());
                mTvRealName.setText(data.getRealName());
                mTvUserCode.setText(data.getUserCode());
                mTvCreateDate.setText(data.getCreateDate());
                mTvSignNum.setText(data.getSignCount());
                mTvRealType.setText(getRealText(mIsValid));
                mTvRealType.setTextColor(ContextCompat.getColor(getActivity(), mIsValid == 0 ? R.color.rgb_999999 : R.color.rgb_dd3217));
                return null;
            }
        });
    }

    /**
     * 获取认证状态
     *
     * @param type
     * @return
     */
    private String getRealText(int type) {
        switch (type) {
            case 0:
                return "未实名";
            case 1:
                return "已认证";
            case 2:
                return " 认证中";

        }
        return "" + type;
    }

    @Override
    protected void initView() {


    }


    @OnClick({R.id.rl_real_name, R.id.rl_address, R.id.rl_bonus, R.id.rl_question, R.id.rl_change_psw, R.id.rl_qrcode, R.id.rl_quit,R.id.rl_vip_content, R.id.rl_pay_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_real_name:
                ActivityUtils.startActivity(getActivity(), RealNameActivity.class);
                break;
            case R.id.rl_address:
                ActivityUtils.startActivity(getActivity(), AddresDetailsActivity.class);
                break;
            case R.id.rl_bonus:
                ActivityUtils.startActivity(getActivity(), MyWalletActivity.class);
                break;
            case R.id.rl_question:
                ActivityUtils.startActivity(getActivity(), NotifyListActivity.class);
                break;
            case R.id.rl_change_psw:
                ActivityUtils.startActivity(getActivity(), ChangePswActivity.class);
                break;
            case R.id.rl_qrcode:
                ActivityUtils.startActivity(getActivity(), QRCodeActivity.class);
                break;
            case R.id.rl_quit:
                UIUtils.showTextDialog(getActivity(), "", "确认退出吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SPUtils.getInstance().clear();
                        getActivity().finish();
                        ActivityUtils.startActivity(getActivity(), LoginActivity.class);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                break;
            case R.id.rl_vip_content:
                if (mData != null) {
                    mIntent = new Intent(getActivity(), UserInfoActivity.class);
                    mIntent.putExtra("UserCode", mData.getUserCode());
                    mIntent.putExtra("UserID", mData.getUserID());
                    mIntent.putExtra("RealName", mData.getRealName());
                    mIntent.putExtra("CreateDate", mData.getCreateDate());
                    mIntent.putExtra("Level", mData.getLevel());
                    mIntent.putExtra("ReceiverPhone", mData.getReceiverPhone());
                    mIntent.putExtra("Status", mData.getStatus());
                    ActivityUtils.startActivity(getActivity(), mIntent);
                }

                break;
            case R.id.rl_pay_address:
                mIntent = new Intent(getActivity(), PayAccountActivity.class);
                mIntent.putExtra("Account", mData.getAccount());
                ActivityUtils.startActivity(getActivity(), mIntent);
                break;
        }
    }


}
