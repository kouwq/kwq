package com.demo.hello.kwq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SecActivity extends AppCompatActivity implements View.OnClickListener{
    TextView out;
    EditText edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        out=findViewById(R.id.txtout);
        edit=findViewById(R.id.inp);
        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.i("main","onClick msg...");
        String str=edit.getText().toString();
        DecimalFormat df=new DecimalFormat("#.00");
        float c=Float.parseFloat(str);
        System.out.println(c);
        out.setText("华氏温度为："+df.format(c*9/5+32)+"F");


    }

//    public void click(View v){
//        Log.i("main","onClick msg...");
//        String str=edit.getText().toString();
//        out.setText("hi "+str);
//    }
}
