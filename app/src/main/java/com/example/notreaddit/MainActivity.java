package com.example.notreaddit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Enumeration;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    EditText searchEditText;
    ImageButton searchButton;
    public static String ip = "http://35.227.16.27/";
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.main_search_btn);
        searchEditText = findViewById(R.id.main_search_text);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButtonOnClick(v);
            }
        });
    }

    private void searchButtonOnClick(View v) {
        query = String.valueOf(searchEditText.getText());
        if (TextUtils.isEmpty(query)){
            searchEditText.setError("Enter a search query!");
            return;
        }

        Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
        intent.putExtra("query",query);
        startActivity(intent);
    }

    private static String getPostParamString(Hashtable<String, Double> params) {
        if(params.size() == 0)
            return "";

        StringBuilder buf = new StringBuilder();
        Enumeration<String> keys = params.keys();
        while(keys.hasMoreElements()) {
            buf.append(buf.length() == 0 ? "" : "&");
            String key = keys.nextElement();
            buf.append(key).append("=").append(String.valueOf(params.get(key)));
        }
        return buf.toString();
    }
}
