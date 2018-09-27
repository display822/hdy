package org.store.android.rw.qbstore.data;

public class MyWalletDetail {

    /**
     * WalletDtlID : caa9dcfc-884f-4350-bb13-a682009025c4
     * UserID : 67ff1fb1-ee8b-4e1c-9434-2ad34f62beea
     * Amount : 10
     * Source : 0
     * AfterAmout : 10
     * BeforeAmout : 0
     * GetDate : 2018-07-13T16:38:38.177
     */

    private String WalletDtlID;
    private String UserID;
    private int Amount;
    private int Source;
    private int AfterAmout;
    private int BeforeAmout;
    private String GetDate;
    /**
     * ScoreDtlID : 3d878bb6-a679-44fa-a8a6-d2487989e957
     * BeforeNumber : 1100
     * AfterNumber : 1100
     * Number : 0
     */

    private String ScoreDtlID;
    private String RealName;
    private int BeforeNumber;
    private int AfterNumber;
    private int Number;


    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getWalletDtlID() {
        return WalletDtlID;
    }

    public void setWalletDtlID(String WalletDtlID) {
        this.WalletDtlID = WalletDtlID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public int getSource() {
        return Source;
    }

    public void setSource(int Source) {
        this.Source = Source;
    }

    public int getAfterAmout() {
        return AfterAmout;
    }

    public void setAfterAmout(int AfterAmout) {
        this.AfterAmout = AfterAmout;
    }

    public int getBeforeAmout() {
        return BeforeAmout;
    }

    public void setBeforeAmout(int BeforeAmout) {
        this.BeforeAmout = BeforeAmout;
    }

    public String getGetDate() {
        return GetDate;
    }

    public void setGetDate(String GetDate) {
        this.GetDate = GetDate;
    }

    public String getScoreDtlID() {
        return ScoreDtlID;
    }

    public void setScoreDtlID(String ScoreDtlID) {
        this.ScoreDtlID = ScoreDtlID;
    }

    public int getBeforeNumber() {
        return BeforeNumber;
    }

    public void setBeforeNumber(int BeforeNumber) {
        this.BeforeNumber = BeforeNumber;
    }

    public int getAfterNumber() {
        return AfterNumber;
    }

    public void setAfterNumber(int AfterNumber) {
        this.AfterNumber = AfterNumber;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }
}
