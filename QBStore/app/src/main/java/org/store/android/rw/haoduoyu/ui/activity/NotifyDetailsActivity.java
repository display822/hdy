package org.store.android.rw.haoduoyu.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;


import org.store.android.rw.haoduoyu.R;

import butterknife.BindView;

public class NotifyDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_details_title)
    TextView mTvDetailsTitle;
    @BindView(R.id.tv_details_date)
    TextView mTvDetailsDate;
    @BindView(R.id.tv_details_content)
    TextView mTvDetailsContent;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_notify_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getString(R.string.notify_list_activity_title));
    }

    @Override
    public void initData() {
        if (!StringUtils.isEmpty(getIntent().getStringExtra("title"))) {
            String title = getIntent().getStringExtra("title");
            String content = getIntent().getStringExtra("content");
            String createtime = getIntent().getStringExtra("createtime");
            mTvDetailsTitle.setText(title);
            mTvDetailsDate.setText(createtime);
            mTvDetailsContent.setText(Html.fromHtml(content,null,null));
        }
    }
}
