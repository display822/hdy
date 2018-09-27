package org.store.android.rw.qbstore.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.data.OrderListData;
import org.store.android.rw.qbstore.widget.utils.GlideUtils;

import java.util.List;

public class FirstTabFragmentAdapter extends BaseQuickAdapter<OrderListData, BaseViewHolder> {
    public FirstTabFragmentAdapter() {
        super(R.layout.item_product_details);


    }


    @Override
    protected void convert(BaseViewHolder helper, OrderListData item) {
        int orderType = item.getOrderType();
        GlideUtils.loadImage(mContext, item.getProductPic(), (RoundedImageView) helper.getView(R.id.iv_item_ico));
        helper.setVisible(R.id.tv_item_date, true)
                .setVisible(R.id.tv_item_seller, true)
                .setVisible(R.id.rl_item_date, true)
                .setVisible(R.id.tv_item_order_type, true);
        helper.setText(R.id.tv_item_price, item.getGetPrice())
                .setText(R.id.tv_item_title, item.getProductName())
                .setText(R.id.tv_item_date, item.getGetDate())
                .setText(R.id.tv_item_seller, orderType == 0 ? item.getSelllerUserCode() : item.getBuyerUserCode())
                .setText(R.id.tv_item_type, orderType == 0 ? "买入交易" : "卖出交易")
                .setText(R.id.tv_item_order_type, getOrderText(orderType, item.getStatus(), item.getIsCheat()));
        helper.setTextColor(R.id.tv_item_order_type, Color.parseColor(getTypeColor(item.getStatus())));

    }

    private String getTypeColor(int status) {
        switch (status) {
            case 0:
                return "#40A3F0";
            case 1:
                return "#DE3D23";
            case 2:
                return "#41963B";
        }

        return "";
    }

    private String getOrderText(int type, int status, int isCheat) {
        if (isCheat == 1) {
            return "虚假记录";
        } else {
            if (type == 0) {
                switch (status) {
                    case 0:
                        return "等您支付";
                    case 1:
                        return "等对方确认";
                    case 2:
                        return "已完成";
                }
            } else if (type == 1) {
                switch (status) {
                    case 0:
                        return "等对方支付";
                    case 1:
                        return "等您确认";
                    case 2:
                        return "已完成";
                }
            }
        }

        return "";
    }

}
