package com.example.qingzhong.nussocial.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.qingzhong.nussocial.R;

/**
 * Created by qingzhong on 2/8/15.
 */
public class PlayGroundFragment extends Fragment {

    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.playgroundlayout,container,false);




        scrollView=(ScrollView)view.findViewById(R.id.playground_scrolview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {


                ControlPostRead controlPad=(ControlPostRead)getActivity();
                controlPad.readPost();



            }
        });


        return view;
    }


    public interface ControlPostRead{
        public void readPost();
        public void hasnewPost(int number);
    }
}
