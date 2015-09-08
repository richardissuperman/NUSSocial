package com.example.qingzhong.nussocial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.activity.SendPostActivity;
import com.example.qingzhong.nussocial.adapter.PlaygroundRecyclerViewAdapter;
import com.example.qingzhong.nussocial.cons.IdentityCons;
import com.example.qingzhong.nussocial.cons.StatusCons;
import com.example.qingzhong.nussocial.datamodel.Post;
import com.example.qingzhong.nussocial.interfaces.VolleyContext;
import com.example.qingzhong.nussocial.utls.ListUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by qingzhong on 2/8/15.
 */
public class PlayGroundFragment extends Fragment implements VolleyContext{

    private ScrollView scrollView;

    private RecyclerView playgroundRecycle ;

    private RequestQueue requestQueue;
    private Request request;
   // JsonRequest
    private ArrayList<Post> postList;
    private PlaygroundRecyclerViewAdapter adapter;
    private FloatingActionButton actionButton;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);

        View view=inflater.inflate(R.layout.playgroundlayout,container,false);
        actionButton=(FloatingActionButton)view.findViewById(R.id.send_post_button);
        playgroundRecycle=(RecyclerView)view.findViewById(R.id.playground_recycler_view);
        playgroundRecycle.setHasFixedSize(true);
        playgroundRecycle.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                ControlPostRead controlPad = (ControlPostRead) getActivity();
                controlPad.readPost();

            }
        });


        initVolleyWidgets();


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        playgroundRecycle.setLayoutManager(linearLayoutManager);

      //  postList=new ArrayList<Post>();
        adapter=new PlaygroundRecyclerViewAdapter(postList);
       // adapter.setList(postList);
        playgroundRecycle.setAdapter(adapter);

        try {

            if(adapter.list==null||adapter.list.size()<=1) {
                startDownloading();
                Toast.makeText(getActivity(),"need to download",Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(getActivity(),"no need to download!!!",Toast.LENGTH_SHORT).show();
            }
        }

        catch(IOException e){
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void initVolleyWidgets() {
        //init the Volley queue
        requestQueue= Volley.newRequestQueue(getActivity());

        //init the request

        Log.e("e","init");
        request=new JsonArrayRequest(new String(IdentityCons.hostName+"post/list"),new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response) {
                Log.e("download","download json successfully");
               // postList= ListUtils.postToArray(response,);
                postList=new ArrayList<Post>();
                new ListUtils(adapter).postToArray(response, postList);
                adapter.list=postList;
                adapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("download",error.toString());

            }
        });


        request.setRetryPolicy(new DefaultRetryPolicy(
                12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);



        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  if(v.getId()==R.id.send_post_button){
                    Intent intent=new Intent(getActivity(), SendPostActivity.class);
                    getActivity().startActivityForResult(intent, StatusCons.SEND_POST);
                    getActivity().overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_popup_exit);
                //}

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void startDownloading() throws IOException {
        requestQueue.add(request);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public interface ControlPostRead{
        public void readPost();
        public void hasnewPost(int number);
    }
}
