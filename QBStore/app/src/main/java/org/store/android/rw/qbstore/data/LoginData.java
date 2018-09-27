package org.store.android.rw.qbstore.data;

public class LoginData {

    /**
     * Token : 4e6274ce-d6dd-4c11-a1fa-19e60a105c96
     * UserCode : 15912344321
     * UserID : e456cc4f-90b3-4dfa-8f28-2d203d76d8c0
     */

    private String Token;
    private String UserCode;
    private String UserID;
    private String AES;


    public String getAES() {
        return AES;
    }

    public void setAES(String AES) {
        this.AES = AES;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }
}
