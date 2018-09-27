package org.store.android.rw.qbstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.MyOrderHistoryAdapter;
import org.store.android.rw.qbstore.data.ExchangeHistoryData;
import org.store.android.rw.qbstore.data.OrderListData;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单记录
 */
public class MyOrderHistoryActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    @BindView(R.id.stl_view)
    SegmentTabLayout mStlView;
    private MyOrderHistoryAdapter mAdapter;
    private String[] mTitles = {"买入交易", "卖出交易"};
    private Intent mIntent;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_order_history;
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyOrderHistoryAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        AppActionImpl.getInstance().GetBuyOrderList(this, UIUtils.getTokenData(), new BaseHttpCallbackListener<List<OrderListData>>() {
            @Override
            public Void onSuccess(List<OrderListData> data) {
                mAdapter.setNewData(data);
                return null;
            }
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        mStlView.setTabData(mTitles);
    }

    @Override
    public void initEvent() {
        mStlView.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                LogUtils.i(position);
                if (position == 0) {
                    AppActionImpl.getInstance().GetBuyOrderList(MyOrderHistoryActivity.this, UIUtils.getTokenData(), new BaseHttpCallbackListener<List<OrderListData>>() {
                        @Override
                        public Void onSuccess(List<OrderListData> data) {
                            mAdapter.setNewData(data);
                            return null;
                        }
                    });
                } else {
                    AppActionImpl.getInstance().GetSellOrderList(MyOrderHistoryActivity.this, UIUtils.getTokenData(), new BaseHttpCallbackListener<List<OrderListData>>() {
                        @Override
                        public Void onSuccess(List<OrderListData> data) {
                            mAdapter.setNewData(data);
                            return null;
                        }
                    });
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderListData data = mAdapter.getData().get(position);
        mIntent = new Intent(this, PayOrderActivity.class);
        mIntent.putExtra("title", getResources().getString(R.string.pay_order_activity_order_title) );
        mIntent.putExtra("OrderID", data.getOrderID());
        ActivityUtils.startActivity(this, mIntent);
    }


}
