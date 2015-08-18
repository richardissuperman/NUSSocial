package com.example.qingzhong.nussocial.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qingzhong.nussocial.R;

/**
 * Created by qingzhong on 1/8/15.
 */
public class PostFragment extends Fragment {

    private FragmentTabHost tabHost;

    private Context mContext;


    public void setContext(Context context){
        this.mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);


        View view =inflater.inflate(R.layout.postfragmentlayout,container,false);
        tabHost=(FragmentTabHost)view.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(),getChildFragmentManager(),android.R.id.tabcontent);


        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator(getResources().getString(R.string.tab_playground_campus)),
                PlayGroundFragment.class, null);

        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator(getResources().getString(R.string.tab_playground_playground)),
                Fragment.class, null);

        tabHost.getTabWidget().getChildTabViewAt(0).setBackgroundResource(R.drawable.tab_playground_backgroundcolor);
        tabHost.getTabWidget().getChildTabViewAt(1).setBackgroundResource(R.drawable.tab_playground_backgroundcolor);


        return view;

    }
}
