package org.store.android.rw.haoduoyu.net;


import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

import org.store.android.rw.haoduoyu.MyApplication;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.data.BasePostData;
import org.store.android.rw.haoduoyu.data.BuySomethingData;
import org.store.android.rw.haoduoyu.data.ExchangeHistoryData;
import org.store.android.rw.haoduoyu.data.GetNoticeData;
import org.store.android.rw.haoduoyu.data.GetUserInfoData;
import org.store.android.rw.haoduoyu.data.GroupListData;
import org.store.android.rw.haoduoyu.data.LoginData;
import org.store.android.rw.haoduoyu.data.MyWalletDetail;
import org.store.android.rw.haoduoyu.data.OrderListData;
import org.store.android.rw.haoduoyu.data.ProductDetailsData;
import org.store.android.rw.haoduoyu.data.SecondTabData;
import org.store.android.rw.haoduoyu.data.SignData;
import org.store.android.rw.haoduoyu.data.TimeStampData;
import org.store.android.rw.haoduoyu.widget.utils.AESUtils;
import org.store.android.rw.haoduoyu.widget.utils.LoadingDialogUtils;
import org.store.android.rw.haoduoyu.widget.utils.UIUtils;
import org.store.android.rw.haoduoyu.R;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppActionImpl implements AppAction, Api {

    private static MyOkHttp mOkhttp;
    private static AppAction mAppAction;

    public static AppAction getInstance() {
        if (mAppAction == null) {
            synchronized (AppActionImpl.class) {
                if (mAppAction == null) {
                    mAppAction = new AppActionImpl();
                }
            }
        }
        return mAppAction;
    }

    private MyOkHttp getHttp() {
        return MyApplication.getInstance().getMyOkHttp();
    }

    public void post(Context context, String url, Object map, GsonResponseHandler handler) {
        LoadingDialogUtils.showLoadingDialog(context, UIUtils.getResString(R.string.loading_start));
        String aes = handler.getAes();
        LogUtils.i("aes秘钥" + aes);
        byte[] data = EncryptUtils.encryptAES2Base64(new Gson().toJson(map).getBytes(), aes.getBytes(), "AES/ECB/PKCS5Padding", null);
        LogUtils.i("aes密文" + new String(data));
        byte[] publicAES = EncryptUtils.encryptRSA2Base64(aes.getBytes(), Constant.PUBLIC_KEY
                , true, "RSA/ECB/PKCS1Padding");
        LogUtils.i("aes密钥" + new String(publicAES));
        getHttp()
                .post()
                .url(url)
                .addParam("screctString", new String(data))
                .addParam("strAesKey", new String(publicAES))
                .tag(this)
                .enqueue(handler);
    }

    @Override
    public void login(Context context, final String phone, final String password, String wh, final BaseHttpCallbackListener<LoginData> listener) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            listener.onFailure("", "用户名或密码不正确，请检查！");
            return;
        }
        final String aesKeyString = AESUtils.generateKeyString();
        Map<String, String> map = new HashMap<>();
        map.put("UserCode", phone);
        map.put("Password", password);
        if (!TextUtils.isEmpty(wh))
            map.put("ResolvingPower", wh);
        post(context, LOGIN_URL, map, new GsonResponseHandler<ApiResponse<LoginData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<LoginData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                ToastUtils.showShort(response.getMessage()+"");
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }


    @Override
    public void register(Context context, String userCode, String password, String realName, String refereePhone, String validCode, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        Map<String, String> map = new HashMap<>();
        map.put("UserCode", userCode);
        map.put("Password", password);
        map.put("RealName", realName);
        map.put("RefereePhone", refereePhone);
        map.put("ValidCode", validCode);
        post(context, REGISTER_URL, map, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    ToastUtils.showShort(response.getMessage());
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void getsmsValidCode(Context context, String phone, final BaseHttpCallbackListener<Void> listener) {
        Map<String, String> map = new HashMap<>();
        map.put("Phone", phone);
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETSMSVALIDCODE_URL, map, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }

            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }
        });

    }

    //    废弃
    @Override
    public void validCode(Context context, String ValidCode, final BaseHttpCallbackListener<Void> listener) {
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("ValidCode", ValidCode);
        getHttp()
                .post()
                .url(VALIDCODE_URL)
                .addParam("screctString", UIUtils.encodeData(stringMap))
                .tag(this)
                .enqueue(new GsonResponseHandler<ApiResponse<Void>>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        listener.onFailure("", error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, ApiResponse<Void> response) {
                        if (response.isResult()) {
                            listener.onSuccess(response.getData());
                        } else {
                            listener.onFailure(response.getStrCode(), response.getMessage());
                        }
                    }
                });
    }

    //        废弃
    @Override
    public void getTimeStamp(Context context, final BaseHttpCallbackListener<TimeStampData> listener) {
        LoadingDialogUtils.showLoadingDialog(context, UIUtils.getResString(R.string.loading_start));
        getHttp()
                .post()
                .url(GETTIMESTAMP_URL)
                .tag(this)
                .enqueue(new GsonResponseHandler<ApiResponse<TimeStampData>>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        LoadingDialogUtils.hideLoadingDialog();
                        listener.onFailure("", error_msg);
                    }

                    @Override
                    public void onSuccess(int statusCode, ApiResponse<TimeStampData> response) {
                        if (response.isResult()) {
                            listener.onSuccess(response.getData());
                        } else {
                            LoadingDialogUtils.hideLoadingDialog();
                            listener.onFailure(response.getStrCode(), response.getMessage());
                        }
                    }
                });
    }

    @Override
    public void chgPassWord(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, CHGPASSWORD_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    ToastUtils.showShort(response.getMessage());
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });

    }

    @Override
    public void getUserInfo(Context context, BasePostData data, final BaseHttpCallbackListener<GetUserInfoData> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETUSERINFO_URL, data, new GsonResponseHandler<ApiResponse<GetUserInfoData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<GetUserInfoData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void getNotice(Context context, BasePostData data, final BaseHttpCallbackListener<List<GetNoticeData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETNOTICE_URL, data, new GsonResponseHandler<ApiResponse<List<GetNoticeData>>>(aesKeyString) {

            @Override
            public void onSuccess(int statusCode, ApiResponse<List<GetNoticeData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });

    }

    @Override
    public void ChgAddress(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, POSTCHGADDRESS_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {

            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    ToastUtils.showShort(response.getMessage());
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void ValidInfo(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, VALIDINFO_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    ToastUtils.showShort(response.getMessage());
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetWalletDetail(Context context, BasePostData data, final BaseHttpCallbackListener<List<MyWalletDetail>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETWALLETDETAIL_URL, data, new GsonResponseHandler<ApiResponse<List<MyWalletDetail>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<MyWalletDetail>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetScoreDetail(Context context, BasePostData data, final BaseHttpCallbackListener<List<MyWalletDetail>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETSCOREDETAIL_URL, data, new GsonResponseHandler<ApiResponse<List<MyWalletDetail>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<MyWalletDetail>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetExchangeDetail(Context context, BasePostData data, final BaseHttpCallbackListener<List<ExchangeHistoryData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETEXCHANGEDETAIL_URL, data, new GsonResponseHandler<ApiResponse<List<ExchangeHistoryData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<ExchangeHistoryData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetReferee3(Context context, BasePostData data, final BaseHttpCallbackListener<SecondTabData> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETREFEREE3_URL, data, new GsonResponseHandler<ApiResponse<SecondTabData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<SecondTabData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetReferee2(Context context, BasePostData data, final BaseHttpCallbackListener<List<GroupListData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETREFEREEL2_URL, data, new GsonResponseHandler<ApiResponse<List<GroupListData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<GroupListData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetReferee(Context context, BasePostData data, final BaseHttpCallbackListener<List<GroupListData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETREFEREE_URL, data, new GsonResponseHandler<ApiResponse<List<GroupListData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<GroupListData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetSign(Context context, BasePostData data, final BaseHttpCallbackListener<SignData> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETSIGN_URL, data, new GsonResponseHandler<ApiResponse<SignData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<SignData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void ExecSign(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, EXECSIGN_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void ChgAccount(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, CHGACCOUNT_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetOrderList(Context context, BasePostData data, final BaseHttpCallbackListener<List<OrderListData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETORDERLIST_URL, data, new GsonResponseHandler<ApiResponse<List<OrderListData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<OrderListData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetBuyOrderList(Context context, BasePostData data, final BaseHttpCallbackListener<List<OrderListData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETBUYORDERLIST_URL, data, new GsonResponseHandler<ApiResponse<List<OrderListData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<OrderListData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetSellOrderList(Context context, BasePostData data, final BaseHttpCallbackListener<List<OrderListData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETSELLORDERLIST_URL, data, new GsonResponseHandler<ApiResponse<List<OrderListData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<OrderListData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void UserActivation(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, USERACTIVATION_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetProductList(Context context, BasePostData data, final BaseHttpCallbackListener<List<ProductDetailsData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETPRODUCTLIST_URL, data, new GsonResponseHandler<ApiResponse<List<ProductDetailsData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<ProductDetailsData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetCampProductList(Context context, BasePostData data, final BaseHttpCallbackListener<List<ProductDetailsData>> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETCAMPPRODUCTLIST_URL, data, new GsonResponseHandler<ApiResponse<List<ProductDetailsData>>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<List<ProductDetailsData>> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void SellProduct(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, SELLPRODUCT_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void TaxExchange(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, TAXEXCHANGE_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void BuyMoneyShopping(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, BUYMONEYSHOPPING_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void BuyScoreShopping(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, BUYSCORESHOPPING_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void BuySomething(Context context, BasePostData data, final BaseHttpCallbackListener<BuySomethingData> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, BUYSOMETHING_URL, data, new GsonResponseHandler<ApiResponse<BuySomethingData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<BuySomethingData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void UploadPayPic(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, UPLOADPAYPIC_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void ComfirmReceive(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, COMFIRMRECEIVE_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void ReportCheat(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, REPORTCHEAT_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void UnReportCheat(Context context, BasePostData data, final BaseHttpCallbackListener<Void> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, UNREPORTCHEAT_URL, data, new GsonResponseHandler<ApiResponse<Void>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<Void> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetOrderListDetial(Context context, BasePostData data, final BaseHttpCallbackListener<OrderListData> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETORDERLISTDETIAL_URL, data, new GsonResponseHandler<ApiResponse<OrderListData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<OrderListData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }

    @Override
    public void GetCampProductDetial(Context context, BasePostData data, final BaseHttpCallbackListener<ProductDetailsData> listener) {
        String aesKeyString = AESUtils.generateKeyString();
        post(context, GETCAMPPRODUCTDETIAL_URL, data, new GsonResponseHandler<ApiResponse<ProductDetailsData>>(aesKeyString) {
            @Override
            public void onSuccess(int statusCode, ApiResponse<ProductDetailsData> response) {
                LoadingDialogUtils.hideLoadingDialog();
                if (response.isSuccess()) {
                    listener.onSuccess(response.getData());
                } else {
                    listener.onFailure(response.getStrCode(), response.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                LoadingDialogUtils.hideLoadingDialog();
                listener.onFailure("", error_msg);
            }
        });
    }


}
