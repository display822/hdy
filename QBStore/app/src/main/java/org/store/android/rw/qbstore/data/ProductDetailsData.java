package org.store.android.rw.qbstore.data;

public class ProductDetailsData {

    /**
     * ProductID : 00000000-0000-0000-0000-000000000000
     * SourceID :
     * SellerID :
     * Seller :
     * Number : 3
     * ProductPic : http://localhost:8091~/UploadFiles/20184007094028.jpg
     * GetDate :
     * PublicDate :
     * Price : 2000.0
     * Status : 1
     * ProductName : 测试商品
     * Category : 2
     * Remark : 测试内容
     */

    private String Buyer;
    private String ProductID;
    private String SourceID;
    private String SellerID;
    private String Seller;
    private int Number;
    private String ProductPic;
    private String GetDate;
    private String PublicDate;
    private String Price;
    private int Status;
    private String ProductName;
    private int Category;
    private String Remark;
    private String ReleaseDateDjs;


    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public String getReleaseDateDjs() {
        return ReleaseDateDjs;
    }


    public void setReleaseDateDjs(String releaseDateDjs) {
        ReleaseDateDjs = releaseDateDjs;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }

    public String getSourceID() {
        return SourceID;
    }

    public void setSourceID(String SourceID) {
        this.SourceID = SourceID;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String SellerID) {
        this.SellerID = SellerID;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String Seller) {
        this.Seller = Seller;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public String getProductPic() {
        return ProductPic;
    }

    public void setProductPic(String ProductPic) {
        this.ProductPic = ProductPic;
    }

    public String getGetDate() {
        return GetDate;
    }

    public void setGetDate(String GetDate) {
        this.GetDate = GetDate;
    }

    public String getPublicDate() {
        return PublicDate;
    }

    public void setPublicDate(String PublicDate) {
        this.PublicDate = PublicDate;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int Category) {
        this.Category = Category;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}
