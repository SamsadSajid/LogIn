package com.example.dolphin.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by DOLPHIN on 12/29/2016.
 */

public class BackGroundTask extends AsyncTask <String,Void,String> {

    String registration_url="http://172.18.18.25/Log In/register.php";
    Context context;
    Activity activity;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    public BackGroundTask(Context context){
        this.context = context;
        activity = (Activity)context;
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        if(method.equals("registration")){
            try{
                URL url=new URL(registration_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream =httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String name= params[1];
                String email = params[2];
                String pass = params[3];
                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");
                    Thread.sleep(5000);
                }
                httpURLConnection.disconnect();
                return  stringBuilder.toString().trim();

            }catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);
        progressDialog =new ProgressDialog(context);
        progressDialog.setTitle("Thinking...");
        progressDialog.setMessage("Connecting to Server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String json) {
        try {
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            String code = jsonObject1.getString("code");
            String message = jsonObject1.getString("message");

            if(code.equals("reg_true")){
                showDialogue("Registration Successfull...",message,code);
            }

            else if(code.equals("reg_false")){
                showDialogue("Registration Failed...",message,code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showDialogue(String title,String message, String code){
        builder.setTitle(title);

        if((code.equals("reg_true"))||(code.equals("reg_false"))){
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            }
            );

            AlertDialog alertDialog =builder.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
