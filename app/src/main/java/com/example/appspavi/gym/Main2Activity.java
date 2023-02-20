package com.example.appspavi.gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
int id,idu1; int idu = 0;
Button reg,view,update,fee,feev,diet;
String name="";
EditText id1,id2;
ProgressBar progressBar;
int v=0,u=0,f=0,fv=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        reg=(Button)findViewById(R.id.reg);
        view=(Button)findViewById(R.id.view);
        update=(Button)findViewById(R.id.atten);
        fee=(Button)findViewById(R.id.fee);
        feev=(Button)findViewById(R.id.feev);
        id1=(EditText)findViewById(R.id.id1);
        diet=(Button)findViewById(R.id.table);
progressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);

        try {
            Bundle b=getIntent().getExtras();
            id=b.getInt("id");
            idu1=b.getInt("idu");
             //Toast.makeText(Main2Activity.this, ""+id, Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), register_cus.class);
                intent_name.putExtra("id", id);
                startActivity(intent_name);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                v=1;
                progressBar.setVisibility(View.VISIBLE);
                new check().execute();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                progressBar.setVisibility(View.VISIBLE);
               u=1;
                new check().execute();
            }
        });
        fee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                progressBar.setVisibility(View.VISIBLE);
                f=1;
                new check().execute();
            }
        });
        feev .setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent_name = new Intent();
                intent_name.setClass(getApplicationContext(), viewfee.class);
                intent_name.putExtra("id", id);
                startActivity(intent_name);

            }
        });

        diet .setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent intent_table = new Intent();
                intent_table.setClass(getApplicationContext(), DietChart.class);
               // intent_name.putExtra("id", id);
                startActivity(intent_table);

            }
        });


    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You want to logout");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent myIntent = new Intent(Main2Activity.this,
                                MainActivity.class);


                        startActivity(myIntent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



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
            //nameValuePairs.add(new BasicNameValuePair("name", lname.getText().toString()));
             nameValuePairs.add(new BasicNameValuePair("action", "login"));
             nameValuePairs.add(new BasicNameValuePair("name", id1.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("pass", id1.getText().toString()));
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



            String name1="",doj="",bmi="",due="",type="",lunch="",dinn="",br="",uname="";
            if (s != null) {


                    try {
                        JSONArray jArray = new JSONArray(s);
                        JSONObject json_data;
                        for (int i = 0; i < jArray.length(); i++) {
                            json_data = jArray.getJSONObject(i);
                            name1 = json_data.getString("loginName");
                            idu = json_data.getInt("id");
                            doj=json_data.getString("doj");
                            bmi=json_data.getString("bmi");
                            due=json_data.getString("due");
                            type=json_data.getString("type");
                            lunch=json_data.getString("lunch");
                            dinn=json_data.getString("dinner");
                            br=json_data.getString("breakfast");
                            uname=json_data.getString("name");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("json error");
                    }

Log.d("=====df"," "+uname+idu);

                    if (s.contains("no data")) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Main2Activity.this, "No such user", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                        if(v==1) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent_name = new Intent();
                        intent_name.setClass(getApplicationContext(), view_attn.class);
                        intent_name.putExtra("idu", idu);
                        intent_name.putExtra("id", id);
                        startActivity(intent_name);

                        /**/
                    }
                    if(u==1){
                      progressBar.setVisibility(View.GONE);
                     Intent intent_name = new Intent();
                     intent_name.setClass(getApplicationContext(), update_atten.class);
                     intent_name.putExtra("idu", idu);
                     intent_name.putExtra("id", id);
                     startActivity(intent_name);
                    }if(f==1){
                        progressBar.setVisibility(View.GONE);
                       Intent intent_name = new Intent();
                        intent_name.setClass(getApplicationContext(), fees.class);
                        intent_name.putExtra("idu", idu);
                        intent_name.putExtra("id", id);
                      intent_name.putExtra("name",name1);
                       startActivity(intent_name);
                    }

                   }

                    }
                    }

                }

            }









