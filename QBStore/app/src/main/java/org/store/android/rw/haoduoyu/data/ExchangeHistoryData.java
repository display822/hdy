package org.store.android.rw.haoduoyu.data;

public class ExchangeHistoryData {

    /**
     * ExchangeID : 0850cd6a-9b07-4c20-bef1-086b5f2711f0
     * ProductID : 00000000-0000-0000-0000-000000000000
     * ProductName : 测试商品
     * ProductPic : http://localhost:8091~/UploadFiles/20184007094028.jpg
     * BuyerID : 67ff1fb1-ee8b-4e1c-9434-2ad34f62beea
     * Buyer : 任文
     * SelllerID : null
     * Selller : null
     * ExchangeType : 0
     * GetDate : 2018-07-07T22:30:02.6
     * kuaididanhao : 1232131231232132131232131
     * Address : xxxxx
     * IsYfs : 0
     */

    private String ExchangeID;
    private String ProductID;
    private String ProductName;
    private String ProductPic;
    private String BuyerID;
    private String OrderID;
    private String Buyer;
    private String SelllerID;
    private String Selller;
    private int ExchangeType;
    private String GetDate;
    private String kuaididanhao;
    private String Address;
    private String Price;
    private String UserCode;
    private int IsYfs;

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getExchangeID() {
        return ExchangeID;
    }

    public void setExchangeID(String ExchangeID) {
        this.ExchangeID = ExchangeID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getProductPic() {
        return ProductPic;
    }

    public void setProductPic(String ProductPic) {
        this.ProductPic = ProductPic;
    }

    public String getBuyerID() {
        return BuyerID;
    }

    public void setBuyerID(String BuyerID) {
        this.BuyerID = BuyerID;
    }

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String Buyer) {
        this.Buyer = Buyer;
    }

    public String getSelllerID() {
        return SelllerID;
    }

    public void setSelllerID(String SelllerID) {
        this.SelllerID = SelllerID;
    }

    public String getSelller() {
        return Selller;
    }

    public void setSelller(String Selller) {
        this.Selller = Selller;
    }

    public int getExchangeType() {
        return ExchangeType;
    }

    public void setExchangeType(int ExchangeType) {
        this.ExchangeType = ExchangeType;
    }

    public String getGetDate() {
        return GetDate;
    }

    public void setGetDate(String GetDate) {
        this.GetDate = GetDate;
    }

    public String getKuaididanhao() {
        return kuaididanhao;
    }

    public void setKuaididanhao(String kuaididanhao) {
        this.kuaididanhao = kuaididanhao;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public int getIsYfs() {
        return IsYfs;
    }

    public void setIsYfs(int IsYfs) {
        this.IsYfs = IsYfs;
    }
}
