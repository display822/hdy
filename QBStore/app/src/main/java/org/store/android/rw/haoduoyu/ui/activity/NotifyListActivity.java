package org.store.android.rw.haoduoyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.adapter.NotifyListAdapter;
import org.store.android.rw.haoduoyu.data.GetNoticeData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;

import java.util.List;

import butterknife.BindView;

public class NotifyListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rlv_content_view)
    RecyclerView mRlvContentView;
    private NotifyListAdapter mAdapter;
    private Intent mIntent;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initAdapter() {
        mRlvContentView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NotifyListAdapter();
        mRlvContentView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void initData() {

        AppActionImpl.getInstance().getNotice(NotifyListActivity.this, UIUtils.getTokenData( ), new BaseHttpCallbackListener<List<GetNoticeData>>() {
            @Override
            public Void onSuccess(List<GetNoticeData> data) {
                mAdapter.setNewData(data);
                return null;
            }
        });

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.notify_list_activity_title));

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        GetNoticeData data = mAdapter.getData().get(position);
        mIntent = new Intent(NotifyListActivity.this, NotifyDetailsActivity.class);
        mIntent.putExtra("title", data.getTitle());
        mIntent.putExtra("content", data.getNoticeContent());
        mIntent.putExtra("createtime", data.getCreateTime());
        ActivityUtils.startActivity(NotifyListActivity.this, mIntent);
    }
}
