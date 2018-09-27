package org.store.android.rw.qbstore.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;

import org.store.android.rw.qbstore.R;
import org.store.android.rw.qbstore.base.Constant;
import org.store.android.rw.qbstore.data.GetUserInfoData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    private AgentWeb mAgentWeb;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_webview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setToolBarColor();
        getToolbarTitle().setText("");
    }

    @Override
    public void initData() {
        GetUserInfoData data = new Gson().fromJson(SPUtils.getInstance().getString(Constant.SP_USER_INFO), GetUserInfoData.class);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLlContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(data.getRegisterLink());
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
