package com.example.qingzhong.nussocial.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.cons.IdentityCons;
import com.example.qingzhong.nussocial.interfaces.VolleyContext;
import com.example.qingzhong.nussocial.utls.PreferenceUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity  implements Handler.Callback,VolleyContext{

    private Handler handler;
    private Request auth_request;
    private Request login_request;
    private RequestQueue queue;
    private boolean auth=false;
    ProgressDialog mDialog;
    private EditText usernameText;
    private EditText passwordText;
    private TextView authText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidgets();
        initVolleyWidgets();

        new Thread(new Runnable() {
            @Override
            public void run() {

                checkAuth();

            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean handleMessage(Message msg) {


        switch (msg.what){
            case 101:
                //next Activity
                mDialog.dismiss();
                Toast.makeText(this,"auth good!",Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
                break;
            case 102:
                //apikey invalid indicate perform login
                mDialog.dismiss();
                Toast.makeText(this,"auth failed!",Toast.LENGTH_LONG).show();
                authText.setText("auth falied, please login");

                //should do login
                break;
            case 103:
                //network error
                mDialog.dismiss();
                Toast.makeText(this,"network error!",Toast.LENGTH_LONG).show();
                authText.setText("network error, please check your internet connection");
                break;

            case 104:

                mDialog.dismiss();
                Toast.makeText(this,"login good!!",Toast.LENGTH_LONG).show();
                authText.setText("login success!");
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case 105:
                mDialog.dismiss();
                Toast.makeText(this,"login failed,!!",Toast.LENGTH_LONG).show();
                authText.setText("username or password invalid");
                break;

        }

        return true;
    }


    @Override
    public void startDownloading() throws IOException {

    }

    private void checkAuth(){
        queue.add(auth_request);
    }

    public void Login(View v){
        if(usernameText.getText().toString()==null||usernameText.getText().toString().equals("")){
            authText.setText("please input username");
            return;
        }
        if(passwordText.getText().toString()==null||passwordText.getText().toString().equals("")){
            authText.setText("please input password");
            return;
        }
        PreferenceUtil util=new PreferenceUtil(LoginActivity.this);
        //edit text content
        String userid=usernameText.getText().toString();
        util.setUserid(userid);
        mDialog.show();
        queue.add(login_request);
    }


    private void initWidgets(){

        handler=new Handler(this);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("authenticating...");
        mDialog.setCancelable(false);
        mDialog.show();

        usernameText=(EditText)findViewById(R.id.login_username);
        passwordText=(EditText)findViewById(R.id.login_password);
        authText=(TextView)findViewById(R.id.login_auth_text);
    }
    @Override
    public void initVolleyWidgets() {

        //request queue
        queue= Volley.newRequestQueue(this);


        //authentication request
        auth_request=new StringRequest(Request.Method.POST,new String(IdentityCons.hostName+"user/authenticate"),new Response.Listener<String>(){

            @Override
            public void onResponse(String callback) {

                //do something
                if(!callback.equals("failed")){
                    Message msg=new Message();
                    msg.what=101;
                    handler.sendMessage(msg);
                    return;
                }


                Message msg=new Message();
                msg.what=102;
                handler.sendMessage(msg);
                return;

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg=new Message();
                msg.what=103;
                handler.sendMessage(msg);

                //do something
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                PreferenceUtil util=new PreferenceUtil(LoginActivity.this);

                String userid=util.getUserId();
                String apikey=util.getApiKey();

                if(userid==null||apikey==null){
                    userid="test";
                    apikey="test";
                }



                //return super.getParams();
                HashMap<String,String> headers=new HashMap<>();
                headers.put("username",userid);
                headers.put("apikey",apikey);

                return headers;            }
        };

        //login request

        login_request=new StringRequest(Request.Method.POST,new String(IdentityCons.hostName+"user/login"),new Response.Listener<String>(){

            @Override
            public void onResponse(String callback) {

                //do something
                if(!callback.equals("failed")){
                    PreferenceUtil util=new PreferenceUtil(LoginActivity.this);
                    util.setApiKey(callback);
                    Message msg=new Message();
                    msg.what=104;
                    handler.sendMessage(msg);
                    return;
                }

                Message msg=new Message();
                msg.what=105;
                handler.sendMessage(msg);
                return;
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

                //do something
                Message msg=new Message();
                msg.what=103;
                handler.sendMessage(msg);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                PreferenceUtil util=new PreferenceUtil(LoginActivity.this);
                //edit text content
                String userid=usernameText.getText().toString();
                util.setUserid(userid);
                //edit text content
                //String hashcode= StringUtil.getSHA(passwordText.getText().toString());
                String pass=passwordText.getText().toString();
                //return super.getParams();
                HashMap<String,String> headers=new HashMap<>();
                headers.put("username",userid);
                headers.put("password",pass);

                return headers;            }
        };


        auth_request.setShouldCache(false);
        login_request.setShouldCache(false);
        auth_request.setRetryPolicy(new DefaultRetryPolicy(
                4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        login_request.setRetryPolicy(new DefaultRetryPolicy(
                4000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }



}
