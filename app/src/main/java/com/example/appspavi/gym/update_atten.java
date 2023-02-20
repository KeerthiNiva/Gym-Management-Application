package com.example.appspavi.gym;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class update_atten extends AppCompatActivity {
    int id,idu;
    final Calendar myCalendar = Calendar.getInstance();
    EditText in, out, date1;
    Button up;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_atten);
        progressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);
        try {
            Bundle b = getIntent().getExtras();
            id = b.getInt("id");
            idu = b.getInt("idu");
            // Toast.makeText(update_atten.this, ""+id, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            System.out.print(e);
        }
        in = (EditText) findViewById(R.id.in);
        out = (EditText) findViewById(R.id.out);
        date1 = (EditText) findViewById(R.id.date);
        up=(Button)findViewById(R.id.update);
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
                new DatePickerDialog(update_atten.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
               new check().execute();

            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent myIntent = new Intent(update_atten.this,
                Main2Activity.class);
        myIntent.putExtra("id",id);

        startActivity(myIntent);

    }
    public class check extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            InputStream is = null;
            StringBuilder sb = null;
            String result = null;

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            // nameValuePairs.add(new BasicNameValuePair("action", "select"));
            String ID=Integer.toString(idu);
            nameValuePairs.add(new BasicNameValuePair("id", ID));
            //nameValuePairs.add(new BasicNameValuePair("name", lname.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("action", "update"));
            nameValuePairs.add(new BasicNameValuePair("in", in.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("out", out.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("date", date1.getText().toString()));
            // nameValuePairs.add(new BasicNameValuePair("login_name", login_name.getText().toString()));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://gymandroid.000webhostapp.com/gym/update.php");
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

            System.out.println("=====/////post" + s);


            int id1 = 0;
            String name1="",doj="",bmi="",due="",type="",lunch="",dinn="",br="";
            if (s != null) {


                try {
                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("json error");
                }



                if (s.contains("update")) {
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(update_atten.this, "updated", Toast.LENGTH_SHORT).show();
                    Intent intent_name = new Intent();
                    intent_name.setClass(getApplicationContext(), Main2Activity.class);
                    intent_name.putExtra("id", id);
                    startActivity(intent_name);
                }



            }

        }

    }

}






