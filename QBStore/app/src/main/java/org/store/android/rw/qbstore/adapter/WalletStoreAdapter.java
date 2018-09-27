package org.store.android.rw.qbstore.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.data.ProductDetailsData;
import org.store.android.rw.qbstore.ui.activity.BaseActivity;
import org.store.android.rw.qbstore.widget.utils.GlideUtils;

import java.util.List;

public class WalletStoreAdapter extends BaseQuickAdapter<ProductDetailsData, BaseViewHolder> {

    public WalletStoreAdapter() {
        super(R.layout.item_wallet_store);
    }


    @Override
    protected void convert(BaseViewHolder helper, ProductDetailsData item) {
        GlideUtils.loadImage(mContext,item.getProductPic(), (ImageView) helper.getView(R.id.tv_item_background));
        helper.setText(R.id.tv_item_price,  item.getPrice()+"")
                .setText(R.id.tv_item_name, item.getProductName())
                .setText(R.id.tv_item_num, "库存:"+item.getNumber()+"");
    }
}
