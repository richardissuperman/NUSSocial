package com.example.qingzhong.nussocial.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.cons.IdentityCons;
import com.example.qingzhong.nussocial.cons.StatusCons;
import com.example.qingzhong.nussocial.utls.AwsUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by qingzhong on 30/8/15.
 */
public class SendPostActivity extends Activity implements Handler.Callback{


    private ImageView sendPost;
    private EditText sendText;
    private ImageView cancelButton;
    private ImageView submitButton;
    private static final int SEND_POST_CAMERA_CODE=1001;
    private Bitmap sendBitmap;
    private AwsUtils awsUtils=new AwsUtils();
    private Handler handler=new Handler(this);
    private MyPostListener listener=new MyPostListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendpostactivity);

        Toast.makeText(this,"fuck",Toast.LENGTH_SHORT).show();
        initWidgets();
    }


    private void sendPost(){


        final String key =awsUtils.generateBitmapName();



        final String postText=sendText.getText().toString();
        if(postText==null||postText.equals("")){
            Toast.makeText(this,"please say something",Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(sendBitmap!=null) {
                    try {

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        sendBitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                        byte[] bitmapdata = bos.toByteArray();
                        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
                        awsUtils.UploadImages(bs, key);

                     }

                    catch (Exception e){

                        e.printStackTrace();

                        return;
                    }

                    Message msg=new Message();
                    msg.what= StatusCons.FINISH_AWS_UPLOAD;
                    Bundle bundle=new Bundle();
                    bundle.putString("key",key);
                    bundle.putString("text",postText);
                    msg.setData(bundle);

                    handler.sendMessage(msg);
                }
                else {
                    Log.e("WARNING","at Least on pic for demo");
                }
            }
        }).start();

    }

    private void takePic(){

        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, SEND_POST_CAMERA_CODE);


    }


    private void initWidgets(){

        sendPost=(ImageView)findViewById(R.id.take_post_pic_button);
        sendPost.setOnClickListener(this.listener);
        sendText=(EditText)findViewById(R.id.post_edittext);
        cancelButton=(ImageView)findViewById(R.id.cancel_action_button);
        cancelButton.setOnClickListener(this.listener);
        submitButton=(ImageView)findViewById(R.id.submit_action_button);
        submitButton.setOnClickListener(this.listener);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SEND_POST_CAMERA_CODE) {
                if (resultCode == RESULT_OK) {
                   // Uri postImgURI = data.getData();

                    sendBitmap=(Bitmap) data.getExtras().get("data");
                    sendPost.setImageBitmap(sendBitmap);
                    //Log.e("take pic", postImgURI.toString());
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("return!!!!!!",e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(RESULT_CANCELED,intent);
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom);
    }


    @Override
    public boolean handleMessage(Message msg) {

        if(msg.what==StatusCons.FINISH_AWS_UPLOAD){



            final String key_=msg.getData().getString("key");
            final String text_=msg.getData().getString("text");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "\n{\n    \"postid\": \"ddd\",\n    \"userid\": \"richard_johnson@sina.com\",\n    \"time\": \"2015-09-11\",\n    \"text\": \""+text_+"\",\n    \"images\": \""+key_+"\"\n  }\n");
                    Request request = new Request.Builder()
                            .url(IdentityCons.hostName+"post/create")
                            .post(body)
                            .addHeader("content-type", "application/json")
                            .build();

                    try {

                        Response response = client.newCall(request).execute();
                        Message msg=new Message();
                        msg.what=StatusCons.FINISH_AWS_UPLOAD;
                        handler.sendMessage(msg);

                    }

                    catch(IOException e){
                        e.printStackTrace();
                        return;
                    }

                }
            }).start();
        }


        if(msg.what==StatusCons.FINISH_AWS_UPLOAD){
            Intent returnIntent = new Intent();
            setResult(RESULT_OK, returnIntent);
            finish();
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);
        }



        return false;
    }




    class MyPostListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            int id=v.getId();

            if(id==R.id.take_post_pic_button){
                Log.e("Click Listener","take pic func");
                takePic();
            }

            if(id==R.id.cancel_action_button){
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom);

            }

            if(id==R.id.submit_action_button){
                Log.e("Click Listener","send post !");

                sendPost();
            }

        }
    }



}
