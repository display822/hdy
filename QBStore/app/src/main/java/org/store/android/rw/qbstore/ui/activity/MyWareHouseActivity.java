package org.store.android.rw.qbstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.MyWareHouseAdapter;
import org.store.android.rw.qbstore.data.BasePostData;
import org.store.android.rw.qbstore.data.ProductDetailsData;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.RecycleViewDivider;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWareHouseActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private MyWareHouseAdapter mAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.my_warehouse_activity_title));
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }



    @Override
    protected void onResume() {
        super.onResume();
        BasePostData tokenData = UIUtils.getTokenData();
        AppActionImpl.getInstance().GetCampProductList(this, tokenData, new BaseHttpCallbackListener<List<ProductDetailsData>>() {
            @Override
            public Void onSuccess(List<ProductDetailsData> data) {
                mAdapter.setNewData(data);
                return null;
            }
        });
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mRlvContentView.addItemDecoration(new RecycleViewDivider(LinearLayoutManager.VERTICAL, (int) getResources().getDimension(R.dimen.x8), ContextCompat.getColor(this, R.color.rgb_f6f6f6)));
        mAdapter = new MyWareHouseAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ProductDetailsData data = mAdapter.getData().get(position);
        Intent intent = new Intent(MyWareHouseActivity.this, ProductDetailsActivity.class);
        intent.putExtra("ProductID", data.getProductID());
        ActivityUtils.startActivity(MyWareHouseActivity.this, intent);
    }
}
