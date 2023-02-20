package com.example.appspavi.gym;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

public class register_cus extends AppCompatActivity {

    Button register;
    EditText name,lname,pass,phn,w,h,doj,brk,lu,din,due;
    Spinner type;
    int acctype=0;


ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cus);





        register = (Button) findViewById(R.id.reg);
        name=(EditText)findViewById(R.id.name);
        lname=(EditText)findViewById(R.id.lname);
        pass=(EditText)findViewById(R.id.pwd);
        phn=(EditText)findViewById(R.id.phn);
        w=(EditText)findViewById(R.id.weight);
        h=(EditText)findViewById(R.id.height);
        doj=(EditText)findViewById(R.id.doj);
       // brk=(EditText)findViewById(R.id.brk);
       // lu=(EditText)findViewById(R.id.lunch);
       // din=(EditText)findViewById(R.id.dinner);
        due=(EditText)findViewById(R.id.Course_due);
         type=(Spinner) findViewById(R.id.account_spin);
        progressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);


        //account //string method add from this method
        List<String> list = new ArrayList<String>();
        list.add("-Select Type-");
        list.add("Weight Gain");
        list.add("Weight Loss");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataAdapter);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                acctype=position;
               // Toast.makeText(register_cus.this, "acctype", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(isNetworkAvailable()){
                    if(pass.getText().toString().length()==0||name.getText().toString().length()==0||doj.getText().toString().length()==0||lname.getText().toString().length()==0||phn.getText().toString().length()==0
                            ||w.getText().toString().length()==0||h.getText().toString().length()==0||due.getText().toString().length()==0) {
                        Toast.makeText(register_cus.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    }

                    else if(!(phn.getText().toString().length()==10))
                    {
                        Toast.makeText(register_cus.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        new check().execute();


                    }
                }
                else
                {
                    Toast.makeText(register_cus.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }




            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent myIntent = new Intent(register_cus.this,
                Main2Activity.class);

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

            nameValuePairs.add(new BasicNameValuePair("action", "register"));
            nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("lname", lname.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", pass.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("phn", phn.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("height",h.getText().toString() ));
            nameValuePairs.add(new BasicNameValuePair("weight",w.getText().toString() ));
            nameValuePairs.add(new BasicNameValuePair("doj",doj.getText().toString() ));
            nameValuePairs.add(new BasicNameValuePair("wtype", ""+acctype));
            //nameValuePairs.add(new BasicNameValuePair("break", brk.getText().toString()));
            //nameValuePairs.add(new BasicNameValuePair("lunch",lu.getText().toString()));
            //nameValuePairs.add(new BasicNameValuePair("dinner",din.getText().toString() ));
            nameValuePairs.add(new BasicNameValuePair("due",due.getText().toString()));
           // nameValuePairs.add(new BasicNameValuePair("type",type.getText().toString()));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://gymandroid.000webhostapp.com/gym/register.php");
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

            String name = "", pass = null;
            int id = 0;
            if (s != null) {

                try {
                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        name = json_data.getString("loginName");
                        pass = json_data.getString("password");
                        id = json_data.getInt("id");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("json error");
                }
                Intent myIntent = new Intent(register_cus.this,
                        Main2Activity.class);
                myIntent.putExtra("id", id);

                startActivity(myIntent);


            }
        }
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
            //nameValuePairs.add(new BasicNameValuePair("id", "1"));
            nameValuePairs.add(new BasicNameValuePair("name", lname.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("action", "login"));
            nameValuePairs.add(new BasicNameValuePair("pass", pass.getText().toString()));
           // nameValuePairs.add(new BasicNameValuePair("phn", mobile.getText().toString()));
           // nameValuePairs.add(new BasicNameValuePair("login_name", login_name.getText().toString()));

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("https://gymandroid.000webhostapp.com/gym/login.php");
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
            String name="";
            if (s != null) {

                try {

                    if (s.contains("no data")) {

                        new fetch().execute();
                    }
                    else
                    {
                        Toast.makeText(register_cus.this, "Login name already exists", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("json error");
                }

            }
            Log.d("=====msg", "onPostExecute: "+name+"--"+id1);
        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


