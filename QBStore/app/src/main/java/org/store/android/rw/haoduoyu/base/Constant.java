package org.store.android.rw.haoduoyu.base;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ResourceUtils;

public interface Constant {

    String SP_TOKEN = "token";
    String SP_USER_ID = "userID";
    String SP_USER_CODE = "userCode";
    String SP_USER_INFO = "userInfo";

    byte[] PUBLIC_KEY = EncodeUtils.base64Decode(ResourceUtils.readAssets2String("key.pem"));

    int REAL_NAME_REQUEST_CODE = 1; //身份验证
    int ORDER_IMG_REQUEST_CODE = 2;//订单详情
}
