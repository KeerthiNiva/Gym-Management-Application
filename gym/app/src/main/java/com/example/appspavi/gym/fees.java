package com.example.appspavi.gym;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class fees extends AppCompatActivity {
    int id,idu;
    final Calendar myCalendar = Calendar.getInstance();
    EditText amt, status, date1;
    Button up;
    String name;
     ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        try {
            Bundle b = getIntent().getExtras();
            id = b.getInt("id");
            idu = b.getInt("idu");
            name=b.getString("name");
           // Toast.makeText(fees.this, ""+id, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            System.out.print(e);
        }
        progressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);
        amt = (EditText) findViewById(R.id.amountt);
        status = (EditText) findViewById(R.id.status);
        date1 = (EditText) findViewById(R.id.date2);
        up = (Button) findViewById(R.id.update1);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                date1.setText(sdf.format(myCalendar.getTime()));


            }

        };

        date1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(fees.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new fetch().execute();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myIntent = new Intent(fees.this,
                Main2Activity.class);
        myIntent.putExtra("id", id);
        startActivity(myIntent);

    }
    public class fetch extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream is = null;
            StringBuilder sb = null;
            String result = null;

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
String ID=Integer.toString(idu);
            nameValuePairs.add(new BasicNameValuePair("action", "fees"));
            nameValuePairs.add(new BasicNameValuePair("id", ID));
            nameValuePairs.add(new BasicNameValuePair("amt", amt.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("status", status.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("date", date1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("name",name));
            Log.d("=====login", "onPostExecute: " + name);

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://gymandroid.000webhostapp.com/gym/leave.php");
                httppost.setEntity(new UrlEncodedFormEntity(
                        nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();


                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "iso-8859-1"), 1);
                sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");
                String line = "0";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();

                System.out.println("=====/////" + result);


            } catch (Exception e) {
                Log.e("log_tag",
                        "Error in http connection" + e.toString());
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {


            Log.d("=====login", "onPostExecute: " + s);
            System.out.println("=====/////post" + s);



            if (s != null) {

                try {
                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);

                       // id = json_data.getInt("id");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("json error");
                }
                progressBar.setVisibility(View.GONE);
                Intent myIntent = new Intent(fees.this,
                        Main2Activity.class);
                myIntent.putExtra("id", id);

                startActivity(myIntent);


            }
        }
    }
        public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}



