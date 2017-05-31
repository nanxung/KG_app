package com.kg.cb.kg_app;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import arc.citie;

/**
 * Created by cb on 17-5-24.
 */

public class secondary_activity extends Activity
       {
           private TextView left_text;
           private GridView gv;
           private BaseAdapter mAdapter = null;
           private ArrayList<Integer> mData = null;

           @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_main);
        init();
        gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(secondary_activity.this, "pic" + id, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
              gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      Toast.makeText(secondary_activity.this, "pic" + position, Toast.LENGTH_LONG).show();

                  }
              });
    }
    private void init(){
        left_text=(TextView)findViewById(R.id.lt_main_title_left);
        gv = (GridView) findViewById(R.id.GridView1);
        //为GridView设置适配器
        gv.setAdapter(new MyAdapter(this));
        //注册监听事件


        left_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

           class MyAdapter extends BaseAdapter{
               //上下文对象
               private Context context;
               //图片数组
               private Integer[] imgs = {
                       R.drawable.a2, R.drawable.a2, R.drawable.a2,
                       R.drawable.a2,
               };
               MyAdapter(Context context){
                   this.context = context;
               }
               public int getCount() {
                   return imgs.length;
               }

               public Object getItem(int item) {
                   return null;
               }

               public long getItemId(int id) {
                   return id;
               }

               //创建View方法
               public View getView(int position, View convertView, ViewGroup parent) {
                   citie citle;
                   if (convertView == null) {
                       citle = new citie(context);
                       citle.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));//设置ImageView对象布局
                       citle.setAdjustViewBounds(false);//设置边界对齐
                       citle.setScaleType(citie.ScaleType.MATRIX);//设置刻度的类型
                       citle.setPadding(5, 3, 5, 3);//设置间距
                   }
                   else {
                       citle = (citie) convertView;
                   }
                   citle.setImageResource(imgs[position]);//为ImageView设置图片资源
                   return citle;
               }
           }

       }
