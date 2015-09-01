package com.example.qingzhong.nussocial.utls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.example.qingzhong.nussocial.adapter.PlaygroundRecyclerViewAdapter;
import com.example.qingzhong.nussocial.datamodel.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by qingzhong on 20/8/15.
 */
public class ListUtils implements Handler.Callback{

    private static final AwsUtils awsUtils=new AwsUtils();
    private Handler handler=new Handler();
    private static Bitmap avatar;
    private static  Bitmap[] bitmaps;
    private static PlaygroundRecyclerViewAdapter adapter;

    public ListUtils(PlaygroundRecyclerViewAdapter adp){
        this.adapter=adp;
        this.handler=new Handler(this);
    }
    public  ArrayList<Post> postToArray(JSONArray jsonArray,ArrayList<Post> list){



        for(int i=0;i<jsonArray.length();i++){
            try {
                final JSONObject object = jsonArray.getJSONObject(i);
                String nickname=object.getString("usernickname");
                String text=object.getString("text");
                final String time=object.getString("date");
                String gender=object.getString("gender");
              //  Bitmap[] bitmaps=null;

                final Post post=new Post(nickname,text,time,gender,bitmaps,avatar);
                list.add(post);



                new Thread(new Runnable() {
                    @Override
                    public void run() {

                      //  synchronized (avatar) {

                            try {

                                File file = awsUtils.DownloadImages(object.getString("avatar"));
                                post.avatar= BitmapFactory.decodeFile(file.getAbsolutePath());
                               // post.avatar=avatar;
                                handler.sendMessage(new Message());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                       // }
                    }
                }).start();


//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        while(avatar==null){
//
//                        }
//                        post.avatar=avatar;
//                        avatar=null;
//                        handler.sendMessage(new Message());
//                        //adapter.notifyDataSetChanged();
//                    }
//                }).start();










                new Thread(new Runnable() {
                    @Override
                    public void run() {

                       // synchronized (bitmaps) {

                            try {


                                File file = awsUtils.DownloadImages(object.getString("images"));
                                post.images = new Bitmap[1];
                                post.images[0] = BitmapFactory.decodeFile(file.getAbsolutePath());
                               // post.images = bitmaps;
                                handler.sendMessage(new Message());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                       // }
                    }
                }).start();




//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        while(bitmaps==null){
//
//                        }
//                        post.images=bitmaps;
//                        bitmaps=null;
//                        handler.sendMessage(new Message());
//                        //adapter.notifyDataSetChanged();
//                    }
//                }).start();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;

    }


    @Override
    public boolean handleMessage(Message msg) {

        adapter.notifyDataSetChanged();
        return false;
    }
}
