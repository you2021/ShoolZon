package com.juj27.shoolzon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_year, spinner_local;
    ArrayAdapter adapter_year, adapter_local;

    String year, local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ac = getSupportActionBar();
        ac.setDisplayShowTitleEnabled(false);


        spinner_year = findViewById(R.id.sn_year);
        adapter_year = ArrayAdapter.createFromResource(this, R.array.year, R.layout.spinner_selected);
        spinner_year.setAdapter(adapter_year);

        adapter_year.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                G.year = spinner_year.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinner_local = findViewById(R.id.sn_local);
        adapter_local = ArrayAdapter.createFromResource(this, R.array.local, R.layout.spinner_selected);
        spinner_local.setAdapter(adapter_local);

        adapter_local.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner_local.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                G.local = spinner_local.getSelectedItem().toString();
                number();
                adapter_local.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    void number(){
        if(G.local.equals("강남구")){
            G.localnum="680";
        }else if(G.local.equals("강동구")){
            G.localnum="740";
        }else if(G.local.equals("강북구")){
            G.localnum="305";
        }else if(G.local.equals("강서구")){
            G.localnum="500";
        }else if(G.local.equals("관악구")){
            G.localnum="620";
        }else if(G.local.equals("광진구")){
            G.localnum="215";
        }else if(G.local.equals("구로구")){
            G.localnum="530";
        }else if(G.local.equals("금천구")){
            G.localnum="545";
        }else if(G.local.equals("노원구")){
            G.localnum="350";
        }else if(G.local.equals("도봉구")){
            G.localnum="320";
        }else if(G.local.equals("동대문구")){
            G.localnum="230";
        }else if(G.local.equals("동작구")){
            G.localnum="590";
        }else if(G.local.equals("마포구")){
            G.localnum="440";
        }else if(G.local.equals("서대문구")){
            G.localnum="440";
        }else if(G.local.equals("서초구")){
            G.localnum="650";
        }else if(G.local.equals("성동구")){
            G.localnum="200";
        }else if(G.local.equals("성북구")){
            G.localnum="290";
        }else if(G.local.equals("송파구")){
            G.localnum="710";
        }else if(G.local.equals("양천구")){
            G.localnum="470";
        }else if(G.local.equals("영등포구")){
            G.localnum="560";
        }else if(G.local.equals("용산구")){
            G.localnum="170";
        }else if(G.local.equals("은평구")){
            G.localnum="380";
        }else if(G.local.equals("종로구")){
            G.localnum="110";
        }else if(G.local.equals("중구")){
            G.localnum="140";
        }else if(G.local.equals("중랑구")){
            G.localnum="260";
        }
    }
    public void clickBtn(View view) {
        if ( G.year.equals("선택") || G.local.equals("선택")){
            Toast.makeText(this, "선택을 해주세요", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, SecondActivity.class);
            this.startActivity(intent);
        }
        
    }
}