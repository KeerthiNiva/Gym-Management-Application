package com.example.appspavi.gym;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class user extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
        String[] country = { "Options","Fees", "Attendence"};
        int id;

        String doj,due,type,name;
        double bi;
        double h;
        double w;
        Integer z;

        TextView DOJ,DUE,TYPE,LU,DI,BR,H,W,NAME,BMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        try {
            Bundle b=getIntent().getExtras();
            id=b.getInt("id");
            doj=b.getString("doj");
            h=b.getDouble("h");
            w=b.getDouble("w");
            due=b.getString("due");
            type=b.getString("type");
            bi=b.getDouble("bi");
           // lunch=b.getString("lunch");
           // dinner=b.getString("dinner");
            //brk=b.getString("breakfast");
            name=b.getString("name");
            z=b.getInt("pay");

        }
        catch (Exception e)
        {
            System.out.print(e);
        }
       // Toast.makeText(user.this, "No Internet Connection"+id, Toast.LENGTH_SHORT).show();
        DOJ=(TextView)findViewById(R.id.doj);
        H=(TextView)findViewById(R.id.height);
        W=(TextView)findViewById(R.id.weight);
        BMI=(TextView)findViewById(R.id.bmi);
        DUE=(TextView)findViewById(R.id.due);
        TYPE=(TextView)findViewById(R.id.type);
       // LU=(TextView)findViewById(R.id.lunch);
       // DI=(TextView)findViewById(R.id.dinner);
        NAME=(TextView)findViewById(R.id.uname);
        //BR=(TextView)findViewById(R.id.breakfst);
        DOJ.setText(doj);
         H.setText(""+h);
         W.setText(""+w);
        BMI.setText(""+bi);


        if(z==0) {


            android.support.v7.app.AlertDialog alert = new android.support.v7.app.AlertDialog.Builder(user.this).create();
            alert.setTitle("Your Are Not Pay Fees");
            alert.setMessage("Contact Your Admin");
            alert.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }


            );
            alert.show();


        }


        //h=h*h;
        //bmi=w/h;
        //DecimalFormat f = new DecimalFormat("##.00");
       // System.out.println(f.format(bmi));
        Log.d("=====login", "UUUUonPostExecute: " );

        DUE.setText(due);
        TYPE.setText(type);
        //LU.setText(lunch);
        //DI.setText(dinner);
        //BR.setText(brk);
        NAME.setText("Welcome"+" "+name+" !!!");
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

    }
    @Override
    public void onBackPressed()
    {
       // super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You want to logout");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent myIntent = new Intent(user.this,
                                        MainActivity.class);
                                myIntent.putExtra("id",id);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getApplicationContext(),country[i] , Toast.LENGTH_LONG).show();
        if(country[i].equals("Fees"))
        {
            Intent intent_name = new Intent();
            intent_name.setClass(getApplicationContext(), viewfee.class);
            intent_name.putExtra("idu", id);
            startActivity(intent_name);
        }
        if(country[i].equals("Attendence"))
        {
            Intent intent_name = new Intent();
            intent_name.setClass(getApplicationContext(), view_attn.class);
            intent_name.putExtra("idu", id);
            startActivity(intent_name);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
