package org.store.android.rw.qbstore.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.adapter.GroupListAdapter;
import org.store.android.rw.qbstore.data.GroupListData;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private GroupListAdapter mAdapter;
    private int mType;
    private Intent mIntent;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        mType = getIntent().getIntExtra("type", 0);
        getToolbarTitle().setText(mType == 0 ? getString(R.string.group_list_activity_title) : getString(R.string.retail_list_activity_title));
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GroupListAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mType == 0){
            AppActionImpl.getInstance().GetReferee(this, UIUtils.getTokenData(), new BaseHttpCallbackListener<List<GroupListData>>() {
                @Override
                public Void onSuccess(List<GroupListData> data) {
                    mAdapter.setNewData(data);
                    return null;
                }
            });
        }else{
            AppActionImpl.getInstance().GetReferee2(this, UIUtils.getTokenData(), new BaseHttpCallbackListener<List<GroupListData>>() {
                @Override
                public Void onSuccess(List<GroupListData> data) {
                    mAdapter.setNewData(data);
                    return null;
                }
            });

        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GroupListData data = mAdapter.getData().get(position);
        mIntent = new Intent(GroupListActivity.this, UserInfoActivity.class);
        mIntent.putExtra("UserCode",data.getUserCode());
        mIntent.putExtra("UserID",data.getUserID());
        mIntent.putExtra("RealName",data.getRealName());
        mIntent.putExtra("CreateDate",data.getCreateDate());
        mIntent.putExtra("Level",data.getLevel());
        mIntent.putExtra("ReceiverPhone",data.getReceiverPhone());
        mIntent.putExtra("Amount",data.getAmount());
        mIntent.putExtra("Status",data.getStatus());
        ActivityUtils.startActivity(this, mIntent);
    }
}
