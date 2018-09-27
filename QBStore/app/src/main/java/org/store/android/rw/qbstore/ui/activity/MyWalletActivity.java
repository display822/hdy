package org.store.android.rw.qbstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.MyWalletAdapter;
import org.store.android.rw.qbstore.base.Constant;
import org.store.android.rw.qbstore.data.GetUserInfoData;
import org.store.android.rw.qbstore.data.MyWalletData;
import org.store.android.rw.qbstore.widget.RecycleViewDivider;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWalletActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private MyWalletAdapter mAdapter;
    private List<MyWalletData> mData;

    private String[] title;
    private Intent mIntent;
    private GetUserInfoData mGetUserInfoData;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.my_wallet_activity_title));
        title = new String[]{getResources().getString(R.string.my_wallet_item_qbye), getResources().getString(R.string.my_integral_detail_activity_name)};
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        mGetUserInfoData = new Gson().fromJson(SPUtils.getInstance().getString(Constant.SP_USER_INFO), GetUserInfoData.class);
        if (mData == null)
            mData = new ArrayList<>();
        mData.add(new MyWalletData(title[0], mGetUserInfoData.getWalletAmount() + ""));
        mData.add(new MyWalletData(title[1], mGetUserInfoData.getScore() + ""));
        mAdapter.setNewData(mData);
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mRlvContentView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL, (int) getResources().getDimension(R.dimen.x2), ContextCompat.getColor(this, R.color.rgb_e6e6e6)));
        mAdapter = new MyWalletAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                mIntent = new Intent(this, MyWalletDetailActivity.class);
                mIntent.putExtra("walletamount",mGetUserInfoData.getWalletAmount() + "");
                ActivityUtils.startActivity(this, mIntent);
                break;
            case 1:
                mIntent = new Intent(this, MyIntegralDetailActivity.class);
                mIntent.putExtra("score",mGetUserInfoData.getScore() + "");
                ActivityUtils.startActivity(this,mIntent );
                break;
        }
    }
}
