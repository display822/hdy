package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.GroupListData;

public class GroupListAdapter extends BaseQuickAdapter<GroupListData, BaseViewHolder> {

    public GroupListAdapter() {
        super(R.layout.item_group_list);
    }


    @Override
    protected void convert(BaseViewHolder helper, GroupListData item) {
        helper.setText(R.id.tv_item_num, item.getNumber()+"")
                .setText(R.id.tv_item_name, item.getRealName())
                .setText(R.id.tv_item_date, item.getCreateDate());
        if (item.getStatus() == 1) {
            helper.setText(R.id.tv_item_leve, item.getLevel());
        } else {
            helper.setText(R.id.tv_item_leve, item.getStatus() == 0 ? "未激活" : "已冻结");
        }


    }
}
