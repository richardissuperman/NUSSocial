package com.example.qingzhong.nussocial.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.datamodel.Post;

import java.util.ArrayList;

/**
 * Created by qingzhong on 20/8/15.
 */
public class PlaygroundRecyclerViewAdapter extends RecyclerView.Adapter<PlaygroundRecyclerViewAdapter.PostViewHolder> {

    private ArrayList<Post> list;

    public PlaygroundRecyclerViewAdapter(ArrayList<Post> list){


        this.list=list;

    }

    @Override
    public PlaygroundRecyclerViewAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playground_single_post, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaygroundRecyclerViewAdapter.PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        public PostViewHolder(View view){
            super(view);

        }

    }
}
