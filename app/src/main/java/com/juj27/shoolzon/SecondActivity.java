package com.juj27.shoolzon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ArrayList<String> items = new ArrayList<>();
    ListView listView;
    ArrayAdapter adapter;

    // api - key
    String apiKey = "jm%2BV99ax5hk0m28XFpy%2Bfc7p0suKAScnNbghyViodg2AJebd8iQEay2W%2B6QXbhrI0EbEspk5JwNLQe%2BjAEFuhw%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ac = getSupportActionBar();
        ac.setDisplayShowTitleEnabled(false);
        ac.setDisplayHomeAsUpEnabled(true);
        TextView tv = findViewById(R.id.tv);
        tv.setText(G.local+"에 대한 결과");

        //리스트 뷰 테스트
//        items.add("aaa");
//        items.add("aaa");
//        items.add("aaa");

        listView = findViewById(R.id.listview);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();

                String address = "http://apis.data.go.kr/B552061/schoolzoneChild/getRestSchoolzoneChild" +
                        "?ServiceKey=" +apiKey+
                        "&searchYearCd="+G.year +
                        "&siDo=11&guGun="+G.localnum +
                        "&numOfRows=10&pageNo=1";
                //Log.i("tag",G.localnum);

                try {
                    URL url = new URL(address);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(isr);
                    int eventType = xpp.getEventType();
                    StringBuffer buffer = null;

                    boolean isData = false;

                    while (eventType != XmlPullParser.END_DOCUMENT){
                        switch (eventType){
                            case XmlPullParser.START_DOCUMENT:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Toast.makeText(SecondActivity.this, "잠시 기다려주세요", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;

                            case  XmlPullParser.START_TAG:
                                String tagName = xpp.getName();
                                if (tagName.equals("item")){
                                    buffer = new StringBuffer();
                                }else if(tagName.equals("spot_nm")){
                                    buffer.append("지점 위치 : ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"\n");
                                    isData = true;
                                }else if(tagName.equals("occrrnc_cnt")){
                                    buffer.append("발생건수 : ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"명"+"\n");
                                }else if(tagName.equals("caslt_cnt")){
                                    buffer.append("사상자 수 : ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"명"+"\n");
                                }else if(tagName.equals("dth_dnv_cnt")){
                                    buffer.append("사망자 수 : ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"명"+"\n");
                                }else if(tagName.equals("se_dnv_cnt")){
                                    buffer.append("중상자 수 : ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"명"+"\n");
                                }else if(tagName.equals("sl_dnv_cnt")){
                                    buffer.append("경상자 수 : ");
                                    xpp.next();
                                    buffer.append(xpp.getText()+"명"+"\n");
                                }
                                break;
                            case XmlPullParser.TEXT:
                                break;
                            case XmlPullParser.END_TAG:
                                String tagName2 = xpp.getName();
                                if(tagName2.equals("item")){
                                    if (isData )items.add(buffer.toString());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                                break;
                        } //switch
                        eventType = xpp.next();
                    } //while
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();  //자동으로 run 메소드발동

        TextView tvEmpty = findViewById(R.id.tv_empty);
        listView.setEmptyView(tvEmpty);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);

    }
}