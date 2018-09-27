package org.store.android.rw.qbstore.data;

public class OrderListData {

    /**
     * OrderID : 2ea6ed3d-9c1f-4378-8190-30d67c0186c9
     * ProductID : 00000000-0000-0000-0000-000000000000
     * ProductName : 测试商品
     * ProductPic : http://localhost:8091~/UploadFiles/20184007094028.jpg
     * BuyerID : null
     * Buyer : null
     * SelllerID : 67ff1fb1-ee8b-4e1c-9434-2ad34f62beea
     * Seller : null
     * GetDate : 2018-07-11T23:05:27.913
     * GetPrice : 1000.0
     * SoldPrice : 1100.0
     * Status : 3
     * OrderType : null
     * PayPic : null
     * CompleteDate : null
     */

    private String OrderID;
    private String ProductID;
    private String ProductName;
    private String ProductPic;
    private String BuyerID;
    private String Buyer;
    private String SelllerID;
    private String Seller;
    private String GetDate;
    private String GetPrice;
    private String SoldPrice;
    private int Status;
    private int OrderType;
    private String PayPic;
    private String CompleteDate;
    private String SelllerUserCode;
    private String BuyerUserCode;
    private String Account;
    private String SellerAccount;
    /**
     * GetPrice : 1000.0
     * IsCheat : 0
     * timestamp : 0
     */

    private int IsCheat;
    private int timestamp;


    public String getSellerAccount() {
        return SellerAccount;
    }

    public void setSellerAccount(String sellerAccount) {
        SellerAccount = sellerAccount;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getSelllerUserCode() {
        return SelllerUserCode;
    }

    public void setSelllerUserCode(String selllerUserCode) {
        SelllerUserCode = selllerUserCode;
    }

    public String getBuyerUserCode() {
        return BuyerUserCode;
    }

    public void setBuyerUserCode(String buyerUserCode) {
        BuyerUserCode = buyerUserCode;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPic() {
        return ProductPic;
    }

    public void setProductPic(String productPic) {
        ProductPic = productPic;
    }

    public String getBuyerID() {
        return BuyerID;
    }

    public void setBuyerID(String buyerID) {
        BuyerID = buyerID;
    }

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public String getSelllerID() {
        return SelllerID;
    }

    public void setSelllerID(String selllerID) {
        SelllerID = selllerID;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public String getGetDate() {
        return GetDate;
    }

    public void setGetDate(String getDate) {
        GetDate = getDate;
    }

    public String getGetPrice() {
        return GetPrice;
    }

    public void setGetPrice(String getPrice) {
        GetPrice = getPrice;
    }

    public String getSoldPrice() {
        return SoldPrice;
    }

    public void setSoldPrice(String soldPrice) {
        SoldPrice = soldPrice;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public String getPayPic() {
        return PayPic;
    }

    public void setPayPic(String payPic) {
        PayPic = payPic;
    }

    public String getCompleteDate() {
        return CompleteDate;
    }

    public void setCompleteDate(String completeDate) {
        CompleteDate = completeDate;
    }

    public int getIsCheat() {
        return IsCheat;
    }

    public void setIsCheat(int IsCheat) {
        this.IsCheat = IsCheat;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
