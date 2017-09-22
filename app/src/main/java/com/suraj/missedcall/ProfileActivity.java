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
 * Created by SURAJ on 7/30/2017.
 */

public class ProfileActivity extends Activity implements RequestReceiver{

    RequestReceiver receiver;
    LinearLayout EditLayout;
    LinearLayout menuLayout;
    EditText AddresseditText, cityEditTxt, ThanaEditTxt, DistEditTxt, PinEditTxt, MobilOneTxt, MobileTwoeditText;
    TextView userNameTxt,editTxt;

    ImageView drawer_nav;
    LinearLayout missedCallLayout, campaningLayout, accountLayout, profile, settingLayout;
    DrawerLayout drawer_layout;
    SharedPreferences sharedPreferences;
    TextView cellNumberTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }

        init();
        clickListener();
    }

    public void GetregisterService(){
        WebserviceHelper get_profile= new WebserviceHelper(receiver,ProfileActivity.this);
        get_profile.setAction(Constant.GET_PROFILE);
        get_profile.execute();
    }

    public void updateProfileService(){
        WebserviceHelper upate_profile= new WebserviceHelper(receiver,ProfileActivity.this);
        upate_profile.setAction(Constant.UPDATE_PROFILE);
        upate_profile.execute();
    }

    public void init(){
        receiver = this;


        editTxt = (TextView)findViewById(R.id.editTxt);
        drawer_nav = (ImageView)findViewById(R.id.drawer_nav);
        cellNumberTxt = (TextView)findViewById(R.id.cellNumberTxt);
        missedCallLayout = (LinearLayout) findViewById(R.id.missedCallLayout);
        campaningLayout = (LinearLayout) findViewById(R.id.campaningLayout);
        accountLayout = (LinearLayout) findViewById(R.id.accountLayout);
        profile = (LinearLayout) findViewById(R.id.profile);
        settingLayout = (LinearLayout) findViewById(R.id.settingLayout);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
        EditLayout = (LinearLayout)findViewById(R.id.EditLayout);

        AddresseditText = (EditText)findViewById(R.id.AddresseditText);
        cityEditTxt  = (EditText)findViewById(R.id.cityEditTxt);
        ThanaEditTxt  = (EditText)findViewById(R.id.ThanaEditTxt);
        DistEditTxt  = (EditText)findViewById(R.id.DistEditTxt);
        PinEditTxt  = (EditText)findViewById(R.id.PinEditTxt);
        MobilOneTxt  = (EditText)findViewById(R.id.MobilOneTxt);
        MobileTwoeditText  = (EditText)findViewById(R.id.MobileTwoeditText);
        userNameTxt = (TextView)findViewById(R.id.userNameTxt);

        MobilOneTxt.setText("+91- "+sharedPreferences.getString("phone",""));

        cellNumberTxt.setText("+91 "+sharedPreferences.getString("phone",""));
        Constant.PHONE_NO = sharedPreferences.getString("phone","");
        GetregisterService();
    }

    public void clickListener(){




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
                Intent intent = new Intent(ProfileActivity.this, HomeActivityy.class);
                startActivity(intent);
                finish();

            }
        });

        campaningLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(ProfileActivity.this, CampaignActivity.class);
                startActivity(intent);
                finish();
            }
        });

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(ProfileActivity.this, AccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();

            }
        });

        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.closeDrawers();
                Intent intent = new Intent(ProfileActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });


        EditLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MobileTwoeditText.setEnabled(true);
                if(editTxt.getText().toString().equalsIgnoreCase("EDIT")){
                    editTxt.setText("UPDATE");
                }else {
                    Constant.OTHER_NUMBER = MobileTwoeditText.getText().toString();
                    Constant.PHONE_NO = sharedPreferences.getString("phone","");
                    editTxt.setText("EDIT");
                    MobileTwoeditText.setEnabled(false);
                    updateProfileService();
                }
            }
        });

    }

    @Override
    public void requestFinished(String[] result) throws Exception {
            if(result[0].equals("1")){
                userNameTxt.setText(Global.profileList.get(0).getFirstName()+" "+Global.profileList.get(0).getLastName());
                AddresseditText.setText(Global.profileList.get(0).getAddress());
                cityEditTxt.setText(Global.profileList.get(0).getCity());
                ThanaEditTxt.setText(Global.profileList.get(0).getCity());
                DistEditTxt.setText(Global.profileList.get(0).getCity());
                PinEditTxt.setText(Global.profileList.get(0).getPincode());
                MobilOneTxt.setText(Global.profileList.get(0).getContactNo());
                MobileTwoeditText.setText(Global.profileList.get(0).getOtherContactNo());
            }else if(result[0].equals("101")) {
                Toast.makeText(getApplicationContext(),"Profile has been updated.",Toast.LENGTH_SHORT).show();
            }
    }
}
