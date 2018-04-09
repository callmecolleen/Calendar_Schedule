package com.example.test1;

import org.litepal.crud.DataSupport;

public class Item extends DataSupport{

    //  自增id？
    private int id;

    //  提醒日期
    private int it_year;
    private int it_month;
    private int it_day;


    //  提醒时间
    private int alarm_hour;
    private int alarm_min;
    private int alarm_sec;

    //  具体事项
    private String my_item;

    //  二进制图片
    private byte[] new_image;

    //  是否提醒
    /*private boolean alarm;*/

    public Item(){
        this.id = 0;
        this.it_year = 1997;
        this.it_month = 1;
        this.it_day = 26;
        this.alarm_hour = 0;
        this.alarm_min = 0;
        this.alarm_sec = 0;
        this.my_item = null;
        /*this.alarm = false;*/
    }

    public Item(int id, int year, int month, int day,
                int hour, int min, int sec, String item/* int pic_num,*/ /*boolean ToF*/){
        this.id = id;
        this.it_year = year;
        this.it_month = month;
        this.it_day = day;
        this.alarm_hour = hour;
        this.alarm_min = min;
        this.alarm_sec = sec;
        this.my_item = item;
        //  this.custom_image = pic_num;
        //  this.alarm = ToF;
    }


    public int getIt_year(){
        return it_year;
    }
    public void setIt_year(int year){ this.it_year = year;}

    public int getIt_month(){
        return it_month;
    }
    public void setIt_month(int month){ this.it_month = month;}

    public int getIt_day(){
        return it_day;
    }
    public void setIt_day(int day){ this.it_day = day;}

    public int getAlarm_hour(){
        return alarm_hour;
    }
    public void setAlarm_hour(int hour){ this.alarm_hour = hour;}

    public int getAlarm_min(){
        return alarm_min;
    }
    public void setAlarm_min(int min){ this.alarm_min = min;}

    public int getAlarm_sec(){
        return alarm_sec;
    }
    public void setAlarm_sec(int sec){
        this.alarm_sec = sec;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    /*public boolean getAlarm(){
        return this.alarm;
    }
    public void setAlarm(boolean ToF){ this.alarm = ToF; }*/

    /*public int getCustom_image(){
        return this.custom_image;
    }
    public void setCustom_image(int image_num){ this.custom_image = image_num; }*/

    public String getMy_item(){
        return my_item;
    }
    public void setMy_item(String item){ this.my_item = item; }

    //  二进制图片的一些方法
    public byte[] getNew_image() {
        return new_image;
    }

    public void setNew_image(byte[] new_image) {
        this.new_image = new_image;
    }
}
