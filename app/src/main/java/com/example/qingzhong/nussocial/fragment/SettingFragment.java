package com.example.qingzhong.nussocial.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.cons.IdentityCons;
import com.example.qingzhong.nussocial.cons.StatusCons;
import com.example.qingzhong.nussocial.interfaces.VolleyContext;
import com.example.qingzhong.nussocial.utls.AwsUtils;
import com.example.qingzhong.nussocial.utls.PreferenceUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * Created by qingzhong on 5/8/15.
 */
public class SettingFragment extends Fragment implements  Handler.Callback, VolleyContext{


    private ImageView profilepic;
    private TextView profilename;
    private RequestQueue queue;
    private Request request;
    private String profile_img_name;
    private static File map;
    private Handler handler;
    private PreferenceUtil preferenceUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.settinglayout,container,false);

        handler=new Handler(this);
        preferenceUtil=new PreferenceUtil(getActivity());

        try {
            //init volley request and queue
            initVolleyWidgets();
            //init all the views
            initUI(view);
            //determine if we have cache values for profile
            startDownloading();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return view;
    }




    private void initUI(View view){
        profilepic=(ImageView)view.findViewById(R.id.setting_profile_img);
        profilename=(TextView)view.findViewById(R.id.setting_profile_name);
    }


    @Override
    public void initVolleyWidgets() {
        //init the Volley queue
        queue= Volley.newRequestQueue(getActivity());

        //init the request
        request=new JsonObjectRequest(new String(IdentityCons.hostName+"user/richard_johnson@sina.com"), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("data from network", response.toString());

                try{
                    //parse the JSON object
                    profile_img_name=response.getString("avatar");
                    String name=response.getString("nickname");
                    profilename.setText(name);
                    preferenceUtil.setProfileName(name);

                }
                catch (Exception e){
                    return;
                }

                //once all the profile data has been retrived, start downloading the profile image
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            map = new AwsUtils().DownloadImages(profile_img_name);
                            preferenceUtil.setProfileFilePath(map.getAbsolutePath());
                        }

                        catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("failed!",error.toString());
                error.printStackTrace();

            }
        });


        request.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    @Override
    public void startDownloading() throws IOException {

        String profilePath=preferenceUtil.getProfileFilePath();
        //start monitoring if the profile img has been got
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(map==null){
                }
                Message msg=new Message();
                msg.what= StatusCons.BITMAP_COMPLETE;
                handler.sendMessage(msg);
            }
        }).start();

        //no cache, start downloading from aws
        if(profilePath==null) {
            queue.add(request);
        }

        //has cache set all the values in UI
        else{
            Log.e("retrive data from Local","begin");
            Log.e("LOCAL PROFILE BITMAP",profilePath);
            Log.e("LOCAL PROFILE NAME",preferenceUtil.getProfileName());
            map=new File(profilePath);
            profilename.setText(preferenceUtil.getProfileName());
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what==StatusCons.BITMAP_COMPLETE){

            profilepic.setImageBitmap(BitmapFactory.decodeFile(map.getAbsolutePath()));

            return true;

        }
        return false;
    }
}
