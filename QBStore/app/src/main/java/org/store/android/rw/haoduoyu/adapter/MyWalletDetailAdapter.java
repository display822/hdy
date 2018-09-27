package org.store.android.rw.haoduoyu.adapter;

import android.view.View;
import android.widget.TextView;
import org.store.android.rw.haoduoyu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.store.android.rw.haoduoyu.data.MyWalletDetail;

public class MyWalletDetailAdapter extends BaseQuickAdapter<MyWalletDetail, BaseViewHolder> {

    public MyWalletDetailAdapter() {
        super(R.layout.item_my_wallet_detail);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyWalletDetail item) {
//        helper.setImageResource(R.id.item_type_ico, item.getSource() == 1 ? R.mipmap.item_signin : R.mipmap.item_deal);
        helper.setText(R.id.tv_item_date, item.getGetDate())
                .setText(R.id.tv_item_price, (item.getAmount() > 0 ? "+" : "") + item.getAmount())
                .setText(R.id.tv_item_title, getTypeText(item.getSource()));
        TextView mItemName = helper.getView(R.id.tv_item_name);
        if (item.getSource() == 2) {
            mItemName.setVisibility(View.VISIBLE);
            mItemName.setText("会员:" + item.getRealName());
        } else {
            mItemName.setVisibility(View.GONE);
        }
    }

    private String getTypeText(int source) {
        switch (source) {
            case 0:
                return "赠送金额";
            case 1:
                return "用户签到";
            case 2:
                return "领导奖励";
            case 3:
                return "分红奖励";
            case 4:
                return "推广奖励";
            case 5:
                return "用户交易";

        }
        return "未知状态";
    }
}
