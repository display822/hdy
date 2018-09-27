package org.store.android.rw.qbstore.ui.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.SecondTabFragmentAdapter;
import org.store.android.rw.qbstore.data.SecondTabData;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.ui.activity.GroupListActivity;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Sun on 2018/7/7.
 */

public class SecondTabFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.tv_history_profit)
    TextView mTvHistoryProfit;
    @BindView(R.id.tv_profit_num)
    TextView mTvProfitNum;
    @BindView(R.id.rl_header_content)
    RelativeLayout mRlHeaderContent;
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private SecondTabFragmentAdapter mAdapter;

    private List<String> mData;

    @Override
    protected int setLayoutId() {

        return R.layout.fragment_second_tab;
    }

    public static SecondTabFragment newInstance() {
        Bundle args = new Bundle();
        SecondTabFragment fragment = new SecondTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        AppActionImpl.getInstance().GetReferee3(getActivity(), UIUtils.getTokenData(), new BaseHttpCallbackListener<SecondTabData>() {
            @Override
            public Void onSuccess(SecondTabData data) {
                mTvProfitNum.setText(data.getAmount() + "");
                mData = new ArrayList<>();
                mData.add(data.getU1Count() + "");
                mData.add(data.getU2Count() + "");
                mAdapter.setNewData(mData);
                return null;
            }
        });
    }


    @Override
    protected void initView() {

        initAdapter();


    }

    private void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SecondTabFragmentAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent =new Intent(getActivity(), GroupListActivity.class);
        intent.putExtra("type",position==0? 0 :1);
        ActivityUtils.startActivity(getActivity(),intent);
    }
}
