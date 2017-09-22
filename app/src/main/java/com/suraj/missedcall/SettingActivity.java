package com.suraj.missedcall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import adapter.CallAdapter;
import utils.Constant;
import utils.RequestReceiver;
import utils.WebserviceHelper;

/**
 * Created by SURAJ on 8/22/2017.
 */

public class SettingActivity extends Activity implements RequestReceiver {

    RequestReceiver receiver;
    ListView contactList;
    CallAdapter  callAdapter;
    SharedPreferences sharedPreferences;
    SwipeRefreshLayout swipe_refresh_layout;
    TextView cellNumberTxt;
    ImageView drawer_nav;
    DrawerLayout drawer_layout;
    LinearLayout missedCallLayout, campaningLayout, accountLayout, profile, settingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }
        init();
        clickListenr();
    }

    public void GetregisterService(){
        WebserviceHelper otp= new WebserviceHelper(receiver,SettingActivity.this);
        otp.setAction(Constant.GET_ALLOCATE_NUMBER);
        otp.execute();
    }

    public void init(){
        receiver = this;
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        swipe_refresh_layout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
        Constant.PHONE_NO = sharedPreferences.getString("phone","");
        contactList = (ListView)findViewById(R.id.list_item);


        drawer_nav = (ImageView)findViewById(R.id.drawer_nav);
        cellNumberTxt = (TextView)findViewById(R.id.cellNumberTxt);
        missedCallLayout = (LinearLayout) findViewById(R.id.missedCallLayout);
        campaningLayout = (LinearLayout) findViewById(R.id.campaningLayout);
        accountLayout = (LinearLayout) findViewById(R.id.accountLayout);
        profile = (LinearLayout) findViewById(R.id.profile);
        settingLayout = (LinearLayout) findViewById(R.id.settingLayout);


        GetregisterService();
    }



    public void clickListenr(){


        drawer_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        missedCallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(SettingActivity.this, HomeActivityy.class);
                startActivity(intent);
                finish();

            }
        });

        campaningLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(SettingActivity.this, CampaignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(SettingActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
            }
        });

        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetregisterService();
            }
        });

    }

    @Override
    public void requestFinished(String[] result) throws Exception {
        if(result[0].equals("1")){
            callAdapter = new CallAdapter(SettingActivity.this, SettingActivity.this,receiver);
            contactList.setAdapter(callAdapter);
            swipe_refresh_layout.setRefreshing(false);
        }else if(result[0].equals("101")){
            Toast.makeText(getApplicationContext(),"NickName updated.",Toast.LENGTH_SHORT).show();
            GetregisterService();
        }
    }
}
