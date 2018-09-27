package org.store.android.rw.haoduoyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.adapter.ExchangeHistoryAdapter;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.data.ExchangeHistoryData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 兑换记录
 */
public class ExchangeHistoryActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    @BindView(R.id.stl_view)
    SegmentTabLayout mStlView;
    private String[] mTitles = {"积分兑换", "库存兑换"};
    private List<ExchangeHistoryData> mIntegralData;
    private List<ExchangeHistoryData> mInventoryData;
    private ExchangeHistoryAdapter mExchangeHistoryAdapter;
    private Intent mIntent;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_order_history;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        mStlView.setTabData(mTitles);
    }

    @Override
    public void initData() {
        mIntegralData = new ArrayList<>();
        mInventoryData = new ArrayList<>();

        AppActionImpl.getInstance().GetExchangeDetail(this, UIUtils.getTokenData(), new BaseHttpCallbackListener<List<ExchangeHistoryData>>() {
            @Override
            public Void onSuccess(List<ExchangeHistoryData> data) {
                for (ExchangeHistoryData exchangeHistoryData : data) {
                    if (exchangeHistoryData.getExchangeType() == 0) {
                        mIntegralData.add(exchangeHistoryData);
                    } else {
                        mInventoryData.add(exchangeHistoryData);
                    }
                }
                mExchangeHistoryAdapter.setNewData(mIntegralData);

                return null;
            }
        });
    }

    @Override
    public void initEvent() {
        mStlView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 0) {
                    mExchangeHistoryAdapter.setNewData(mIntegralData);
                } else {
                    mExchangeHistoryAdapter.setNewData(mInventoryData);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mExchangeHistoryAdapter = new ExchangeHistoryAdapter();
        mRlvContentView.setAdapter(mExchangeHistoryAdapter);
        mExchangeHistoryAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        ExchangeHistoryData exchangeHistoryData = mExchangeHistoryAdapter.getData().get(position);
//        mIntent = new Intent(this, PayOrderActivity.class);
//        mIntent.putExtra("title", getResources().getString(R.string.pay_order_activity_order_title) );
//        mIntent.putExtra("OrderID", exchangeHistoryData.getOrderID());
//        ActivityUtils.startActivity(this, mIntent);
    }
}
