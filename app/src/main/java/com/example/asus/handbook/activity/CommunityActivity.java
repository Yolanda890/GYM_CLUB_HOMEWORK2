package com.example.asus.handbook.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.*;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asus.handbook.adapter.CommunityAdapter;
import com.example.asus.handbook.dataobject.Community;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.example.asus.handbook.R;
import com.example.asus.handbook.dataobject.MyUser;


public class CommunityActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private RecyclerView view_community;
    private CommunityAdapter cAdapter;
    private List<String> values1;
    private List<String> values2;
    private List<String> values3;
    private SwipeRefreshLayout refreshLayout;
    private static String currentusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);

        currentusername = getIntent().getStringExtra("username");

        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        mNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.main:
                                Intent intent = new Intent(CommunityActivity.this, Main3Activity.class);
                                intent.putExtra("username",currentusername);
                                startActivity(intent);
                                break;
                            case R.id.community:

                                break;
                            case R.id.find:
                                intent=new Intent(CommunityActivity.this, SearchingActivity.class);
                                intent.putExtra("username",currentusername);
                                startActivity(intent);
                                break;
                            case R.id.me:
                                intent=new Intent(CommunityActivity.this, MeActivity.class);
                                intent.putExtra("username",currentusername);
                                startActivity(intent);
                                break;


                        }
                        return true;
                    }
                });
        mNavigationView.setSelectedItemId(R.id.community);//根据具体情况调用
        //底部导航栏有几项就有几个Fragment

        values1 = new ArrayList<>();
        values2 = new ArrayList<>();
        values3 = new ArrayList<>();

        view_community = findViewById(R.id.view_community);
        view_community.setLayoutManager(new LinearLayoutManager(this));
        view_community.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        view_community.setLayoutManager(manager);
        refresh();

        refreshLayout = findViewById(R.id.refresh_community);
        refreshLayout.setColorSchemeResources(R.color.orange,R.color.blue,R.color.green);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void refresh(){
        values1.clear();
        values2.clear();
        values3.clear();
        BmobQuery<Community> query = new BmobQuery<>();

        Date curdate = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        ft.format(curdate);
        query.addWhereLessThanOrEqualTo("updatedAt",new BmobDate(curdate));
        query.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                if(e == null) {
                    for (int i = list.size()-1; i >= 0 ; i--) {
                        values1.add(list.get(i).getStatecontent());
                        values2.add(list.get(i).getStateimage());
                        values3.add(list.get(i).getUsername());
                    }
                }
                else{
                    System.out.println("error");
                    Toast ts = Toast.makeText(CommunityActivity.this,getResources().getString(getResources().getIdentifier("stringShowCFail", "string", getPackageName())), Toast.LENGTH_LONG);
                    ts.show() ;
                }
            }
        });
        BmobQuery<MyUser> query2 = new BmobQuery<>();
        query2.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                if(e == null){
                    for(int i = 0;i <values3.size();i++){
                        for(int j = 0;j < list.size();j++){
                            if(list.get(j).getUsername().equalsIgnoreCase(values3.get(i))){
                                values3.set(i,list.get(j).getFigureimage());
                                break;
                            }
                        }
                    }
                    view_community.removeAllViews();
                    cAdapter = new CommunityAdapter(values1,values2, values3, R.layout.layout_communitycard, CommunityActivity.this);
                    view_community.setAdapter(cAdapter);
                }
                else{
                    System.out.println("error");
                    Toast ts = Toast.makeText(CommunityActivity.this,getResources().getString(getResources().getIdentifier("stringShowCFail", "string", getPackageName())), Toast.LENGTH_LONG);
                    ts.show() ;
                }
            }
        });
    }



}
