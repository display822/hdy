package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.widget.utils.GlideUtils;

public class ThirdTabFragmentAdapter extends BaseQuickAdapter<ProductDetailsData, BaseViewHolder> {


    public ThirdTabFragmentAdapter() {
        super(R.layout.item_product_details);
    }


    @Override
    protected void convert(BaseViewHolder helper, ProductDetailsData item) {
        int status = item.getStatus();
        GlideUtils.loadImage(mContext, item.getProductPic(), (RoundedImageView) helper.getView(R.id.iv_item_ico));
        helper.setVisible(R.id.ll_item_old_price, true);
        helper.setText(R.id.tv_item_price, item.getPrice())
                .setText(R.id.tv_item_title, item.getProductName())
                .setText(R.id.tv_item_date, item.getGetDate())
                .setText(R.id.tv_item_old_price, item.getPrice())
                .setText(R.id.tv_item_type, status == 1 ? "出售中" : "未知状态");
    }
}
