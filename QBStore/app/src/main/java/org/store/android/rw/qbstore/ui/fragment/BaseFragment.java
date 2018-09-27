package org.store.android.rw.qbstore.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

import org.store.android.rw.qbstore.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 基于Fragmentation框架实现沉浸式，如果要在Fragment实现沉浸式，请在onSupportVisible实现沉浸式，
 */

public abstract class BaseFragment extends SupportFragment {
    protected Activity mActivity;
    protected View mRootView;
    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (view != null) {
            View titleBar = view.findViewById(setTitleBar());
            if (titleBar != null)
                ImmersionBar.setTitleBar(mActivity, titleBar);
            View statusBarView = view.findViewById(setStatusBarView());
            if (statusBarView != null)
                ImmersionBar.setStatusBarView(mActivity, statusBarView);
        }
        initView();
        initData();
        setListener();
    }

    protected int setTitleBar() {
        return R.id.toolbar;
    }

    protected int setStatusBarView() {
        return 0;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //如果要在Fragment单独使用沉浸式，请在onSupportVisible实现沉浸式
        if (isImmersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.navigationBarWithKitkatEnable(false).init();
        }
    }

    private boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();

        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }
}
