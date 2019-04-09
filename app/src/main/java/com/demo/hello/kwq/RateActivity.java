package com.demo.hello.kwq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    public final String TAG="RateActivity";

    EditText rmb;
    TextView show;
    float dollarRate = 1/6.7f;
    float euroRate = 1/11f;
    float wonRate = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showOut);

    }

    public void onClick(View btn) {
        //获取用户输入内容
        String str = rmb.getText().toString();
        float r = 0;
        float val = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        }else{
            Toast.makeText(this,"请输入金额：", Toast.LENGTH_SHORT).show();
        }

        if (btn.getId() == R.id.btn_dollar) {
            val = r * dollarRate;
        } else if (btn.getId() == R.id.btn_euro) {
            val = r * euroRate;
        } else {
            val = r * wonRate;
        }
        show.setText(String.format("%.2f",val));
//        DecimalFormat df=new DecimalFormat("#.00");
//        show.setText(String.valueOf(df.format(val)));
//        show.setText("" + val);
    }

    private void openConfig() {
        Intent config=new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);

        Log.i(TAG,"openOne:dollarRate="+dollarRate);
        Log.i(TAG,"openOne:euroRate="+euroRate);
        Log.i(TAG,"openOne:wonRate="+wonRate);

        startActivityForResult(config,1);
    }

    public void openOne(View v){
        //打开一个Activity
//        Log.i("open","Open One page:");
//        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.baidu.com/"));
//        Intent call = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:13550585364"));
//        startActivity(call);
//        finish();
        openConfig();
//        startActivity(config);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==2){
//            bdl.putFloat("key_dollar",newDollar);
//            bdl.putFloat("key_euro",newEuro);
//            bdl.putFloat("key_won",newWon);
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("key_dollar",0.1f);
            euroRate=bundle.getFloat("key_euro",0.1f);
            wonRate=bundle.getFloat("key_won",0.1f);
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate);
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
