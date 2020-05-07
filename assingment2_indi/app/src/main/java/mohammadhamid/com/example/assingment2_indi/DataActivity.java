package mohammadhamid.com.example.assingment2_indi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import mohammadhamid.com.example.assingment2_indi.Models.Meals;

public class DataActivity extends AppCompatActivity {
    private TextView edtNameMeals;
    private TextView edtTypeMealsy;
    private TextView countryMeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        edtNameMeals=findViewById(R.id.edtName);
        edtTypeMealsy=findViewById(R.id.edtCountry);
        countryMeals=findViewById(R.id.edtType);

        Intent intent =getIntent() ;
        int IDMeals= intent.getIntExtra("Meals",0);

        String url="http://192.168.1.231/meals/GetMeals.php?IDMeals="+IDMeals;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1234);
        }else{
            DownloadTextTask runner=new DownloadTextTask();
            runner.execute(url);
        }
    }

    private InputStream openHttpConnection(String urlString) throws IOException {
        InputStream inputStream=null;
        int response=-1;

        URL url=new URL(urlString);
        URLConnection urlConnection=url.openConnection();

        if(!(urlConnection instanceof HttpURLConnection)){
            throw new IOException("Not an HTTP connection");
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            httpURLConnection.setAllowUserInteraction(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            response = httpURLConnection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            }
        }catch (Exception e){
            Log.d("networking",e.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return inputStream;
    }

    private String downloadText(String urlString){
        int BUFFER_SIZE=2000;
        InputStream inputStream=null;
        try{
            inputStream=openHttpConnection(urlString);
        }catch (IOException e){
            Log.d("networking",e.getLocalizedMessage());
            return "";
        }

        InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
        int charRead;
        String stringResult="";
        char[] inputBuffer=new char[BUFFER_SIZE];
        try{
            while((charRead=inputStreamReader.read(inputBuffer))>0){
                String readString=String.valueOf(inputBuffer,0,charRead);
                stringResult+=readString;
                inputBuffer=new char[BUFFER_SIZE];
            }
            inputStream.close();
        }catch (IOException e){
            Log.d("networking",e.getLocalizedMessage());
            return "";
        }
        return stringResult;
    }

    private class DownloadTextTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {
            return downloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String str) {

                Gson gson=new Gson();
                Type type=new TypeToken<Meals>(){}.getType();
                Meals meals=gson.fromJson(str,type);

           edtNameMeals.setText(String.valueOf(meals.getNameMeals()));
            edtTypeMealsy.setText(String.valueOf(meals.getIdMeals()));
            countryMeals.setText(String.valueOf(meals.getCountryMeals()));
        }


    }
}