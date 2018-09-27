package org.store.android.rw.qbstore.net;


import com.blankj.utilcode.util.ToastUtils;


import java.io.Serializable;

/**
 *

 */
public class ApiResponse<T> implements Serializable {


    // 返回的数据
    private T Data;
    // 返回的状态码
    private boolean Result;
    // 返回的状态消息
    private String Message;

    private String StrCode;

    private String AES;

    public String getAES() {
        return AES;
    }

    public void setAES(String AES) {
        this.AES = AES;
    }

    public ApiResponse(boolean Result, String Message) {
        this.Result = Result;
        this.Message = Message;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return Result;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        this.Data = data;
    }


    public void showMsg() {
        ToastUtils.showShort(getMessage());
        return;
    }

    public String getStrCode() {
        return StrCode;
    }

    public void setStrCode(String strCode) {
        StrCode = strCode;
    }
}
