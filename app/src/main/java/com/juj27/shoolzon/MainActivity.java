package com.juj27.shoolzon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_year, spinner_local;
    ArrayAdapter adapter_year, adapter_local;

    String year, local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }


    }



    public void clickBtn(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        this.startActivity(intent);
    }
}