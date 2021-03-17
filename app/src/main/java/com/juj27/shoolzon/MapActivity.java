package com.juj27.shoolzon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    //구글 지도 객체 참조변수
    GoogleMap gMap;
    TextView tvMap;

    double lon, lat;
    ArrayList<MakerItem> items = new ArrayList<>();

    SupportMapFragment mapFragment;

    String apiKey = "jm%2BV99ax5hk0m28XFpy%2Bfc7p0suKAScnNbghyViodg2AJebd8iQEay2W%2B6QXbhrI0EbEspk5JwNLQe%2BjAEFuhw%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        tvMap = findViewById(R.id.tv_map);
        Toolbar toolbar = findViewById(R.id.toolbar_map);
        setSupportActionBar(toolbar);
        ActionBar ac = getSupportActionBar();
        ac.setDisplayShowTitleEnabled(false);
        ac.setDisplayHomeAsUpEnabled(true);
        tvMap.setText(G.local+"에 대한 결과 지도 보기");

        //xml에 추가했던 Fragment[SupportMapFragment] 참조하기
        FragmentManager fragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();

                String address = "http://apis.data.go.kr/B552061/schoolzoneChild/getRestSchoolzoneChild" +
                        "?ServiceKey=" +apiKey+
                        "&searchYearCd="+G.year +
                        "&siDo=11&guGun="+G.localnum +
                        "&numOfRows=10&pageNo=1";

                try {
                    URL url = new URL(address);
                    InputStream is = url.openStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(isr);
                    int eventType = xpp.getEventType();
                    MakerItem item = null;



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
                                    item = new MakerItem();
                                }else if(tagName.equals("lo_crd")){
                                    xpp.next();
                                    item.lng = Double.parseDouble(xpp.getText());
                                }else if(tagName.equals("la_crd")){
                                    xpp.next();
                                    item.lat = Double.parseDouble(xpp.getText());
                                }else if (tagName.equals("spot_nm")){
                                    xpp.next();
                                    item.spotName=xpp.getText();
                                }
                                break;
                            case XmlPullParser.TEXT:
                                break;
                            case XmlPullParser.END_TAG:
                                String tagName2 = xpp.getName();
                                if(tagName2.equals("item")){
                                    items.add(item);
                                }
                                break;
                        } //switch
                        eventType = xpp.next();
                    } //while

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MapActivity.this, ""+items.size(), Toast.LENGTH_SHORT).show();
                            showMarker();
                        }
                    });

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
    }

    void showMarker(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap=googleMap;

                MakerItem item2 = items.get(0);
                LatLng seoul = new LatLng(item2.lat, item2.lng);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,11));

                for (MakerItem item : items){
                    MarkerOptions marker= new MarkerOptions();
                    marker.title(item.spotName);
                    marker.position(new LatLng(item.lat, item.lng));

                    gMap.addMarker(marker);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}