package org.store.android.rw.qbstore.widget;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.Button;

public class TimeCount extends CountDownTimer {

    private Button mButton;

    public TimeCount(long millisInFuture, long countDownInterval, Button button) {
        super(millisInFuture, countDownInterval);
        mButton = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mButton.setEnabled(false);
        mButton.setText("(" + millisUntilFinished / 1000 + ") 秒");
    }

    @Override
    public void onFinish() {
        mButton.setText("短信验证码");
        mButton.setEnabled(true);

    }
}
