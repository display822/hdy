package org.store.android.rw.haoduoyu.data;

public class GetNoticeData {

    /**
     * CreateTime : 2018-07-07T21:25:30.15
     * ID : 617
     * IsShow : true
     * IsTop : false
     * NoticeContent : <span>好多魚是一家大型的互联网提提商城，在好多魚商城，物品可以通过买卖而升值。</span>
     * Title : 关于好多魚
     */

    private String CreateTime;
    private int ID;
    private boolean IsShow;
    private boolean IsTop;
    private String NoticeContent;
    private String Title;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isIsShow() {
        return IsShow;
    }

    public void setIsShow(boolean IsShow) {
        this.IsShow = IsShow;
    }

    public boolean isIsTop() {
        return IsTop;
    }

    public void setIsTop(boolean IsTop) {
        this.IsTop = IsTop;
    }

    public String getNoticeContent() {
        return NoticeContent;
    }

    public void setNoticeContent(String NoticeContent) {
        this.NoticeContent = NoticeContent;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }
}
