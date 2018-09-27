package org.store.android.rw.qbstore.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.FirstTabFragmentAdapter;
import org.store.android.rw.qbstore.data.BasePostData;
import org.store.android.rw.qbstore.data.OrderListData;
import org.store.android.rw.qbstore.data.SignData;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.ui.activity.EncryptActivity;
import org.store.android.rw.qbstore.ui.activity.ExchangeHistoryActivity;
import org.store.android.rw.qbstore.ui.activity.IntegralStoreActivity;
import org.store.android.rw.qbstore.ui.activity.MyOrderHistoryActivity;
import org.store.android.rw.qbstore.ui.activity.MyWalletActivity;
import org.store.android.rw.qbstore.ui.activity.MyWareHouseActivity;
import org.store.android.rw.qbstore.ui.activity.NeophyteActivity;
import org.store.android.rw.qbstore.ui.activity.NotifyListActivity;
import org.store.android.rw.qbstore.ui.activity.PayOrderActivity;
import org.store.android.rw.qbstore.ui.activity.QRCodeActivity;
import org.store.android.rw.qbstore.ui.activity.WalletStoreActivity;
import org.store.android.rw.qbstore.ui.activity.WebViewActivity;
import org.store.android.rw.qbstore.widget.RecycleViewDivider;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Sun on 2018/7/7.
 */

public class FirstTabFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    @BindView(R.id.ll_my_wallet_content)
    LinearLayout mLlMyWalletContent;
    @BindView(R.id.ll_my_warehouse_content)
    LinearLayout mLlMyWarehouseContent;
    private FirstTabFragmentAdapter mAdapter;
    private ViewHolder mHeadHolder;
    private Intent mIntent;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_first_tab;
    }

    public static FirstTabFragment newInstance() {
        Bundle args = new Bundle();
        FirstTabFragment fragment = new FirstTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void initData() {
        super.initData();
        AppActionImpl.getInstance().GetSign(getActivity(), UIUtils.getTokenData(), new BaseHttpCallbackListener<SignData>() {
            @Override
            public Void onSuccess(SignData data) {
                mHeadHolder.mTvSignIn.setText(data.getResult() == 1 ? UIUtils.getResString(R.string.first_fragment_jrwqd) : UIUtils.getResString(R.string.first_fragment_jrqd));
                mHeadHolder.mLlSignInContent.setEnabled(data.getResult() == 1);
                return null;
            }
        });



    }


    @Override
    public void onResume() {
        super.onResume();
        AppActionImpl.getInstance().GetOrderList(getActivity(), UIUtils.getTokenData(), new BaseHttpCallbackListener<List<OrderListData>>() {
            @Override
            public Void onSuccess(List<OrderListData> data) {
                if (mAdapter != null)
                    mAdapter.setNewData(data);
                return null;
            }
        });
    }

    @Override
    protected void initView() {
        initAdapte();
        initEvent();
    }

    private void initEvent() {
        mHeadHolder.mLlWalletStore.setOnClickListener(this);
        mHeadHolder.mLlIntegralStore.setOnClickListener(this);
        mHeadHolder.mLlNeophyteContent.setOnClickListener(this);
        mHeadHolder.mLlSignInContent.setOnClickListener(this);
        mHeadHolder.mRlTransactionHistory.setOnClickListener(this);
        mHeadHolder.mRlExchangeHistory.setOnClickListener(this);
        mHeadHolder.mRlNotify.setOnClickListener(this);
        mHeadHolder.mRlQrcode.setOnClickListener(this);
    }


    private void initAdapte() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FirstTabFragmentAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.item_first_fragment_header, (ViewGroup) mRlvContentView.getParent(), false);
        mHeadHolder = new ViewHolder(headView);
        mAdapter.addHeaderView(headView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //交易记录
            case R.id.rl_transaction_history:

                ActivityUtils.startActivity(getActivity(), MyOrderHistoryActivity.class);
                break;
            //钱包商城
            case R.id.ll_wallet_store:
                ActivityUtils.startActivity(getActivity(), WalletStoreActivity.class);
                break;
            case R.id.rl_notify:
                ActivityUtils.startActivity(getActivity(), NotifyListActivity.class);
                break;
            case R.id.ll_integral_store:
                ActivityUtils.startActivity(getActivity(), IntegralStoreActivity.class);
                break;
            case R.id.ll_neophyte_content:
                ActivityUtils.startActivity(getActivity(), NeophyteActivity.class);
                break;
            case R.id.rl_exchange_history:
                ActivityUtils.startActivity(getActivity(), ExchangeHistoryActivity.class);
                break;
            case R.id.ll_sign_in_content:
                AppActionImpl.getInstance().ExecSign(getActivity(), UIUtils.getTokenData(), new BaseHttpCallbackListener<Void>() {
                    @Override
                    public Void onSuccess(Void data) {
                        mHeadHolder.mTvSignIn.setText(UIUtils.getResString(R.string.first_fragment_jrqd));
                        mHeadHolder.mLlSignInContent.setEnabled(false);
                        return null;
                    }
                });
                break;
            case R.id.rl_qrcode:
                ActivityUtils.startActivity(getActivity(), QRCodeActivity.class);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OrderListData data = mAdapter.getData().get(position);
        mIntent = new Intent(getActivity(), PayOrderActivity.class);
        mIntent.putExtra("title", data.getOrderType() == 0 ? getResources().getString(R.string.pay_order_activity_pay_title) : getResources().getString(R.string.pay_order_activity_order_title));
        mIntent.putExtra("OrderID", data.getOrderID());
        ActivityUtils.startActivity(getActivity(), mIntent);
    }


    @OnClick({R.id.ll_my_wallet_content, R.id.ll_my_warehouse_content})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_my_wallet_content:
                ActivityUtils.startActivity(getActivity(), MyWalletActivity.class);
                break;
            case R.id.ll_my_warehouse_content:
                ActivityUtils.startActivity(getActivity(), MyWareHouseActivity.class);
                break;
        }
    }


    static class ViewHolder {
        @BindView(R.id.ll_wallet_store)
        LinearLayout mLlWalletStore;
        @BindView(R.id.ll_integral_store)
        LinearLayout mLlIntegralStore;
        @BindView(R.id.ll_neophyte_content)
        LinearLayout mLlNeophyteContent;
        @BindView(R.id.ll_sign_in_content)
        LinearLayout mLlSignInContent;
        @BindView(R.id.tv_transaction)
        TextView mTvTransaction;
        @BindView(R.id.rl_transaction_history)
        RelativeLayout mRlTransactionHistory;
        @BindView(R.id.tv_exchange)
        TextView mTvExchange;
        @BindView(R.id.rl_exchange_history)
        RelativeLayout mRlExchangeHistory;
        @BindView(R.id.tv_notify)
        TextView mTvNotify;
        @BindView(R.id.rl_notify)
        RelativeLayout mRlNotify;
        @BindView(R.id.tv_qrcode)
        TextView mTvQrcode;
        @BindView(R.id.tv_sign_in)
        TextView mTvSignIn;
        @BindView(R.id.rl_qrcode)
        RelativeLayout mRlQrcode;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}

