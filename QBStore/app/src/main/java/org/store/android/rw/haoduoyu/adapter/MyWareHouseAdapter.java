package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.widget.utils.GlideUtils;

public class MyWareHouseAdapter extends BaseQuickAdapter<ProductDetailsData, BaseViewHolder> {

    public MyWareHouseAdapter() {
        super(R.layout.item_product_details);
    }


    @Override
    protected void convert(BaseViewHolder helper, ProductDetailsData item) {
        GlideUtils.loadImage(mContext,item.getProductPic(),(RoundedImageView)helper.getView(R.id.iv_item_ico));
        helper.setVisible(R.id.ll_item_old_price, true);
        helper.setText(R.id.tv_item_price, "" + item.getPrice())
                .setText(R.id.tv_item_title, item.getProductName())
                .setText(R.id.tv_item_old_price, "￥" + item.getPrice())
                .setText(R.id.tv_item_type, getTypeText(item.getStatus()));

    }

    private String getTypeText(int status) {
        switch (status) {
            case 0:
                return "待出售";
            case 4:
                return "冻结中";
        }
        return "未知状态";
    }
}
