package org.store.android.rw.qbstore;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.server.callback.ConfigRequestCallback;
import com.tsy.sdk.myokhttp.MyOkHttp;

import org.store.android.rw.qbstore.widget.GlideImageLoader;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Sun on 2018/7/7.
 */
//java -jar screenMatchPX.jar 320,480;480,800;480,854;540,888;600,1024;720,1184;720,1196;720,1280;720,1208;768,1024;768,1280;768,1184;800,1280;1080,1794;1080,1812;1080,1821;1080,1920;1440,2560;1440,2392;
public class MyApplication extends Application {
    private static Context mContext; // 全局上下文对象
    private static MyApplication mInstance;
    private MyOkHttp mMyOkHttp;
    private ApplicationLike tinkerApplicationLike;


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public MyOkHttp getMyOkHttp() {
        return mMyOkHttp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = getApplicationContext(); // 获取全局上下文对象

        LogUtils.getConfig().setLogSwitch(false);
        //持久化存储cookie
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));

        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);

        //自定义OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)       //设置开启cookie
                .addInterceptor(logging)            //设置开启log
                .build();
        mMyOkHttp = new MyOkHttp(okHttpClient);

        //默认
//        mMyOkHttp = new MyOkHttp();
        initPicker();
        initTinke();
    }

    private void initTinke() {
        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化 SDK
        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(3);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果

    }

    private void initPicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(600);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(600);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);//保存文件的高度。单位像素
    }

    /**
     * 获取application的全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }
}
