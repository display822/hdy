package org.store.android.rw.haoduoyu.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.adapter.ThirdTabFragmentAdapter;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 新手区
 */
public class NeophyteActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    @BindView(R.id.srl_content_view)
    SwipeRefreshLayout mSrlContentView;
    @BindView(R.id.emptyView)
    LinearLayout emptyView;
    private ThirdTabFragmentAdapter mAdapter;

    @Override
    public void initAdapter() {
        mSrlContentView.setOnRefreshListener(this);
        mSrlContentView.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ThirdTabFragmentAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_neophyte;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.first_fragment_xsqd));
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }

    private  void  loadData(){
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("Category", "1");
        tokenData.setData(map);
        AppActionImpl.getInstance().GetProductList(this, tokenData, new BaseHttpCallbackListener<List<ProductDetailsData>>() {
            @Override
            public Void onSuccess(List<ProductDetailsData> data) {
                LogUtils.i(data.size());
                mSrlContentView.setRefreshing(false);
                if (mAdapter != null)
                    mAdapter.setNewData(data);
                if(data.size()==0){
                    emptyView.setVisibility(View.VISIBLE);
                }else{
                    emptyView.setVisibility(View.GONE);
                }
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductDetailsData data = mAdapter.getData().get(position);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("ProductID", data.getProductID());
        ActivityUtils.startActivity(this, intent);
    }


    @Override
    public void onRefresh() {
        loadData();
    }
}
