package org.store.android.rw.haoduoyu.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.OrderListData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.widget.utils.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

public class PayOrderActivity extends BaseActivity {
    @BindView(R.id.cv_time)
    CountdownView mCvTime;
    @BindView(R.id.tv_text_1)
    TextView mTvText1;
    @BindView(R.id.tv_text_2)
    TextView mTvText2;
    @BindView(R.id.tv_text_3)
    TextView mTvText3;
    @BindView(R.id.tv_text_4)
    TextView mTvText4;
    @BindView(R.id.tv_pay_type)
    TextView mTvPayType;
    @BindView(R.id.tv_pay_phone)
    TextView mTvPayPhone;
    @BindView(R.id.tv_copy_content)
    TextView mTvCopyContent;
    @BindView(R.id.iv_add_ico)
    ImageView mIvAddIco;
    @BindView(R.id.iv_pay_img)
    ImageView mIvPayImg;
    @BindView(R.id.tv_order_price)
    TextView mTvOrderPrice;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.btn_1)
    Button mBtn1;
    @BindView(R.id.btn_2)
    Button mBtn2;
    @BindView(R.id.ll_btn_content)
    LinearLayout mLlBtnContent;
    @BindView(R.id.iv_product_img)
    ImageView mIvProductImg;
    private BasePostData mTokenData;
    private int mOrderType; // 买入 0 卖出1
    private int mStatus; //订单状态

    private String[] mBuyerText1 = {"等待付款，您需要使用支付宝向", "等待确认，您已使用支付宝向", "您已使用支付宝向"};
    private String[] mBuyerText3 = {"支付", "支付", "支付，该笔订单已完成"};
    private String[] mBuyerText4 = {"后，如未支付，您的账户将会被冻结", "，如未确认，系统将自动确认", ""};
    private String[] mSellerText1 = {"等待付款，", "等待确认，", "", ""};
    private String[] mSellerText3 = {"需要使用支付宝向您支付，", "已使用支付宝向您支付，", "已使用支付宝向您支付，该笔订单已完成"};
    private String[] mSellerText4 = {"后，如未支付，对方账户将被冻结", "，如未确认，系统将自动确认", ""};
    private ImageItem mImageItem;
    private String mOrderID;
    private String mBuyerID;
    private String mSelllerID;
    private int mIsCheat;
    private String mProductID;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_pay_order;
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        mTokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("OrderID", getIntent().getStringExtra("OrderID"));
        mTokenData.setData(map);
        AppActionImpl.getInstance().GetOrderListDetial(this, mTokenData, new BaseHttpCallbackListener<OrderListData>() {
            @Override
            public Void onSuccess(OrderListData data) {
                mTvOrderPrice.setText("￥ " + data.getGetPrice());
                mOrderType = data.getOrderType();
                mStatus = data.getStatus();
                mOrderID = data.getOrderID();
                mBuyerID = data.getBuyerID();
                mSelllerID = data.getSelllerID();
                mProductID = data.getProductID();
                mIsCheat = data.getIsCheat();
                GlideUtils.loadImage(PayOrderActivity.this, data.getProductPic(), mIvProductImg);
                if (data.getStatus() != 0)
                    GlideUtils.loadImage(PayOrderActivity.this, data.getPayPic(), mIvPayImg);
                if (mOrderType == 0) {
                    mTvText1.setText(mBuyerText1[mStatus]);
                    mTvText2.setText(data.getSeller());
                    mTvText3.setText(mBuyerText3[mStatus]);
                    mTvText4.setText(mBuyerText4[mStatus]);
                    mTvPayType.setText("收款人的支付宝");
                    mTvPayPhone.setText(data.getSellerAccount());

                    switch (mStatus) {
                        case 0:
                            mCvTime.setVisibility(View.VISIBLE);
                            mBtnSubmit.setVisibility(View.VISIBLE);

                            break;
                        case 1:
                            mCvTime.setVisibility(View.VISIBLE);
                            mBtnSubmit.setVisibility(View.GONE);
                            if (mIsCheat == 1) {
                                mCvTime.setVisibility(View.GONE);
                                mTvText1.setText("虚假记录，");
                                mTvText3.setText("标记为虚假信息,请与对方联系");
                                mTvText4.setVisibility(View.GONE);
                            }
                            break;
                        case 2:
                            mCvTime.setVisibility(View.GONE);
                            mBtnSubmit.setVisibility(View.GONE);

                            break;
                    }

                } else {
                    LogUtils.i("mStatus" + mStatus);
                    LogUtils.i("mIsCheat" + mIsCheat);
                    mTvText1.setText(mSellerText1[mStatus]);
                    mTvText2.setText(data.getBuyer());
                    mTvText3.setText(mSellerText3[mStatus]);
                    mTvText4.setText(mSellerText4[mStatus]);
                    mTvPayType.setText("打款人的联系方式");
                    mTvPayPhone.setText(data.getBuyerUserCode());
                    if (mStatus != 2) {
                        mCvTime.setVisibility(View.VISIBLE);
                    } else {
                        mCvTime.setVisibility(View.GONE);
                    }
                    if (mStatus == 1) {
                        mLlBtnContent.setVisibility(View.VISIBLE);
                        if (data.getIsCheat() == 0) {
                            mBtn1.setVisibility(View.VISIBLE);
                            mBtn2.setVisibility(View.VISIBLE);
                            mBtn2.setText("虚假记录");
                        } else if (data.getIsCheat() == 1) {
                            mTvText1.setText("虚假记录，您标记");
                            mTvText2.setText(data.getSeller());
                            mTvText3.setText("的支付为虚假信息");
                            mTvText4.setText("如果对方已重新支付，请点击\"撤销虚假信息\"");
                            mCvTime.setVisibility(View.GONE);
                            mBtn1.setVisibility(View.GONE);
                            mBtn2.setVisibility(View.VISIBLE);
                            mBtn2.setText("撤销虚假记录");
                        }
                    } else {
                        mLlBtnContent.setVisibility(View.GONE);
                        mBtn1.setVisibility(View.GONE);
                        mBtn2.setVisibility(View.GONE);
                    }
                }


                //设置倒计时
                int time = data.getTimestamp() * 1000;
                if (time > 60000000) {
                    DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
                    dynamicConfigBuilder.setShowHour(false);
                    mCvTime.dynamicShow(dynamicConfigBuilder.build());
                }
                mCvTime.start(time);


                return null;
            }
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText(getIntent().getStringExtra("title"));

    }


    @OnClick({R.id.iv_pay_img, R.id.btn_submit, R.id.btn_1, R.id.btn_2, R.id.tv_pay_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pay_img:
                if (mOrderType == 0 && mStatus == 0) {
                    ActivityUtils.startActivityForResult(this, ImageGridActivity.class, Constant.ORDER_IMG_REQUEST_CODE);
                }
                break;
            case R.id.btn_submit:
                //确认打款
                if (mOrderType == 0 && mStatus == 0) {
                    if (mImageItem == null) {
                        ToastUtils.showShort("请上传图片后提交");
                        return;
                    }

                    UIUtils.showTextDialog(this, null, "确认打款吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BasePostData tokenData = UIUtils.getTokenData();
                            Map<String, String> map = new HashMap<>();
                            map.put("OrderID", mOrderID);
                            map.put("BuyerID", mBuyerID);
                            map.put("SelllerID", mSelllerID);
                            map.put("PayPic", UIUtils.imageToBase64(mImageItem.path));
                            tokenData.setData(map);
                            AppActionImpl.getInstance().UploadPayPic(PayOrderActivity.this, tokenData, new BaseHttpCallbackListener<Void>() {
                                @Override
                                public Void onSuccess(Void data) {
                                    mBtnSubmit.setVisibility(View.GONE);
                                    finish();
                                    return null;
                                }
                            });
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                }


                break;
            case R.id.btn_1:
                //确认收款
                if (mOrderType == 1 && mStatus == 1) {
                    UIUtils.showTextDialog(this, null, "确认收款吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BasePostData tokenData = UIUtils.getTokenData();
                            Map<String, String> map = new HashMap<>();
                            map.put("OrderID", mOrderID);
                            map.put("ProductID", mProductID);
                            map.put("SelllerID", mSelllerID);
                            map.put("BuyerID", mBuyerID);
                            tokenData.setData(map);
                            AppActionImpl.getInstance().ComfirmReceive(PayOrderActivity.this, tokenData, new BaseHttpCallbackListener<Void>() {
                                @Override
                                public Void onSuccess(Void data) {
                                    mLlBtnContent.setVisibility(View.GONE);
                                    mBtn1.setVisibility(View.GONE);
                                    mBtn2.setVisibility(View.GONE);
                                    finish();
                                    return null;
                                }
                            });
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                }


                break;
            case R.id.btn_2:
                //虚假记录

                if (mOrderType == 1 && mStatus == 1) {
                    BasePostData tokenData = UIUtils.getTokenData();
                    Map<String, String> map = new HashMap<>();
                    map.put("OrderID", mOrderID);
                    map.put("SelllerID", mSelllerID);
                    tokenData.setData(map);
                    if (mIsCheat == 0) {
                        AppActionImpl.getInstance().ReportCheat(this, tokenData, new BaseHttpCallbackListener<Void>() {
                            @Override
                            public Void onSuccess(Void data) {
                                initData();
                                return null;
                            }
                        });
                    } else {
                        AppActionImpl.getInstance().UnReportCheat(this, tokenData, new BaseHttpCallbackListener<Void>() {
                            @Override
                            public Void onSuccess(Void data) {
                                initData();
                                return null;
                            }
                        });
                    }
                }
                break;
            case R.id.tv_pay_phone:
                if (!TextUtils.isEmpty(mTvPayPhone.getText().toString())) {
                    ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData myClip = ClipData.newPlainText("text", mTvPayPhone.getText().toString());
                    myClipboard.setPrimaryClip(myClip);
                    ToastUtils.showShort("已复制到剪切板");
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == Constant.ORDER_IMG_REQUEST_CODE) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mImageItem = images.get(0);
                GlideUtils.loadImage(this, mImageItem.path, mIvPayImg);
            }
        }
    }


}
