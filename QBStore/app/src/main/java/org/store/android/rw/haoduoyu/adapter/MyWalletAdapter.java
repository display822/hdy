package org.store.android.rw.haoduoyu.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.data.MyWalletData;

public class MyWalletAdapter extends BaseQuickAdapter<MyWalletData, BaseViewHolder> {


    public MyWalletAdapter() {
        super(R.layout.item_my_wallet);

    }


    @Override
    protected void convert(BaseViewHolder helper, MyWalletData item) {
        helper.setText(R.id.tv_item_title, item.getTitle())
                .setText(R.id.tv_item_num, item.getNum());
        helper.setImageResource(R.id.item_bg_ico,helper.getLayoutPosition() == 0? R.mipmap.qbye: R.mipmap.jfye);
    }
}
