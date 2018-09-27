package org.store.android.rw.qbstore.data;

public class BasePostData<T> {

    private T Data;

    private String Token;


    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }


}
