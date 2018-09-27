package org.store.android.rw.haoduoyu.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;

import org.store.android.rw.haoduoyu.adapter.ThirdTabFragmentAdapter;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.ui.activity.ProductDetailsActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.store.android.rw.haoduoyu.R;
import butterknife.BindView;

/**
 * Created by Sun on 2018/7/7.
 */

public class ThirdTabFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener,  SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    @BindView(R.id.srl_content_view)
    SwipeRefreshLayout mSrlContentView;
    private ThirdTabFragmentAdapter mAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_third_tab;
    }

    public static ThirdTabFragment newInstance() {
        Bundle args = new Bundle();
        ThirdTabFragment fragment = new ThirdTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView() {
        initAdapter();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void initAdapter() {
        mSrlContentView.setOnRefreshListener(this);
        mSrlContentView.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRlvContentView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ThirdTabFragmentAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductDetailsData data = mAdapter.getData().get(position);
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra("ProductID", data.getProductID());
        ActivityUtils.startActivity(getActivity(), intent);
    }


    private void loadData(){
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("Category", "2");
        tokenData.setData(map);
        AppActionImpl.getInstance().GetProductList(getActivity(), tokenData, new BaseHttpCallbackListener<List<ProductDetailsData>>() {
            @Override
            public Void onSuccess(List<ProductDetailsData> data) {
                LogUtils.i(data.size());
                mSrlContentView.setRefreshing(false);
                if (mAdapter != null)
                    mAdapter.setNewData(data);
                return null;
            }

            @Override
            public Void onFailure(String resultCode, String msg) {
                mSrlContentView.setRefreshing(false);
                return super.onFailure(resultCode, msg);
            }
        });
    }


    @Override
    public void onRefresh() {
        loadData();
    }
}
