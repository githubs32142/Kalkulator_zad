package com.example.android.kalkulator;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText n1EdT,n2EdT,resEdT;
    Button sub,mul,div,add,pi;
    LogicService logicService;
    boolean mBound=false;
    ProgressBar progressBar;
    private ServiceConnection logicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogicService.LocalBinder binder = (LogicService.LocalBinder)service;
            logicService= binder.getService();
            mBound= true;
            Toast.makeText(MainActivity.this,"Logic Service Connected!",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            logicService=null;
            mBound= false;
            Toast.makeText(MainActivity.this,"Logic Service Disconnected",Toast.LENGTH_SHORT).show();
        }
    };
    private AlertDialog.Builder builder;

    @Override
    protected void onStart() {
        super.onStart();
        if(!mBound){
            this.bindService(new Intent(MainActivity.this,LogicService.class),logicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            mBound= false;
            this.unbindService(logicConnection);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        n1EdT = (EditText)findViewById(R.id.n1EditText);
        n2EdT=(EditText) findViewById(R.id.n2EditText);
        resEdT= (EditText) findViewById(R.id.resEditText);
        sub= (Button)findViewById(R.id.subbutton);
        mul=(Button)findViewById(R.id.mulbutton);
        div= (Button) findViewById(R.id.divbutton);
        add= (Button) findViewById(R.id.addbutton);
        pi =(Button) findViewById(R.id.piButton);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1=0.0;
                Double n2=0.0;
                try {
                    n1= Double.parseDouble(n1EdT.getText().toString());
                    n2= Double.parseDouble(n2EdT.getText().toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if (mBound){
                    double ret=logicService.add(n1,n2) ;
                    resEdT.setText(String.valueOf(ret));
                }
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1=0.0;
                Double n2=0.0;
                try {
                    n1= Double.parseDouble(n1EdT.getText().toString());
                    n2= Double.parseDouble(n2EdT.getText().toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if (mBound){
                   double ret=logicService.sub(n1,n2) ;
                    resEdT.setText(String.valueOf(ret));
                }
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1=0.0;
                Double n2=0.0;
                try {
                    n1= Double.parseDouble(n1EdT.getText().toString());
                    n2= Double.parseDouble(n2EdT.getText().toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if (mBound){
                    double ret= logicService.mul(n1,n2) ;
                    resEdT.setText(String.valueOf(ret));
                }
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double n1=0.0;
                Double n2=0.0;
                try {
                    n1= Double.parseDouble(n1EdT.getText().toString());
                    n2= Double.parseDouble(n2EdT.getText().toString());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if (mBound){
                    if(n1!=0.0){
                        double ret= logicService.div(n1,n2) ;
                        resEdT.setText(String.valueOf(ret));
                    }

                }
            }
        });
        pi.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      new AsynTask().execute(Integer.parseInt(n1EdT.getText().toString()));
                                  }
                              }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        return true;
    }
    private class AsynTask extends AsyncTask<Integer,Void,Double>{

        @Override
        protected Double doInBackground(Integer... doubles) {
            Random generator = new Random();
            int nk = 0;
            double x,y;
            double s;
            for(int i = 1; i <= doubles[0]; i++)
            {
                x = generator.nextDouble();
                y = generator.nextDouble();
                if(x*x + y*y <= 1)
                {
                    nk++;
                }
            }
            s = 4. * nk / doubles[0];
        return s;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this,"Start execute Pi",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            Toast.makeText(MainActivity.this,"End execute Pi",Toast.LENGTH_SHORT).show();
            resEdT.setText(String.valueOf(aDouble));
            progressBar.setVisibility(View.GONE);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.makeClear:{
                n1EdT.setText("0.0");
                n2EdT.setText("0.0");
                resEdT.setText("0.0");
                Toast.makeText(MainActivity.this,"Make cler data",Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.about:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View v= getLayoutInflater().inflate(R.layout.author_layout,null);
                builder.setView(v);
                final AlertDialog dialog = builder.create();
                final  Button btn = (Button)v.findViewById(R.id.okBtn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                dialog.show();
            }
            default:
                return false;
        }

    }
}
