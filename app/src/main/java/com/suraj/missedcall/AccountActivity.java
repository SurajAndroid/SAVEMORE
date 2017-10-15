package com.suraj.missedcall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.Constant;
import utils.Global;
import utils.RequestReceiver;
import utils.WebserviceHelper;

/**
 * Created by SURAJ on 8/30/2017.
 */

public class AccountActivity extends Activity implements RequestReceiver{

    RequestReceiver receiver;
    TextView cellNumberTxt;
    ImageView drawer_nav;
    DrawerLayout drawer_layout;
    LinearLayout missedCallLayout, campaningLayout, accountLayout, profile, settingLayout;
    SharedPreferences sharedPreferences;
    TextView smsTxt, dateTxt;
    TextView rechargeTxt, renewTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }

        init();
        clickListener();
    }

    public void init(){
        receiver = this;
        smsTxt = (TextView)findViewById(R.id.smsTxt);
        dateTxt = (TextView)findViewById(R.id.dateTxt);

        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
        Constant.PHONE_NO = sharedPreferences.getString("phone","");
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_nav = (ImageView)findViewById(R.id.drawer_nav);
        cellNumberTxt = (TextView)findViewById(R.id.cellNumberTxt);
        missedCallLayout = (LinearLayout) findViewById(R.id.missedCallLayout);
        campaningLayout = (LinearLayout) findViewById(R.id.campaningLayout);
        accountLayout = (LinearLayout) findViewById(R.id.accountLayout);
        profile = (LinearLayout) findViewById(R.id.profile);
        settingLayout = (LinearLayout) findViewById(R.id.settingLayout);
        cellNumberTxt.setText("+91 "+sharedPreferences.getString("phone",""));

        rechargeTxt = (TextView)findViewById(R.id.rechargeTxt);
        renewTxt  = (TextView)findViewById(R.id.renewTxt);

        AccoutService();
    }

    public void setdata(){
        dateTxt.setText(Global.setting.get(0).getExpiryDate());
        smsTxt.setText(Global.setting.get(0).getRemainingSmsCount()+"/"+Global.setting.get(0).getTotalSmsCount());
    }

    public void AccoutService(){
        WebserviceHelper account= new WebserviceHelper(receiver,AccountActivity.this);
        account.setAction(Constant.GET_CAMPAING);
        account.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AccountActivity.this, HomeActivityy.class);
        startActivity(intent);
        finish();
    }

    public void clickListener(){
        rechargeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://rec.rajhans.digital/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        renewTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://ren.rajhans.digital/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

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
                Intent intent = new Intent(AccountActivity.this, HomeActivityy.class);
                startActivity(intent);
                finish();

            }
        });

        campaningLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(AccountActivity.this, CampaignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawer_layout.closeDrawers();
                        Intent intent = new Intent(AccountActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(AccountActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void requestFinished(String[] result) throws Exception {

        if(result[0].equals("101")){
            setdata();
        }
    }
}
