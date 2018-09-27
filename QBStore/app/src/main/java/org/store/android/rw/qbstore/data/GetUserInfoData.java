package org.store.android.rw.qbstore.data;

public class GetUserInfoData {

    /**
     * UserID : 67ff1fb1-ee8b-4e1c-9434-2ad34f62beea
     * UserCode : 17671649722
     * RealName : 陈杰
     * RefereePhone : 13163275278
     * CreateDate : 2018-07-06T20:59:49.627
     * CardId : 42130299010258435
     * CardIDPic : null
     * IsValid : 1
     * Receiver : 陈洁
     * ReceiverPhone : 17671649722
     * Province : 湖北省
     * City : 武汉市
     * ADDRESS : 硚口区
     * Account : 17671649722
     * WalletAmount : 2400
     * Status : 1
     * Level : 骑兵
     * Score : 1100
     * Signature : null
     * RefereeNum : 5
     * RefereeL2Num : 10
     */

    private String UserID;
    private String UserCode;
    private String RealName;
    private String RefereePhone;
    private String CreateDate;
    private String CardId;
    private String CardIDPic;
    private int IsValid;
    private String Receiver;
    private String ReceiverPhone;
    private String Province;
    private String City;
    private String ADDRESS;
    private String Account;
    private int WalletAmount;
    private int Status;
    private String Level;
    private int Score;
    private String Signature;
    private String RegisterLink;
    private int RefereeNum;
    private int RefereeL2Num;


    public String getRegisterLink() {
        return RegisterLink;
    }

    public void setRegisterLink(String registerLink) {
        RegisterLink = registerLink;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String RealName) {
        this.RealName = RealName;
    }

    public String getRefereePhone() {
        return RefereePhone;
    }

    public void setRefereePhone(String RefereePhone) {
        this.RefereePhone = RefereePhone;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String CardId) {
        this.CardId = CardId;
    }

    public String getCardIDPic() {
        return CardIDPic;
    }

    public void setCardIDPic(String CardIDPic) {
        this.CardIDPic = CardIDPic;
    }

    public int getIsValid() {
        return IsValid;
    }

    public void setIsValid(int IsValid) {
        this.IsValid = IsValid;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String ReceiverPhone) {
        this.ReceiverPhone = ReceiverPhone;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String Account) {
        this.Account = Account;
    }

    public int getWalletAmount() {
        return WalletAmount;
    }

    public void setWalletAmount(int WalletAmount) {
        this.WalletAmount = WalletAmount;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public int getRefereeNum() {
        return RefereeNum;
    }

    public void setRefereeNum(int RefereeNum) {
        this.RefereeNum = RefereeNum;
    }

    public int getRefereeL2Num() {
        return RefereeL2Num;
    }

    public void setRefereeL2Num(int RefereeL2Num) {
        this.RefereeL2Num = RefereeL2Num;
    }
}
