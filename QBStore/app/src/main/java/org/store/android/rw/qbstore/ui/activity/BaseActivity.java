package org.store.android.rw.qbstore.ui.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.base.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sun on 2018/7/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ImmersionBar mImmersionBar;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private ImageView mToolbarLeftBack;
    private Unbinder mUnbinder;

    /**
     * 布局id
     *
     * @return
     */
    public abstract int getContentViewResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getContentViewResId() != 0)
            setContentView(getContentViewResId());
        mUnbinder = ButterKnife.bind(this);
        if (isImmersionBarEnabled())
            initImmersionBar();
        AppManager.getAppManager().addActivity(this);
        initToolbar();
        initView(savedInstanceState);
        initAdapter();
        initData();
        initEvent();

    }

    /**
     * 初始化视图
     */
    public abstract void initView(Bundle savedInstanceState);


    public void initEvent() {
    }

    public void initData() {

    }

    public void initAdapter() {

    }

    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbar_subtitle);
        mToolbarLeftBack = (ImageView) findViewById(R.id.toobar_left_back);

        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        /**
         * 判断是否有Toolbar,并默认显示返回按钮
         */
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        } else {

        }
    }


    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * @return
     */
    public TextView getSubTitle() {
        return mToolbarSubTitle;
    }


    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        View top_view = findViewById(R.id.top_view);
        if (top_view != null) {
            mImmersionBar.statusBarView(top_view).statusBarColor(R.color.rgb_eeeeee);
        }
        mImmersionBar.init();
    }


    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


    //    /**
//     * type 1 白色背景  黑色字体
//     *
//     * @param type
//     */
    public void setToolBarColor() {
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        mToolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.rgb_333333));
        mToolbarSubTitle.setTextColor(ContextCompat.getColor(this, R.color.rgb_333333));
        if ( isShowBacking()){
            mToolbarLeftBack.setRotation(180);
            mToolbarLeftBack.setImageResource(R.mipmap.gray_right_back);
        }
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        if (null != mToolbarLeftBack)
            mToolbarLeftBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }
}
