package org.store.android.rw.haoduoyu.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.gyf.barlibrary.ImmersionBar;

import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.net.BaseHttpCallbackListener;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.BuySomethingData;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.net.AppActionImpl;
import org.store.android.rw.haoduoyu.widget.utils.GlideUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailsActivity extends BaseActivity {
    @BindView(R.id.toobar_left_back)
    ImageView mToobarLeftBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView mToolbarSubtitle;
    @BindView(R.id.toolbar_right_button)
    ImageView mToolbarRightButton;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.item_detail_container)
    NestedScrollView mItemDetailContainer;
    @BindView(R.id.iv_details_bg)
    ImageView mIvDetailsBg;
    @BindView(R.id.tv_details_title)
    TextView mTvDetailsTitle;
    @BindView(R.id.tv_details_type)
    TextView mTvDetailsType;
    @BindView(R.id.tv_details_price)
    TextView mTvDetailsPrice;
    @BindView(R.id.tv_details_context)
    TextView mTvDetailsContext;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.btn_sale)
    Button mBtnSale;
    private BasePostData mTokenData;
    private int mCategory; //商品状态
    private String mBuyer;
    private String mProductID;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        mTokenData = UIUtils.getTokenData();
        Map<String, String> map = new HashMap<>();
        map.put("ProductID", getIntent().getStringExtra("ProductID"));
        mTokenData.setData(map);
        AppActionImpl.getInstance().GetCampProductDetial(this, mTokenData, new BaseHttpCallbackListener<ProductDetailsData>() {

            @Override
            public Void onSuccess(ProductDetailsData data) {
                mCategory = data.getCategory();
                mBuyer = data.getBuyer();
                mProductID = data.getProductID();

                if (mCategory == 5) {
                    // 仓库
                    if (data.getStatus() == 0) {
                        mBtnSale.setVisibility(View.VISIBLE);
                        mBtnSubmit.setVisibility(View.VISIBLE);
                    } else {
                        mBtnSale.setVisibility(View.GONE);
                        mBtnSubmit.setVisibility(View.GONE);
                    }
                    mTvDetailsType.setVisibility(View.VISIBLE);
                    mTvDetailsType.setText(data.getReleaseDateDjs());
                    mBtnSubmit.setText("兑换");

                } else {
                    mBtnSale.setVisibility(View.INVISIBLE);
                    mTvDetailsType.setVisibility(View.GONE);
                    mBtnSubmit.setText("立即购买");
                }
                GlideUtils.loadImage(ProductDetailsActivity.this, data.getProductPic(), mIvDetailsBg);
                mTvDetailsTitle.setText(data.getProductName());
                mTvDetailsPrice.setText(data.getPrice());
                mTvDetailsContext.setText(Html.fromHtml(data.getRemark(), null, null));
                return null;
            }
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("商品详情");
        mToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//设置展开后标题的颜色
        mToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后标题的颜色
        mToolbarLayout.setExpandedTitleGravity(Gravity.START);


    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        mImmersionBar.titleBar(mToolbar).init();
    }


    @OnClick({R.id.btn_sale, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sale:
                UIUtils.showTextDialog(this, "", "确认卖出商品吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> map = new HashMap<>();
                        map.put("Buyer", mBuyer);
                        map.put("ProductID", mProductID);
                        mTokenData.setData(map);
                        AppActionImpl.getInstance().SellProduct(ProductDetailsActivity.this, mTokenData, new BaseHttpCallbackListener<Void>() {
                            @Override
                            public Void onSuccess(Void data) {
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

                break;
            case R.id.btn_submit://购买
                switch (mCategory) {
                    case 1:
                        //新手抢单区
                    case 2:
                        //市场
                        UIUtils.showTextDialog(this, "", "确认购买商品吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppActionImpl.getInstance().BuySomething(ProductDetailsActivity.this, mTokenData, new BaseHttpCallbackListener<BuySomethingData>() {
                                    @Override
                                    public Void onSuccess(BuySomethingData data) {
                                        Intent mIntent = new Intent(ProductDetailsActivity.this, PayOrderActivity.class);
                                        mIntent.putExtra("title", getResources().getString(R.string.pay_order_activity_pay_title));
                                        mIntent.putExtra("OrderID", data.getOrderID());
                                        ActivityUtils.startActivity(ProductDetailsActivity.this, mIntent);
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

                        break;
                    case 3:
                        //钱包商城
                        UIUtils.showTextDialog(this, "", "确认购买商品吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppActionImpl.getInstance().BuyMoneyShopping(ProductDetailsActivity.this, mTokenData, new BaseHttpCallbackListener<Void>() {
                                    @Override
                                    public Void onSuccess(Void data) {
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

                        break;
                    case 4:
                        //积分商城
                        UIUtils.showTextDialog(this, "", "确认购买商品吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppActionImpl.getInstance().BuyScoreShopping(ProductDetailsActivity.this, mTokenData, new BaseHttpCallbackListener<Void>() {
                                    @Override
                                    public Void onSuccess(Void data) {
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

                        break;
                    case 5:
                        //仓库 兑换
                        UIUtils.showTextDialog(this, "", "确认兑换商品吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppActionImpl.getInstance().TaxExchange(ProductDetailsActivity.this, mTokenData, new BaseHttpCallbackListener<Void>() {
                                    @Override
                                    public Void onSuccess(Void data) {
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

                        break;
                }

                break;
        }
    }
}
