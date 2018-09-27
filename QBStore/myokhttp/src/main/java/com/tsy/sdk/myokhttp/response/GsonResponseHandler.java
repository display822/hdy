package com.tsy.sdk.myokhttp.response;

import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Gson类型的回调接口
 * Created by tsy on 16/8/15.
 */
public abstract class GsonResponseHandler<T> implements IResponseHandler {

    private Type mType;
    private String mAes;

    public GsonResponseHandler() {
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    public GsonResponseHandler(String aes) {
        mAes = aes;
        Type myclass = getClass().getGenericSuperclass();    //反射获取带泛型的class
        if (myclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameter = (ParameterizedType) myclass;      //获取所有泛型
        mType = $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);  //将泛型转为type
    }

    private Type getType() {
        return mType;
    }

    @Override
    public final void onSuccess(final Response response) {
        ResponseBody responseBody = response.body();
        String responseBodyStr = "";

        try {
            responseBodyStr = responseBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail read response body");
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail read response body");
                }
            });
            return;
        } finally {
            responseBody.close();
        }

        try {
            responseBodyStr = new String(EncryptUtils.decryptBase64AES(new JSONObject(responseBodyStr).getString("ResultCode").getBytes(), getAes().getBytes(), "AES/ECB/PKCS5Padding", null));
        } catch (JSONException e) {
            e.printStackTrace();
            com.blankj.utilcode.util.LogUtils.i("解密错误");
            return;
        }
        com.blankj.utilcode.util.LogUtils.i("解密后的数据-----" + responseBodyStr);
        final String finalResponseBodyStr = responseBodyStr;


        try {
            Gson gson = new Gson();
            final T gsonResponse = (T) gson.fromJson(finalResponseBodyStr, getType());
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(response.code(), gsonResponse);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("onResponse fail parse gson, body=" + finalResponseBodyStr);
            MyOkHttp.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(response.code(), "fail parse gson, body=" + finalResponseBodyStr);
                }
            });

        }
    }


    public String getAes() {
        return mAes;
    }

    public void setAes(String aes) {
        mAes = aes;
    }

    public abstract void onSuccess(int statusCode, T response);

    @Override
    public void onProgress(long currentBytes, long totalBytes) {

    }
}
