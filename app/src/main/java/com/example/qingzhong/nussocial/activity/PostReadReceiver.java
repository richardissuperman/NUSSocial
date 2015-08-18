package com.example.qingzhong.nussocial.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by qingzhong on 18/8/15.
 */
public class PostReadReceiver extends  BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getAction(),Toast.LENGTH_SHORT).show();
    }
}
