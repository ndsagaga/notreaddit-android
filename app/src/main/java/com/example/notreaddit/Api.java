package com.example.notreaddit;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Api extends AsyncTask<String, Void, String> {


    public interface AsyncResponse {
        void postExecute(String output);
    }

    private AsyncResponse delegate = null;

    public Api(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params){
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.d("HTTP","URL OPENED "+params[0]);

            //connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("GET");

            Log.d("HTTP","Method set");


            if (params.length > 1) {
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(params[1]);
                Log.d("HTTP","POSTING");
            }

            int statusCode = connection.getResponseCode();

            Log.d("HTTP","Status "+statusCode);
            if (statusCode ==  200 || statusCode == 304) {
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                return convertInputStreamToString(inputStream);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("HTTP","OUTSIDE");
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        if (result == null){
            Log.e("HTTP","Result is NULL");
        }else {
            Log.e("HTTP", "Result is " + result);
            delegate.postExecute(result);
        }
    }

    private String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        String line;
        Log.d("HTTP","START");
        try {
            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                Log.d("HTTP","TEMP "+line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}