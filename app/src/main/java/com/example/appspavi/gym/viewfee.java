package com.example.appspavi.gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class viewfee extends AppCompatActivity {
    ListView listView;
    List<Hero1> heroList;
    ProgressBar progressBar;

    int id,idu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfee);
        listView=(ListView)findViewById(R.id.list);
        heroList = new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.simpleProgressBar);
        try {
            Bundle b=getIntent().getExtras();
            id=b.getInt("id");
            idu=b.getInt("idu");
            // Toast.makeText(option1.this, ""+id, Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
        progressBar.setVisibility(View.VISIBLE);
        new view().execute();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(id==1) {
            Intent myIntent = new Intent(viewfee.this,
                    Main2Activity.class);
            myIntent.putExtra("id", id);

            startActivity(myIntent);
        }


    }
    public class view extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String ID="";
            InputStream is = null;
            StringBuilder sb = null;
            String result = null;

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            if(id==1){
            nameValuePairs.add(new BasicNameValuePair("action", ""));
              ID=Integer.toString(id);
            }else
            { ID=Integer.toString(idu);
                nameValuePairs.add(new BasicNameValuePair("action", "one"));
            }
            nameValuePairs.add(new BasicNameValuePair("id", ID));
            nameValuePairs.add(new BasicNameValuePair("amt", ""));
            nameValuePairs.add(new BasicNameValuePair("status", ""));
            nameValuePairs.add(new BasicNameValuePair("date",""));
            nameValuePairs.add(new BasicNameValuePair("name",""));

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

            System.out.println("=====/////fee" + s);


            int id1 = 0;
            String name="",date="",amt="";
            if(s.contains("no data"))
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(viewfee.this, "No Data available", Toast.LENGTH_SHORT).show();
            }
            else if (s != null) {


                try {
                    JSONArray jArray = new JSONArray(s);
                    JSONObject json_data;
                    for (int i = 0; i < jArray.length(); i++) {
                        json_data = jArray.getJSONObject(i);
                        id1 = json_data.getInt("id");
                        amt = json_data.getString("amount");
                        date = json_data.getString("date");
                        name = json_data.getString("name");
                        Hero1 hero = new Hero1(date,name,amt);
                        heroList.add(hero);

                    }
                    Log.d("=====login", "UUUUonPostExecute: " + heroList);
                    progressBar.setVisibility(View.GONE);
                    ListViewAdapter1 adapter = new ListViewAdapter1(heroList, getApplicationContext());
                    listView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("json error");
                }


            }

        }

    }
}