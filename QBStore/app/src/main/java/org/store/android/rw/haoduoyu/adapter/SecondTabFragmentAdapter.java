package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.store.android.rw.haoduoyu.R;

public class SecondTabFragmentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SecondTabFragmentAdapter() {
        super(R.layout.item_second_tab_fragment);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int layoutPosition = helper.getLayoutPosition();
        helper.setText(R.id.tv_item_title, layoutPosition == 0 ? mContext.getResources().getString(R.string.group_list_activity_title) : mContext.getResources().getString(R.string.retail_list_activity_title))
                .setText(R.id.tv_item_content, layoutPosition == 0 ? "代理是我直接推广的客户" : "零售是代理商帮助我推广的客户")
                .setText(R.id.tv_item_price, item);

    }
}
