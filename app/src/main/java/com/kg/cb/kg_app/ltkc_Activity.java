package com.kg.cb.kg_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import arc.citie;

public class ltkc_Activity extends Activity {
    private TextView left_text;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltkc_);
        init();
    }

    public void one(View v){
        Toast.makeText(this,"基础知识",Toast.LENGTH_LONG).show();
//        Intent in=new Intent(this,secondary_activity.class);
//        startActivity(in);
    }


    public void two(View v){
        Toast.makeText(this,"开采工艺",Toast.LENGTH_LONG).show();
//        Intent in=new Intent(this,secondary_activity.class);
//        startActivity(in);
    }
    public void three(View v){

    }
    public void four(View v){
        Toast.makeText(this,"矿山实例",Toast.LENGTH_LONG).show();

    }
    private void init(){
        left_text=(TextView)findViewById(R.id.lt_main_title_left);


        left_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
