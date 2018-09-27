package org.store.android.rw.qbstore.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.MyWalletDetailAdapter;
import org.store.android.rw.qbstore.data.BasePostData;
import org.store.android.rw.qbstore.data.MyWalletDetail;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 钱包明细
 */
public class MyWalletDetailActivity extends BaseActivity {
    @BindView(R.id.tv_header_name)
    TextView mTvHeaderName;
    @BindView(R.id.tv_header_price)
    TextView mTvHeaderPrice;
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private MyWalletDetailAdapter mAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_wallet_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.my_wallet_detail_activity_title));
        mTvHeaderName.setText(getString(R.string.my_wallet_detail_activity_name));
        mTvHeaderPrice.setText(getIntent().getStringExtra("walletamount"));
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {
        BasePostData tokenData = UIUtils.getTokenData();
        AppActionImpl.getInstance().GetWalletDetail(this, tokenData, new BaseHttpCallbackListener<List<MyWalletDetail>>() {
            @Override
            public Void onSuccess(List<MyWalletDetail> data) {
                mAdapter.setNewData(data);
                return null;
            }
        });
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyWalletDetailAdapter();
        mRlvContentView.setAdapter(mAdapter);
    }
}
