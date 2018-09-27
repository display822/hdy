package org.store.android.rw.haoduoyu.ui.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import org.store.android.rw.haoduoyu.R;
import org.store.android.rw.haoduoyu.base.Constant;
import org.store.android.rw.haoduoyu.widget.CommonVideoView;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.cv_view)
    CommonVideoView mCvView;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        playVideoView();
    }

    private void playVideoView() {
        mCvView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.start));
        mCvView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
            }
        });
        //循环播放
        mCvView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mCvView.start();
            }
        });
        mCvView.start();
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        playVideoView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        mCvView.stopPlayback();
        super.onStop();
    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String token = SPUtils.getInstance().getString(Constant.SP_TOKEN);
        if (TextUtils.isEmpty(token)) {
            ActivityUtils.startActivity(this, LoginActivity.class);
        } else {
            ActivityUtils.startActivity(this, MainActivity.class);
        }
        finish();
    }
}
