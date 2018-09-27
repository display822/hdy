package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.ExchangeHistoryData;
import org.store.android.rw.haoduoyu.widget.utils.GlideUtils;

public class ExchangeHistoryAdapter extends BaseQuickAdapter<ExchangeHistoryData,BaseViewHolder> {

    public ExchangeHistoryAdapter( ) {
        super(R.layout.item_product_new);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExchangeHistoryData item) {
        int exchangeType = item.getExchangeType();
        GlideUtils.loadImage(mContext,item.getProductPic(),(RoundedImageView)helper.getView(R.id.iv_item_ico));
        helper.setVisible(R.id.tv_item_date, true)
                .setVisible(R.id.tv_item_seller, true);
        helper.setText(R.id.tv_item_price, item.getPrice())
                .setText(R.id.tv_item_title, item.getProductName())
                .setText(R.id.tv_item_date, item.getGetDate())
                .setText(R.id.tv_item_seller,  item.getUserCode())
                .setText(R.id.tv_item_type, exchangeType == 0 ? "积分兑换" : "库存兑换");
    }
}
