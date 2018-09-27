package org.store.android.rw.haoduoyu.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import org.store.android.rw.haoduoyu.R;


/**
 * Created by SUN on 2018/3/22.
 */

public class AgreementClickText extends ClickableSpan {
    private Context mContext;
    private View.OnClickListener mOnClickListener;

    public AgreementClickText(Context context, View.OnClickListener onClickListener) {
        mContext = context;
        mOnClickListener = onClickListener;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(ContextCompat.getColor(mContext, R.color.rgb_dd3217));
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View view) {
        mOnClickListener.onClick(view);
    }
}
