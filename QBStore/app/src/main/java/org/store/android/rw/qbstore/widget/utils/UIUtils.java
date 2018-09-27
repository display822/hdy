package org.store.android.rw.qbstore.widget.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import org.store.android.rw.qbstore.MyApplication;
import org.store.android.rw.qbstore.base.Constant;
import org.store.android.rw.qbstore.data.BasePostData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class UIUtils {


    /**
     * 获取全局上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return MyApplication.getContext();
    }


    /**
     * 获取资源对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 切换activity时的动画效果
     *
     * @param activity
     */
    public static void switchActivityAnim(Activity activity) {
        //activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    public static String getTime() {
        long time = System.currentTimeMillis();//获取系统时间的10位的时间戳
        String str = String.valueOf(time).substring(4);
        return str;
    }


    public static void richText(TextView tv, String str, String regExp) {
        try {
            SpannableStringBuilder style = new SpannableStringBuilder(str);
            Pattern p = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            while (m.find()) {
                int start = m.start(0);
                int end = m.end(0);
                style.setSpan(new BackgroundColorSpan(Color.WHITE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //指定位置文本的背景颜色
                style.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //指定位置文本的字体颜色
            }
            tv.setText(style);
        } catch (PatternSyntaxException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(File filePath) {
        if (filePath.isFile() && filePath.exists()) {
            return filePath.delete();
        }
        return false;
    }

    /**
     * 删除多个
     *
     * @param filesPath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(List<File> filesPath) {
        for (File file : filesPath)
            if (file.isFile() && file.exists()) {
                if (!file.delete())
                    return false;
            }
        return true;
    }


    /**
     * 替换成服务器需要的路径
     *
     * @param s
     * @return
     */
    public static String subStringImg(String s) {
        return s.substring(s.indexOf("imgs"), s.length());
    }

    public static String getNewData() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }


    /**
     * 清空用户信息
     */
    public static void cleanUserInfo() {

    }


    public static void setDisableEmoticon(EditText editText) {
        InputFilter inputFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    //                    Toast.makeText(MainActivity.this,"不支持输入表情", 0).show();
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{inputFilter});
    }

    public static List<String> getTestData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        return list;
    }


    public static String encodeData(Map<String, String> data) {
        return Base64.encode(new Gson().toJson(data));
    }

    public static String encodeData(BasePostData data) {
        return Base64.encode(new Gson().toJson(data));
    }

    /**
     * 获取一个格式化后的字符串
     *
     * @param strResId
     * @return
     */
    public static String getResString(int strResId) {
        String str = UIUtils.getResources().getString(strResId);
        return str;
    }


    public static BasePostData getTokenData() {
        BasePostData postData = new BasePostData();
        postData.setToken(SPUtils.getInstance().getString(Constant.SP_TOKEN));
        return postData;
    }


    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = android.util.Base64.encodeToString(data, android.util.Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public static void showTextDialog(Context context, String title, String message, @Nullable DialogInterface.OnClickListener positiveListener, @Nullable DialogInterface.OnClickListener negativeListene) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(message);
        builder.setPositiveButton("确定", positiveListener);
        builder.setNegativeButton("取消", negativeListene);
        builder.show();
    }
}
