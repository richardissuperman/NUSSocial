package com.example.qingzhong.nussocial.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qingzhong.nussocial.R;

/**
 * Created by qingzhong on 2/8/15.
 */
public class PlayGroundFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.playgroundlayout,container,false);


        return view;
    }
}
