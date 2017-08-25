package com.fireegg1991.poemthree;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alfo6-17 on 2017-08-07.
 */

public class Item implements Parcelable{
    int num;
    String title;
    String userName;
    String date;
    String con1;
    String con2;
    String con3;
    long id;

    protected Item(Parcel in) {
        num = in.readInt();
        title = in.readString();
        userName = in.readString();
        date = in.readString();
        con1 = in.readString();
        con2 = in.readString();
        con3 = in.readString();
        id = in.readLong();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getCon1() {
        return con1;
    }

    public void setCon1(String con1) {
        this.con1 = con1;
    }

    public String getCon2() {
        return con2;
    }

    public void setCon2(String con2) {
        this.con2 = con2;
    }

    public String getCon3() {
        return con3;
    }

    public void setCon3(String con3) {
        this.con3 = con3;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Item() {
    }

    public Item(int num, String title, String userName, String date) {
        this.num = num;
        this.title = title;
        this.userName = userName;
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(num);
        dest.writeString(title);
        dest.writeString(userName);
        dest.writeString(date);
        dest.writeString(con1);
        dest.writeString(con2);
        dest.writeString(con3);
        dest.writeLong(id);
    }
}
