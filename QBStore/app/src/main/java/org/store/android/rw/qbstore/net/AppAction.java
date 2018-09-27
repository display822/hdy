package org.store.android.rw.qbstore.net;

import android.content.Context;

import org.store.android.rw.qbstore.data.BasePostData;
import org.store.android.rw.qbstore.data.BuySomethingData;
import org.store.android.rw.qbstore.data.ExchangeHistoryData;
import org.store.android.rw.qbstore.data.GetNoticeData;
import org.store.android.rw.qbstore.data.GetUserInfoData;
import org.store.android.rw.qbstore.data.GroupListData;
import org.store.android.rw.qbstore.data.LoginData;
import org.store.android.rw.qbstore.data.MyWalletDetail;
import org.store.android.rw.qbstore.data.OrderListData;
import org.store.android.rw.qbstore.data.ProductDetailsData;
import org.store.android.rw.qbstore.data.SecondTabData;
import org.store.android.rw.qbstore.data.SignData;
import org.store.android.rw.qbstore.data.TimeStampData;

import java.util.List;
import java.util.Map;


/**
 * Created by SUN on 2017/3/10.
 */

public interface AppAction {

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @param listener
     */
    public void login(Context context, String phone, String password, String ResolvingPower , BaseHttpCallbackListener<LoginData> listener);

    /**
     * @param context
     * @param listener
     */
    public void register(Context context, String userCode,String password,String realName,String refereePhone, String ValidCode, BaseHttpCallbackListener<Void> listener);

    public void getsmsValidCode(Context context, String phone, BaseHttpCallbackListener<Void> listener);

    public void validCode(Context context, String ValidCode, BaseHttpCallbackListener<Void> listener);

    public void getTimeStamp(Context context, BaseHttpCallbackListener<TimeStampData> listener);

    public void chgPassWord(Context context, BasePostData data , BaseHttpCallbackListener<Void> listener);

    public void getUserInfo(Context context, BasePostData data , BaseHttpCallbackListener<GetUserInfoData> listener);

    public void getNotice(Context context,  BasePostData data,   BaseHttpCallbackListener<List<GetNoticeData>> listener);

    public void ChgAddress(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void ValidInfo(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void GetWalletDetail(Context context,  BasePostData data,   BaseHttpCallbackListener<List<MyWalletDetail>> listener);

    public void GetScoreDetail(Context context,  BasePostData data,   BaseHttpCallbackListener<List<MyWalletDetail>> listener);

    public void GetExchangeDetail(Context context,  BasePostData data,   BaseHttpCallbackListener<List<ExchangeHistoryData>> listener);

    public void GetReferee3(Context context,  BasePostData data,   BaseHttpCallbackListener<SecondTabData> listener);

    public void GetReferee2(Context context,  BasePostData data,   BaseHttpCallbackListener<List<GroupListData>> listener);

    public void GetReferee(Context context,  BasePostData data,   BaseHttpCallbackListener<List<GroupListData>> listener);

    public void GetSign(Context context,  BasePostData data,   BaseHttpCallbackListener<SignData> listener);

    public void ExecSign(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void ChgAccount(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);




    //订单
    public void GetOrderList(Context context,  BasePostData data,   BaseHttpCallbackListener<List<OrderListData>> listener);

    public void GetBuyOrderList(Context context,  BasePostData data,   BaseHttpCallbackListener<List<OrderListData>> listener);

    public void GetSellOrderList(Context context,  BasePostData data,   BaseHttpCallbackListener<List<OrderListData>> listener);

    public void UserActivation(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);


    //商品
    public void GetProductList(Context context,  BasePostData data,   BaseHttpCallbackListener<List<ProductDetailsData>> listener);

    public void GetCampProductList(Context context,  BasePostData data,   BaseHttpCallbackListener<List<ProductDetailsData>> listener);

    public void SellProduct(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void TaxExchange(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void BuyMoneyShopping(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void BuyScoreShopping(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void BuySomething(Context context,  BasePostData data,   BaseHttpCallbackListener<BuySomethingData> listener);

    public void UploadPayPic(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);

    public void ComfirmReceive(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);
    public void ReportCheat(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);
    public void UnReportCheat(Context context,  BasePostData data,   BaseHttpCallbackListener<Void> listener);


    public void GetOrderListDetial(Context context,  BasePostData data,   BaseHttpCallbackListener<OrderListData> listener);


    public void GetCampProductDetial(Context context,  BasePostData data,   BaseHttpCallbackListener<ProductDetailsData> listener);




}
