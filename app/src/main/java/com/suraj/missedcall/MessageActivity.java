package com.suraj.missedcall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utils.Constant;
import utils.MarshMallowPermission;
import utils.RequestReceiver;
import utils.WebserviceHelper;

/**
 * Created by SURAJ on 7/25/2017.
 */

public class MessageActivity extends Activity implements RequestReceiver{

    RequestReceiver receiver;
    Button veify;
    EditText mobileNoEdit;
    MarshMallowPermission marshMallowPermission;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_reg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }
        veify = (Button)findViewById(R.id.veify);

        veify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MessageActivity.this, Otp.class);
                startActivity(intent);
            }
        });

        init();
        clickListener();
    }

    public void init(){
        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);
        Constant.DEVICE_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
//        Toast.makeText(getApplicationContext(),""+ Constant.DEVICE_ID,Toast.LENGTH_SHORT).show();
        marshMallowPermission = new MarshMallowPermission(MessageActivity.this);
        receiver = this;
        mobileNoEdit = (EditText)findViewById(R.id.mobileNoEdit);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!marshMallowPermission.checkPermissionForSms()) {
                marshMallowPermission.requestPermissionForSms();
                if (!marshMallowPermission.checkPermissionForSmsReed()) {
                    marshMallowPermission.checkPermissionForSms();
                    if (!marshMallowPermission.checkPermissionForReadCallLog()) {
                        marshMallowPermission.requestPermissionForReadCallLog();
                    }
                }
            }
        }
    }


    public void getOTPService(){
        WebserviceHelper otp= new WebserviceHelper(receiver,MessageActivity.this);
        otp.setAction(Constant.GENRATE_OTP);
        otp.execute();
    }

    public void GetregisterService(){
        WebserviceHelper otp= new WebserviceHelper(receiver,MessageActivity.this);
        otp.setAction(Constant.REGISTER_DEVICE);
        otp.execute();
    }

    public void clickListener(){

        veify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mobileNoEdit.getText().length()!=0){
                        if(mobileNoEdit.getText().length()>=10){
                            Constant.PHONE_NO = mobileNoEdit.getText().toString();
                            GetregisterService();
                        }else {
                            Toast.makeText(getApplicationContext(),"Enter valid mobile number",Toast.LENGTH_SHORT).show();
                        }
                }else {
                    Toast.makeText(getApplicationContext(),"Enter mobile number",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void requestFinished(String[] result) throws Exception {
        if(result[0].equals("1")){
            Intent intent = new Intent(MessageActivity.this, Otp.class);
            startActivity(intent);
        }else if(result[0].equals("101")){

            if(Constant.DEVICE_ID.equals(Constant.GETDEVICE_ID)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("status", "1");
                editor.putString("phone",""+Constant.PHONE_NO);
                editor.commit();
                Intent  intent = new Intent(MessageActivity.this, HomeActivityy.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                getOTPService();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Invalid User",Toast.LENGTH_SHORT).show();
        }
    }
}
