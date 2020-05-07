package mohammadhamid.com.example.assingment2_indi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
public class AddActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtType;
    private EditText edtCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtName = findViewById(R.id.edtName);
        edtType = findViewById(R.id.edtType);
        edtCountry = findViewById(R.id.edtCountry);

    }

    public void btnAddOnClick(View view) {
        String restURL = "http://192.168.1.231/meals/add.php";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1234);
        } else {
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restURL);
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


    private String processRequest(String restURL) throws UnsupportedEncodingException {
        String NameMeals = edtName.getText().toString();
        String TypeMeals = edtType.getText().toString();
        String countryMeals= edtCountry.getText().toString();


        String data = URLEncoder.encode("NameMeals", "UTF-8")
                + "=" + URLEncoder.encode(NameMeals, "UTF-8");
        data += "&" + URLEncoder.encode("TypeMeals", "UTF-8")
                + "=" + URLEncoder.encode(TypeMeals, "UTF-8");
        data += "&" + URLEncoder.encode("countryMeals", "UTF-8")
                + "=" + URLEncoder.encode(countryMeals, "UTF-8");


        String text = "";
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(restURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            text = stringBuilder.toString();
        } catch (Exception e) {
            Log.d("Exception", e.getLocalizedMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        return text;
    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                Log.d("Exception", e.getLocalizedMessage());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(AddActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }
}