package com.example.qingzhong.nussocial.utls;

import com.example.qingzhong.nussocial.datamodel.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by qingzhong on 20/8/15.
 */
public class ListUtils {

    public static void postToArray(JSONArray jsonArray, ArrayList<Post> list){

        list=new ArrayList<Post>();

        for(int i=0;i<jsonArray.length();i++){
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                String userid=object.getString("userid");
                String text=object.getString("text");
                String time=object.getString("time");

              //  Post post=new Post();
               // post.
               // list.add();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
