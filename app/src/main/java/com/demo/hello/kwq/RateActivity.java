package com.demo.hello.kwq;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class RateActivity extends AppCompatActivity implements Runnable{
    public final String TAG="RateActivity";

    EditText rmb;
    TextView show;
    Handler handler;
    float dollarRate = 1/6.7f;
    float euroRate = 1/11f;
    float wonRate = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showOut);

        //获取sp里面保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
//        PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate =sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate=sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate=sharedPreferences.getFloat("won_rate",0.0f);
        Log.i(TAG,"sp:dollarRate="+dollarRate);
        Log.i(TAG,"sp:euroRate="+euroRate);
        Log.i(TAG,"sp:wonRate="+wonRate);

        //开启子线程
        Thread t=new Thread(this);
        t.start();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==5){
                    String str=(String)msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg="+str);
                    show.setText(str);
                }else if(msg.what==6){
                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar-rate",0.0f);
                    euroRate=bdl.getFloat("euro-rate",0.0f);
                    wonRate=bdl.getFloat("won-rate",0.0f);
                    Log.i(TAG,"handleMessage:dollarRate="+dollarRate);
                    Log.i(TAG,"handleMessage:euroRate="+euroRate);
                    Log.i(TAG,"handleMessage:wonRate="+wonRate);

                    Toast.makeText(RateActivity.this,"汇率已更新",Toast.LENGTH_SHORT).show();

                }
                super.handleMessage(msg);
            }
        };


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

        Log.i(TAG,"openConfig:dollarRate="+dollarRate);
        Log.i(TAG,"openConfig:euroRate="+euroRate);
        Log.i(TAG,"openConfig:wonRate="+wonRate);

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
            dollarRate=bundle.getFloat("key_dollar",0.0f);
            euroRate=bundle.getFloat("key_euro",0.0f);
            wonRate=bundle.getFloat("key_won",0.0f);
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate);
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);

            //将新汇率写入sp里面
            SharedPreferences sharedPreferences=getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
            editor.commit();
            Log.i(TAG,"onActivityResult:data have been saved in the SharedPreferences");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run().....");
        for(int i =1;i<=6;i++){
            Log.i(TAG,"run:i="+i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //用于保存获取的汇率
        Bundle bundle=new Bundle();

        //获取Msg对象，用于返回主线程
//        Message msg=handler.obtainMessage(5);
//        msg.what=5;
//        msg.obj="hello from run()";
//        handler.sendMessage(msg);

        //获取网络数据
        /*URL url= null;
        try {
            url = new URL("http://www.usd-cny.com/");
            HttpURLConnection http =(HttpURLConnection)url.openConnection();
            InputStream in =http.getInputStream();

            String html=inputStream2String(in);
            Log.i(TAG,"run:html="+html);
            Document doc=Jsoup.parse(html);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/").get();
            Log.i(TAG, "run:"+doc.title());
            Elements tables=doc.getElementsByTag("table");
//            int i=1;
//            for(Element table:tables){
//                Log.i(TAG,"run:table["+i+"]="+table);
//                i++;
//            }
            Element table1=tables.get(0);
//            Log.i(TAG,"run:table[6]="+table1);
            //获取td中的内容
            Elements tds=table1.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=7){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i(TAG,"run:text="+td1.text()+"==>"+td2.text());

                String str1=td1.text();
                String val=td2.text();
                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));
                }else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));
                }else if("韩元".equals(str1)){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));
                }
            }
//            for(Element td:tds){
//                Log.i(TAG,"run:td="+td);
//                Log.i(TAG,"run:text="+td.text());
//                Log.i(TAG,"run:html="+td.html());
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //bundle中保存所获取的汇率


        //获取Msg对象，用于返回主线程
        Message msg=handler.obtainMessage(6);
        msg.obj=bundle;
        handler.sendMessage(msg);

    }

    public String inputStream2String (InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }


}
