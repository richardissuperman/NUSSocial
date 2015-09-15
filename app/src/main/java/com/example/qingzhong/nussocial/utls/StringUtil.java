package com.example.qingzhong.nussocial.utls;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by qingzhong on 14/9/15.
 */
public class StringUtil {


    public static boolean isNullandEmpty(String passStr){
        if(passStr==null|| passStr.equals("")){
            return true;
        }else{
            return false;
        }
    }

    public static String genRandomPostId(){
        String preflix=System.currentTimeMillis()+"";
        String suffix=(Math.random()*100)+"";
        return (preflix+suffix).replace(".", "");
    }


    public static String getApiKey(String password){
        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String combine=genRandomPostId()+password;
        md.update(combine.getBytes());
        byte[] result=md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return  sb.toString();
    }


    public static String getSHA(String password){


        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String combine=password;
        md.update(combine.getBytes());
        byte[] result=md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return  sb.toString();


    }


//   public static void main(String[] args){
//	   for(int i=0;i<=10;i++){
//	   System.out.println(genRandomPostId());
//	   }
//   }
}