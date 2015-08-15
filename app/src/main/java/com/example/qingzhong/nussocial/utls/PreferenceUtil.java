package com.example.qingzhong.nussocial.utls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.qingzhong.nussocial.cons.IdentityCons;

/**
 * Created by qingzhong on 15/8/15.
 */
public class PreferenceUtil {




    private Activity context;
    private SharedPreferences prefs;


    public PreferenceUtil(Activity activity){

        this.context=activity;
        prefs=context.getPreferences(Context.MODE_PRIVATE);

    }

    public void setProfileFilePath(String filePath){

        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(IdentityCons.identityArray[0],filePath);
        editor.commit();

    }

    public String getProfileFilePath(){
        return prefs.getString(IdentityCons.identityArray[0],null);
    }


    public void setProfileName(String name){
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString(IdentityCons.identityArray[1],name);
        editor.commit();
    }

    public String getProfileName(){
        return prefs.getString(IdentityCons.identityArray[1],null);
    }


    public void clearProfileCache(){
       // for(int i=0;i<IdentityCons.identityArray.length;i++){
            SharedPreferences.Editor editor=prefs.edit();
            editor.clear();
            editor.commit();
       // }
    }
}
