package com.example.qingzhong.nussocial.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.datamodel.Post;

import java.util.ArrayList;

/**
 * Created by qingzhong on 20/8/15.
 */
public class PlaygroundRecyclerViewAdapter extends RecyclerView.Adapter<PlaygroundRecyclerViewAdapter.PostViewHolder> {

    public ArrayList<Post> list;

    public PlaygroundRecyclerViewAdapter(ArrayList<Post> list){


        this.list=list;

    }

    public PlaygroundRecyclerViewAdapter(){

    }
    public void setList(ArrayList<Post> list){
        this.list=list;
    }

    @Override
    public PlaygroundRecyclerViewAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playground_single_post, parent, false);
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlaygroundRecyclerViewAdapter.PostViewHolder holder, int position) {

        if(list!=null&&list.size()!=0){

            Post post=list.get(position);
            holder.setNicknameTextView(post.usernickname);
            holder.setDateTextView(post.date);
            if(post.gender=="female"){
                holder.setGenderImageView(R.drawable.ic_filter_girl);
            }
            else {
                holder.setGenderImageView(R.drawable.ic_filter_boy);

            }
            holder.setPostTextView(post.posttext);
            if(post.images!=null){
                holder.setPostImageView(post.images[0]);
            }
            else {
                holder.setPostImageView(R.drawable.ic_not_real_avatar_tip);
            }

            if(post.avatar!=null){
                holder.setProfileImageView(post.avatar);
            }
            else {
                holder.setProfileImageView(R.drawable.defualt_profile);
            }
         //   holder.setPostImageView();

            if(post.likes>0){
                holder.setLikeTextView(post.likes+"");
            }
            else {
                //do nothing
            }

            if(post.comments>0){
                holder.setCommentTextView(post.comments+"");
            }
            else {
                //do nothing
            }
        }

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

        private TextView nicknameTextView;
        private ImageView  profileImageView;
        private TextView dateTextView;
        private ImageView genderImageView;
        private TextView postTextView;
        private ImageView postImageView;
        private TextView likeTextView;
        private TextView commentTextView;
        private View rootView;

        public PostViewHolder(View view){
            super(view);
            rootView=view;

            nicknameTextView=(TextView)rootView.findViewById(R.id.profilenickname);
            dateTextView=(TextView)rootView.findViewById(R.id.posttime);
            genderImageView=(ImageView)rootView.findViewById(R.id.genderimg);
            postTextView=(TextView)rootView.findViewById(R.id.posttext);
            postImageView=(ImageView)rootView.findViewById(R.id.postimg);
            profileImageView=(ImageView)rootView.findViewById(R.id.profileimg);
            likeTextView=(TextView)rootView.findViewById(R.id.post_like_text);
            commentTextView=(TextView)rootView.findViewById(R.id.post_comment_text);



        }

        public void setNicknameTextView(String nickname){
            this.nicknameTextView.setText(nickname);
        }
        public void setDateTextView(String date){
            this.dateTextView.setText(date);
        }
        public void setGenderImageView(int resourceID){
            //this.genderImageView.setImageBitmap(map);
            this.genderImageView.setImageResource(resourceID);
        }
        public void setPostTextView(String text){
            this.postTextView.setText(text);
        }
        public void setPostImageView(Bitmap map){
            this.postImageView.setImageBitmap(map);
        }
        public void setPostImageView(int resrouceId){
            this.postImageView.setImageResource(resrouceId);
        }
        public void setProfileImageView(Bitmap map){
            this.profileImageView.setImageBitmap(map);
        }
        public void setProfileImageView(int reourceId){
            this.profileImageView.setImageResource(reourceId);
        }

        public void setLikeTextView(String s){
            this.likeTextView.setText(s);
        }

        public void setCommentTextView(String s){
            this.commentTextView.setText(s);
        }

    }
}
