package org.store.android.rw.qbstore.net;


import org.store.android.rw.qbstore.base.Constant;

import java.io.File;
import java.util.List;


/**
 * @类名：Api.java
 * @描述：访问网络Api接口
 */
public interface Api {


//    public String SERVER_URL = "http://www.pflhw.com/"; //测试服务器
    public String SERVER_URL = "http://180.76.160.130:8090"; //测试服务器
    /**
     * Server Connection Address
     */
    public final static String USER_URL = SERVER_URL + "userservices.asmx/";

    public final static String ORDER_URL = SERVER_URL + "orderservices.asmx/";

    public final static String PRODUCT_URL = SERVER_URL + "productservices.asmx/";


    // 用户登录接口
    public final static String LOGIN_URL = USER_URL + "Login";

    //注册
    public final static String REGISTER_URL = USER_URL + "Register";

    //获取验证码
    public final static String GETSMSVALIDCODE_URL = USER_URL + "GetSmsValidCode";

    //验证验证码
    public final static String VALIDCODE_URL = USER_URL + "ValidCode ";

    //获取时间戳
    public final static String GETTIMESTAMP_URL = USER_URL + "GetTIMESTAMP ";

    //修改密码
    public final static String CHGPASSWORD_URL = USER_URL + "ChgPassword";

    //获取用户信息
    public final static String GETUSERINFO_URL = USER_URL + "GetUserInfo";

    //获取公告信息
    public final static String GETNOTICE_URL = USER_URL + "GetNotice";

    //修改地址信息
    public final static String POSTCHGADDRESS_URL = USER_URL + "ChgAddress";

    //身份认证
    public final static String VALIDINFO_URL = USER_URL + "ValidInfo";

    //获取钱包记录
    public final static String GETWALLETDETAIL_URL = USER_URL + "GetWalletDetail";

    //获取积分明细
    public final static String GETSCOREDETAIL_URL = USER_URL + "GetScoreDetail";

    public final static String GETEXCHANGEDETAIL_URL = USER_URL + "GetExchangeDetail";

    //    推广接口
    public final static String GETREFEREE3_URL = USER_URL + "GetRefereeL3 ";

    //获取代理列表
    public final static String GETREFEREEL2_URL = USER_URL + "GetReferee2 ";

    public final static String GETREFEREE_URL = USER_URL + "GetReferee";
//    激活会员
    public final static String USERACTIVATION_URL = USER_URL + "UserActivation";

    //获取今天是否签到
    public final static String GETSIGN_URL = USER_URL + "GetSign";

    //今日签到
    public final static String EXECSIGN_URL = USER_URL + "ExecSign";


    public final static String CHGACCOUNT_URL = USER_URL + "ChgAccount";






    //订单
    //获取全部订单
    public final static String GETORDERLIST_URL = ORDER_URL + "GetOrderList";

    //    获取购入订单
    public final static String GETBUYORDERLIST_URL = ORDER_URL + "GetBuyOrderList";

    //获取卖出订单
    public final static String GETSELLORDERLIST_URL = ORDER_URL + "GetSellOrderList";

    //    出售商品
    public final static String SELLPRODUCT_URL = ORDER_URL + "SellProduct";

    //    库存税换
    public final static String TAXEXCHANGE_URL = ORDER_URL + "TaxExchange";
    //钱包商城购买
    public final static String BUYMONEYSHOPPING_URL = ORDER_URL + "BuyMoneyShopping";

    //积分商城购买
    public final static String BUYSCORESHOPPING_URL = ORDER_URL + "BuyScoreShopping";

    //购买市场商品
    public final static String BUYSOMETHING_URL = ORDER_URL + "BuySomething";

    public final static String GETORDERLISTDETIAL_URL = ORDER_URL + "GetOrderListDetial";


    /**
     * 上传支付证明
     */
    public final static String UPLOADPAYPIC_URL = ORDER_URL + "UploadPayPic";
    //确认收款
    public final static String COMFIRMRECEIVE_URL = ORDER_URL + "ComfirmReceive";
    //    举报虚假上传
    public final static String REPORTCHEAT_URL = ORDER_URL + "ReportCheat";
    //    撤销虚假上传
    public final static String UNREPORTCHEAT_URL = ORDER_URL + "UnReportCheat";


    //商品
    public final static String GETPRODUCTLIST_URL = PRODUCT_URL + "GetProductList";

    //获取仓库列表
    public final static String GETCAMPPRODUCTLIST_URL = PRODUCT_URL + "GetCampProductList";
    //商品详情
    public final static String GETCAMPPRODUCTDETIAL_URL = PRODUCT_URL + "GetCampProductDetial";
}
