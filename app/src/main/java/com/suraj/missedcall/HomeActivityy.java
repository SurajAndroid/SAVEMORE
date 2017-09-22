package com.suraj.missedcall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import adapter.GetAllocateNumberAdapter;
import utils.Constant;
import utils.RequestReceiver;
import utils.WebserviceHelper;


public class HomeActivityy extends Activity implements View.OnClickListener, RequestReceiver{
    String username_navigation = "";
    TextView username_nav, cellNumberTxt;
    ImageView drawer_nav;
    DrawerLayout drawer_layout;
    SharedPreferences sharedPreferences;
    Context mContext;
    ListView list_item;
    LinearLayout missedCallLayout, campaningLayout, accountLayout, profile, settingLayout;
    boolean doubleBackToExitPressedOnce = false;
    RequestReceiver receiver;
    SwipeRefreshLayout swipe_refresh_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }

        mContext = this;
        receiver = this;
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setOnClickListener(this);

        list_item=(ListView)findViewById(R.id.list_item);

        swipe_refresh_layout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);

        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
        username_nav = (TextView) findViewById(R.id.username_nav);
        cellNumberTxt = (TextView)findViewById(R.id.cellNumberTxt);
        cellNumberTxt.setText("+91 -"+sharedPreferences.getString("phone",""));
        username_nav.setText("USER NAME " + username_navigation);

        Constant.PHONE_NO = sharedPreferences.getString("phone","");
        drawer_nav = (ImageView) findViewById(R.id.drawer_nav);
        drawer_nav.setOnClickListener(this);



        missedCallLayout = (LinearLayout)findViewById(R.id.missedCallLayout);
        campaningLayout  = (LinearLayout)findViewById(R.id.campaningLayout);
        accountLayout  = (LinearLayout)findViewById(R.id.accountLayout);
        profile  = (LinearLayout)findViewById(R.id.profile);
        settingLayout  = (LinearLayout)findViewById(R.id.settingLayout);

        missedCallLayout.setOnClickListener(this);
        campaningLayout.setOnClickListener(this);
        accountLayout.setOnClickListener(this);
        profile.setOnClickListener(this);
        settingLayout.setOnClickListener(this);

        swipe_refresh_layout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        getAllnumber();

                    }
                }
        );


        getAllnumber();
    }


    public void getAllnumber(){
        WebserviceHelper otp= new WebserviceHelper(receiver,HomeActivityy.this);
        otp.setAction(Constant.GET_ALLOCATE_NUMBER);
        otp.execute();
    }


    //    Back for exit App
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Tap again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllnumber();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
           case R.id.drawer_nav:
                drawer_layout.openDrawer(GravityCompat.START);
                break;

            case R.id.missedCallLayout:

                drawer_layout.closeDrawers();
                getAllnumber();
                /*Intent intent = new Intent(HomeActivityy.this,MissedcallActivity.class);
                startActivity(intent);*/
                break;

            case R.id.campaningLayout:
                Intent intent = new Intent(HomeActivityy.this, CampaignActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawers();
                break;

            case R.id.accountLayout:
                drawer_layout.closeDrawers();
                Intent acount = new Intent(HomeActivityy.this,AccountActivity.class);
                startActivity(acount);
                break;

            case R.id.profile:
                drawer_layout.closeDrawers();
                Intent intent1 = new Intent(HomeActivityy.this,ProfileActivity.class);
                startActivity(intent1);
                break;

            case R.id.settingLayout:
                drawer_layout.closeDrawers();
                Intent setting = new Intent(HomeActivityy.this, SettingActivity.class);
                startActivity(setting);
                break;

        }
    }

    @Override
    public void requestFinished(String[] result) throws Exception {
        if(result[0].equals("1")){
            swipe_refresh_layout.setRefreshing(false);
            GetAllocateNumberAdapter topDealAdapter = new GetAllocateNumberAdapter(HomeActivityy.this,HomeActivityy.this);
            list_item.setAdapter(topDealAdapter);
        }
    }

}
