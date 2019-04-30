package com.demo.hello.kwq;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends AppCompatActivity implements Runnable{
    final String TAG = "RateListActivity";
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        ListView listView=findViewById(R.id.mylist);
        String data[] = {"111","22222"};

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 8) {
                    ListView listView=findViewById(R.id.mylist);
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1, list2);
                    listView.setAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void run() {
        //获取网络数据，放入list带回主线程中
        List<String> retList = new ArrayList<String>();
        Document doc = null;
        try {
            Thread.sleep(3000);
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/index.html").get();
            Log.i(TAG, "run:" + doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table2 = tables.get(1);
            Elements tds = table2.getElementsByTag("td");
            for (int i = 0; i < tds.size(); i += 8) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                Log.i(TAG, "run:text=" + str1 + "==>" + val);
                retList.add(str1 + "==>" + val);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(8);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
