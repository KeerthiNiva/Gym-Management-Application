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
import android.widget.Button;
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
import java.util.Date;
import java.util.Locale;

import static com.example.appspavi.gym.R.id.due;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText name, pass;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login);
        name = (EditText) findViewById(R.id.login_name);
        pass = (EditText) findViewById(R.id.pass);

        progressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(isNetworkAvailable()){

                    progressBar.setVisibility(View.VISIBLE);
                    new fetch().execute();
                }
                else {

                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
    @Override
    public void onBackPressed()
    {

        moveTaskToBack(true);

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

            nameValuePairs.add(new BasicNameValuePair("action", "login"));
            nameValuePairs.add(new BasicNameValuePair("name", name.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", pass.getText().toString()));


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


            Log.d("=====login", "onPostExecute: " + s);
            System.out.println("=====/////pavipost" + s);

            String name1="",doj="",due="",type="",lunch="",dinn="",br="",pass1="",uname="";
            int id1 = 0;
            int pay=0;

            double h=0,w=0,bi=0;
            if (s != null) {

                    try {
                        JSONArray jArray = new JSONArray(s);
                        JSONObject json_data;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            name1 = json_data.getString("loginName");
                            pass1 = json_data.getString("password");
                            id1 = json_data.getInt("id");
                          //  id1 = json_data.getInt("id");
                            doj=json_data.getString("doj");
                            h=json_data.getDouble("height");
                            w=json_data.getDouble("weight");
                            bi=json_data.getDouble("bmi");
                            due=json_data.getString("due");
                            type=json_data.getString("type");
                            lunch=json_data.getString("lunch");
                            dinn=json_data.getString("dinner");
                            br=json_data.getString("breakfast");
                            uname=json_data.getString("name");
                            pay=json_data.getInt("payment");


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("json error");
                    }
                    if (s.contains("no data")) {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(MainActivity.this, "username password does not exist", Toast.LENGTH_SHORT).show();
                    } else if (name1.equals(name.getText().toString()) && pass1.equals(pass.getText().toString())) {
                        if(id1==1) {
                            Intent intent_name = new Intent();
                            intent_name.setClass(getApplicationContext(), Main2Activity.class);
                            intent_name.putExtra("id", id1);
                            startActivity(intent_name);
                        }else{
                            //declare the variable sent to the type wise user java files
                            Intent intent_name = new Intent();
                            intent_name.setClass(getApplicationContext(), user.class);
                            intent_name.putExtra("id", id1);
                            intent_name.putExtra("doj",doj);
                            intent_name.putExtra("h",h);
                            intent_name.putExtra("w",w);
                            intent_name.putExtra("due",due);
                            intent_name.putExtra("type",type);
                            intent_name.putExtra("lunch",lunch);
                            intent_name.putExtra("dinner",dinn);
                            intent_name.putExtra("breakfast",br);
                            intent_name.putExtra("name",uname);
                            intent_name.putExtra("bi",bi);
                            intent_name.putExtra("pay",pay);
                            startActivity(intent_name);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "username password incorrect", Toast.LENGTH_SHORT).show();
                    }

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





