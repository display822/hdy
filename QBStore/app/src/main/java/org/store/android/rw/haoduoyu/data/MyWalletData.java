package org.store.android.rw.haoduoyu.data;

public class MyWalletData {
    private String title;
    private String num;

    public MyWalletData(String title, String num) {
        this.title = title;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
