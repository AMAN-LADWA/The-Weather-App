package com.viber.whatstheweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView editText ;
    TextView textView;
    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url ;
            HttpURLConnection connection = null;
            try{
                url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream in = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data!=-1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                Log.i("Got JSOn",result);
                return result;
            }catch (Exception e){
                Log.i("ERR","Error in DOinBG");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo = jsonObject.getString("weather");
                Log.i("Weather content",weatherInfo);
                JSONArray arr = new JSONArray(weatherInfo);
                String restex="";
                for (int i=0;i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    restex =jsonPart.getString("main")+" "+jsonPart.getString("description");
                    if(!restex.equals(""))
                    textView.setText(restex);

                }
            }catch (Exception e){
                Log.i("ERR","Error in poSTEXEC");
            }
        }
    }
    public void getWeather(View view){
        try {
            DownloadTask task = new DownloadTask();
            task.execute("http://api.openweathermap.org/data/2.5/weather?q="+ editText.getText().toString()+"&appid=ac4db2d502a862f7d3672318f9900547");
            Log.i("Done ","here");

        }catch (Exception e){
            Log.i("Error ","in getWeather");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (TextView) findViewById(R.id.editTextTextPersonName);
        textView = (TextView) findViewById(R.id.textView2);
    }


}