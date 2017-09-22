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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import utils.Constant;
import utils.Global;
import utils.RequestReceiver;
import utils.WebserviceHelper;

/**
 * Created by SURAJ on 8/30/2017.
 */

public class CampaignActivity extends Activity implements RequestReceiver{

    RequestReceiver receiver;
    TextView cellNumberTxt;
    ImageView drawer_nav;
    LinearLayout missedCallLayout, campaningLayout, accountLayout, profile, settingLayout;
    DrawerLayout drawer_layout;
    SharedPreferences sharedPreferences;
    TextView registerNumber, smsCountTxt, sendTxt;
    EditText fromEdit, toEditTxt,messageEdit;
    CheckBox selectAllCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unique_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }

        init();
        clickListener();
    }

    public void init(){

        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
        Constant.PHONE_NO = sharedPreferences.getString("phone","");
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        receiver = this;

        drawer_nav = (ImageView)findViewById(R.id.drawer_nav);
        cellNumberTxt = (TextView)findViewById(R.id.cellNumberTxt);
        missedCallLayout = (LinearLayout) findViewById(R.id.missedCallLayout);
        campaningLayout = (LinearLayout) findViewById(R.id.campaningLayout);
        accountLayout = (LinearLayout) findViewById(R.id.accountLayout);
        profile = (LinearLayout) findViewById(R.id.profile);
        settingLayout = (LinearLayout) findViewById(R.id.settingLayout);

        registerNumber = (TextView)findViewById(R.id.registerNumber);
        smsCountTxt = (TextView)findViewById(R.id.smsCountTxt);
        sendTxt = (TextView)findViewById(R.id.sendTxt);

        fromEdit = (EditText)findViewById(R.id.fromEdit);
        toEditTxt = (EditText)findViewById(R.id.toEditTxt);
        messageEdit = (EditText)findViewById(R.id.messageEdit);
        selectAllCheck = (CheckBox)findViewById(R.id.selectAllCheck);
        cellNumberTxt.setText("+91 - "+sharedPreferences.getString("phone",""));
        CampaignService();
    }


    public void setdata(){
        registerNumber.setText(Global.setting.get(0).getTotalDistinctNumbersCount());
        smsCountTxt.setText(Global.setting.get(0).getRemainingSmsCount()+"/"+Global.setting.get(0).getTotalSmsCount());
    }

    public void CampaignService(){
        WebserviceHelper campaign= new WebserviceHelper(receiver,CampaignActivity.this);
        campaign.setAction(Constant.GET_CAMPAING);
        campaign.execute();
    }


    public void sendSmsService(){
        WebserviceHelper campaign= new WebserviceHelper(receiver,CampaignActivity.this);
        campaign.setAction(Constant.SEND_SMS);
        campaign.execute();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CampaignActivity.this, HomeActivityy.class);
        startActivity(intent);
        finish();
    }


    public void setValidation(){

        if(fromEdit.getText().toString().length()!=0){
            if(toEditTxt.getText().toString().length()!=0){
                if(Integer.parseInt(fromEdit.getText().toString())<Integer.parseInt(toEditTxt.getText().toString())){
                      if((Integer.parseInt(toEditTxt.getText().toString())-Integer.parseInt(fromEdit.getText().toString()))< Integer.parseInt(Global.setting.get(0).getRemainingSmsCount())){
                           if(Integer.parseInt(toEditTxt.getText().toString())>Integer.parseInt(Global.setting.get(0).getTotalDistinctNumbersCount())){
                               Toast.makeText(getApplicationContext(),"Please enter right format",Toast.LENGTH_SHORT).show();
                          }else{

                               if(messageEdit.getText().length()!=0){
                                   if(selectAllCheck.isChecked()){
                                       Constant.START_VALUE = "0";
                                       Constant.END_VALUE = "0";
                                   }else {
                                       Constant.START_VALUE = fromEdit.getText().toString();
                                       Constant.END_VALUE = toEditTxt.getText().toString();
                                   }
                                   Constant.MESSAGE_TXT = messageEdit.getText().toString();
                                   sendSmsService();

                               }else {
                                   Toast.makeText(getApplicationContext(),"Please enter sms",Toast.LENGTH_SHORT).show();
                               }
                          }
                      }else {
                          Toast.makeText(getApplicationContext(),"No enough sms remaining please recharge your account",Toast.LENGTH_SHORT).show();
                      }
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter right format",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Please enter sms from to till",Toast.LENGTH_SHORT).show();
            }
        }else {
            if(selectAllCheck.isChecked()){
                Constant.START_VALUE = "0";
                Constant.END_VALUE = "0";
                Constant.MESSAGE_TXT = messageEdit.getText().toString();
                if(messageEdit.getText().toString().length()!=0){
                    Constant.MESSAGE_TXT = messageEdit.getText().toString();
                    sendSmsService();
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter sms",Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getApplicationContext(),"Please enter sms from to till",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clickListener(){

        sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValidation();
            }
        });

        selectAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fromEdit.setEnabled(false);
                    toEditTxt.setEnabled(false);
                }else {
                    fromEdit.setEnabled(true);
                    toEditTxt.setEnabled(true);
                }

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
                Intent intent = new Intent(CampaignActivity.this, HomeActivityy.class);
                startActivity(intent);
                finish();

            }
        });

        campaningLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
            }
        });

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(CampaignActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(CampaignActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(CampaignActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void requestFinished(String[] result) throws Exception {
        if(result[0].equals("101")){
            setdata();
        }else if(result[0].equals("102")){
            fromEdit.setText("");
            toEditTxt.setText("");
            messageEdit.setText("");
            Toast.makeText(getApplicationContext(),"SMS send successfully.",Toast.LENGTH_SHORT).show();
        }
    }
}
