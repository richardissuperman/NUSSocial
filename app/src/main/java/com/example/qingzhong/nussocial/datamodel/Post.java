package com.example.qingzhong.nussocial.datamodel;

import android.graphics.Bitmap;

/**
 * Created by qingzhong on 20/8/15.
 */
public class Post {


    //"usernickname":"richard","text":"fuck you !!!!web api!!!","date":1441929600000,"images":"test.jpg","gender":"male"}]

    public Post(String nickname,String text,String date,String gender,Bitmap[] bitmaps,Bitmap ava,int comments,int likes){

        this.usernickname=nickname;
        this.posttext=text;
        this.date=date;
        this.gender=gender;
        this.images=null;
        this.avatar=ava;
        this.comments=comments;
        this.likes=likes;

    }

    public String usernickname;
    public String posttext;
    public String gender;
    public Bitmap[] images;
    public String date;
    public Bitmap avatar;
    public int comments;
    public int likes;
}
