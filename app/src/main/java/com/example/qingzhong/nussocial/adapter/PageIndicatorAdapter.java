package com.example.qingzhong.nussocial.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.fragment.PostFragment;
import com.example.qingzhong.nussocial.fragment.SettingFragment;
import com.viewpagerindicator.IconPagerAdapter;

/**
 * Created by qingzhong on 1/8/15.
 */
public class PageIndicatorAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

    private  static int[] tabStrings={R.string.tab_playground,R.string.tab_msg,R.string.tab_found,R.string.tab_settings};
    private static int[] tabResouces={R.drawable.ic_tab_playground,R.drawable.ic_tab_msg,R.drawable.ic_tab_found,R.drawable.ic_tab_settings};
    private FragmentManager manager;
    private Context context;
    public PageIndicatorAdapter(FragmentManager manager,Context context){
        super(manager);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            PostFragment fragment=new PostFragment();
            fragment.setContext(context);
            return fragment;
        }


        if(position==3){
            return new SettingFragment();
        }
        return new Fragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       // return super.getPageTitle(position);

        int resourceId=tabStrings[position];
        String tabString=context.getResources().getString(resourceId);
        return tabString;
    }

    @Override
    public int getIconResId(int i) {
        return tabResouces[i];
    }






}
