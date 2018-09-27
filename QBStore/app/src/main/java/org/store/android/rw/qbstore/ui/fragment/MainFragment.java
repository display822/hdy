package org.store.android.rw.qbstore.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.server.callback.ConfigRequestCallback;

import org.store.android.rw.qbstore.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Sun on 2018/7/7.
 */

public class MainFragment extends BaseFragment {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    @BindView(R.id.ll_first)
    LinearLayout mLlFirst;
    @BindView(R.id.ll_second)
    LinearLayout mLlSecond;
    @BindView(R.id.ll_third)
    LinearLayout mLlThird;
    @BindView(R.id.ll_fourth)
    LinearLayout mLlFourth;

    private SupportFragment[] mFragments = new SupportFragment[4];

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstTabFragment = findChildFragment(FirstTabFragment.class);
        if (firstTabFragment == null) {
            mFragments[FIRST] = FirstTabFragment.newInstance();
            mFragments[SECOND] = SecondTabFragment.newInstance();
            mFragments[THIRD] = ThirdTabFragment.newInstance();
            mFragments[FOURTH] = FourthTabFragment.newInstance();
            loadMultipleRootFragment(R.id.content, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]);
        } else {
            mFragments[FIRST] = firstTabFragment;
            mFragments[SECOND] = findChildFragment(SecondTabFragment.class);
            mFragments[THIRD] = findChildFragment(ThirdTabFragment.class);
            mFragments[FOURTH] = findChildFragment(FourthTabFragment.class);
        }
    }

    @Override
    protected void initData() {
        TinkerPatch.with().fetchDynamicConfig(new ConfigRequestCallback() {
            @Override
            public void onSuccess(HashMap<String, String> hashMap) {
                String s = hashMap.get("status");
                LogUtils.i(s);
                if (Integer.parseInt(s) == 2) {
                    SPUtils.getInstance().clear();
                    getActivity().finish();
                }
            }

            @Override
            public void onFail(Exception e) {

            }
        }, true);
    }

    @Override
    protected void initView() {
        tabSelected(mLlFirst);
    }

    private void tabSelected(LinearLayout linearLayout) {
        mLlFirst.setSelected(false);
        mLlSecond.setSelected(false);
        mLlThird.setSelected(false);
        mLlFourth.setSelected(false);
        linearLayout.setSelected(true);
    }


    @OnClick({R.id.ll_first, R.id.ll_second, R.id.ll_third, R.id.ll_fourth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_first:
                showHideFragment(mFragments[FIRST]);
                tabSelected(mLlFirst);
                break;
            case R.id.ll_second:
                showHideFragment(mFragments[SECOND]);
                tabSelected(mLlSecond);
                break;
            case R.id.ll_third:
                showHideFragment(mFragments[THIRD]);
                tabSelected(mLlThird);
                break;
            case R.id.ll_fourth:
                showHideFragment(mFragments[FOURTH]);
                tabSelected(mLlFourth);
                break;
        }
    }
}
