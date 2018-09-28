package org.store.android.rw.haoduoyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.adapter.WalletStoreAdapter;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 积分商城
 */
public class IntegralStoreActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private WalletStoreAdapter mAdapter;
    @BindView(R.id.emptyView)
    public LinearLayout llyt;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WalletStoreAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BasePostData tokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("Category", "4");
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
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.first_fragment_jfsc));
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductDetailsData data = mAdapter.getData().get(position);
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra("ProductID", data.getProductID());
        ActivityUtils.startActivity(this, intent);
    }
}
