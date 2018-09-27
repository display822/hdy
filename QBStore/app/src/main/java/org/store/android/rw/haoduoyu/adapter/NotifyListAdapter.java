package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.GetNoticeData;

public class NotifyListAdapter extends BaseQuickAdapter<GetNoticeData, BaseViewHolder> {
    public NotifyListAdapter() {
        super(R.layout.item_notify_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetNoticeData item) {
        helper.setText(R.id.tv_item_title, item.getTitle())
                .setText(R.id.tv_item_date, item.getCreateTime());
    }
}
