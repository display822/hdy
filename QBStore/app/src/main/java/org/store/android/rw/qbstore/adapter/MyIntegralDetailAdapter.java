package org.store.android.rw.qbstore.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.data.MyWalletDetail;

import java.util.List;

public class MyIntegralDetailAdapter extends BaseQuickAdapter<MyWalletDetail, BaseViewHolder> {

    public MyIntegralDetailAdapter() {
        super(R.layout.item_my_wallet_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyWalletDetail item) {
        helper.setImageResource(R.id.item_type_ico, R.mipmap.item_deal);
        helper.setText(R.id.tv_item_date, item.getGetDate())
                .setText(R.id.tv_item_price, (item.getNumber() > 0 ? "+" : "") + item.getNumber())
                .setText(R.id.tv_item_title, getTypeText(item.getSource()));
    }

    private String getTypeText(int source) {
        switch (source) {
            case 0:
                return "后端赠送";
            case 1:
                return "签到奖励";
            case 2:
                return "升级奖励";
            case 3:
                return "订单奖励";
            case 4:
                return "推广奖励";
            case 5:
                return "消费积分";
        }
        return "未知状态";
    }
}
