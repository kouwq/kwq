package com.demo.hello.kwq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity implements View.OnClickListener {
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score=findViewById(R.id.score);
        Button btn1=findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        Button btn2=findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        Button btn3=findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        Button res=findViewById(R.id.reset);
        res.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Log.i("main","onClick msg...");
        String str=score.getText().toString();
        int c=Integer.parseInt(str);
        System.out.println("previous scores:"+c);
        switch (v.getId()){
            case R.id.btn1:
                score.setText(""+(c+3));
                break;
            case R.id.btn2:
                score.setText(""+(c+2));
                break;
            case R.id.btn3:
                score.setText(""+(c+1));
                break;
            case R.id.reset:
                score.setText("0");
        }

    }
}
