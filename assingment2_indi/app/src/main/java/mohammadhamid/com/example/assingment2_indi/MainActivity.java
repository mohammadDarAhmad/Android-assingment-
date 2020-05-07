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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ListView ListMelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListMelas = findViewById(R.id.ListMelas);
        String url = "http://192.168.1.231/meals/GetListMeals.php";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1234);
        } else {
            DownloadTextTask runner = new DownloadTextTask();
            runner.execute(url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.addOption)
        {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }
       return true;
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
            Log.d("networking", Objects.requireNonNull(e.getLocalizedMessage()));
            return "";
        }
        return stringResult;
    }

    private class DownloadTextTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... urls) {
            return downloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String str) {

            Gson gson=new Gson();
            //Toast.makeText(getApplicationContext(), "You don't have data in list ", Toast.LENGTH_LONG).show();

            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            ArrayList<String> detailsList=gson.fromJson(str,listType);
            if (detailsList != null)
                printListView(detailsList);
            else {
                Toast.makeText(getApplicationContext(), "You don't have data in list ", Toast.LENGTH_LONG).show();

            }
        }
    }



    private void printListView(ArrayList<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, list);
        ListMelas.setAdapter(arrayAdapter);
/*
        ListMelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,DataActivity.class);
                intent.putExtra("Meals",(position+1));
                startActivity(intent);
            }
        });
*/

    }
    }
    /*
   StudentList = (ListView)findViewById(R.id.listStudent);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, studentsNames);
        StudentList.setAdapter(arrayAdapter);

        StudentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentList.this,StudentDetails.class);

                intent.putExtra("Student",students.get(position));
                startActivity(intent);
            }
        });
    */

