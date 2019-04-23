package com.demo.hello.kwq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity implements View.OnClickListener {
    TextView score_a, score_b;
    private final String TAG="Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Log.i(TAG,"onCreate():");
        score_a = findViewById(R.id.scoreA);
        score_b = findViewById(R.id.scoreB);
        Button btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        Button btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        Button btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        Button btn1b = findViewById(R.id.btn_B1);
        btn1b.setOnClickListener(this);
        Button btn2b = findViewById(R.id.btn_B2);
        btn2b.setOnClickListener(this);
        Button btn3b = findViewById(R.id.btn_B3);
        btn3b.setOnClickListener(this);
        Button res = findViewById(R.id.btn_reset);
        res.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = score_a.getText().toString();
        String scoreb = score_b.getText().toString();
        Log.i(TAG,"onSaveInstanceState():");

        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");

        Log.i(TAG,"onRestoreInstanceState():");
        score_a.setText(scorea);
        score_b.setText(scoreb);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart():");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume():");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart():");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause():");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop():");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy():");
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick msg...");
        String str_a = score_a.getText().toString();
        String str_b = score_b.getText().toString();
        int a = Integer.parseInt(str_a);
        int b = Integer.parseInt(str_b);
        System.out.println("previous scores:" + a + ":" + b);
        switch (v.getId()) {
            case R.id.btn_1:
                score_a.setText("" + (a + 3));
                break;
            case R.id.btn_2:
                score_a.setText("" + (a + 2));
                break;
            case R.id.btn_3:
                score_a.setText("" + (a + 1));
                break;
            case R.id.btn_B1:
                score_b.setText("" + (b + 3));
                break;
            case R.id.btn_B2:
                score_b.setText("" + (b + 2));
                break;
            case R.id.btn_B3:
                score_b.setText("" + (b + 1));
                break;
            case R.id.btn_reset:
                score_a.setText("0");
                score_b.setText("0");
        }

    }
}
