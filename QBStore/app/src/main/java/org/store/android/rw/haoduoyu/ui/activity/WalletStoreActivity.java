package org.store.android.rw.haoduoyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.adapter.WalletStoreAdapter;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class WalletStoreActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    @BindView(R.id.emptyView)
    LinearLayout llyt;
    private WalletStoreAdapter mAdapter;

    @Override

    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.wallet_store_activity_title));
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("Category", "3");
        tokenData.setData(map);
        AppActionImpl.getInstance().GetProductList(this, tokenData, new BaseHttpCallbackListener<List<ProductDetailsData>>() {

            @Override
            public Void onSuccess(List<ProductDetailsData> data) {
                if (mAdapter != null)
                    mAdapter.setNewData(data);
                if(data.size()==0){
                    llyt.setVisibility(View.VISIBLE);
                }else{
                    llyt.setVisibility(View.GONE);
                }
                return null;
            }
        });

    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WalletStoreAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductDetailsData data = mAdapter.getData().get(position);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("ProductID", data.getProductID());
        ActivityUtils.startActivity(this, intent);
    }
}
