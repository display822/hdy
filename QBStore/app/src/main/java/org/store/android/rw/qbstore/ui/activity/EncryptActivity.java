package org.store.android.rw.qbstore.ui.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ResourceUtils;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.net.AppActionImpl;
import org.store.android.rw.qbstore.net.BaseHttpCallbackListener;
import org.store.android.rw.qbstore.widget.utils.AESUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptActivity extends BaseActivity {

    private RSAPrivateKey rsaPrivateKey;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText("加密测试");

    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    public void initData() {
        super.initData();
            String str = "{\"UserCode\":\"17603065251\",\"Password\":\"E10ADC3949BA59ABBE56E057F20F883E\"}";
        String AES_PUBLIC_KEY = "5E67D551708E8E63A7ADBBE70B4BB758";
        byte[] bytes = EncryptUtils.encryptAES2Base64(str.getBytes(), AES_PUBLIC_KEY.getBytes(), "AES/ECB/PKCS5Padding", null);
        LogUtils.i(new String(bytes));

        String s = ResourceUtils.readAssets2String("key.pem");
        LogUtils.i(s);

        byte[] bytes2 = EncodeUtils.base64Decode(s.getBytes());
        byte[] bytes1 = EncryptUtils.encryptRSA2Base64(str.getBytes(), bytes2
                , true, "RSA/ECB/PKCS1Padding");



        LogUtils.i("RSA 公钥加密" + new String(bytes1));
//
//        AppActionImpl.getInstance().gettest(this,    new String(bytes1) , new BaseHttpCallbackListener<Void>() {
//            @Override
//            public Void onSuccess(Void data) {
//
//                return null;
//            }
//        });
    }


}
