package com.example.alain.profsurfing;

public class User2 {
    public String beginningtime;
    public String endtime;
    public String course;
    public String moreinfo;

    public User2(){

    }

    public User2(String beginningtime, String endtime, String course, String moreinfo){
        this.beginningtime = beginningtime;
        this.endtime = endtime;
        this.course = course;
        this.moreinfo = moreinfo;
    }

    public String getBeginningtime() {
        return beginningtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getCourse() {
        return course;
    }

    public String getMoreinfo() {
        return moreinfo;
    }
}
