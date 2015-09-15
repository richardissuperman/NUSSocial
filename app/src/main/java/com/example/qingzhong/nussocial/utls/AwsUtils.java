package com.example.qingzhong.nussocial.utls;

import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transfermanager.Download;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.example.qingzhong.nussocial.cons.IdentityCons;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by qingzhong on 13/8/15.
 */
public class AwsUtils {


    private  AWSCredentials credentials=new BasicAWSCredentials(IdentityCons.appId,IdentityCons.appSecret);





    public void UploadImages(InputStream stream,String key){

        TransferManager manager=new TransferManager(credentials);
        Upload upload=manager.upload(IdentityCons.bucketName,key,stream,null);


        while(!upload.isDone()){

        }


      //  Download download=manager.download(IdentityCons.bucketName,name,file);


    }

    public File DownloadImages(String name) throws IOException{



        File file=File.createTempFile("profile",".jpg");


        TransferManager manager=new TransferManager(credentials);

        Download download=manager.download(IdentityCons.bucketName,name,file);

        while(!download.isDone()){

          //  Log.e("upload progress",download.getProgress().getPercentTransferred()+" ");

        }

        Log.e("good","img good "+download.isDone());

        Log.e("down load file ",file.getAbsolutePath());


        if(file.getName().endsWith(".jpg")){
            //return BitmapFactory.decodeFile(file.getAbsolutePath());
            return file;
        }

        return null;
    }



    public String generateBitmapName(){
        String s=(System.currentTimeMillis()+Math.random()+100)+".jpg";
        return s;
    }



}
