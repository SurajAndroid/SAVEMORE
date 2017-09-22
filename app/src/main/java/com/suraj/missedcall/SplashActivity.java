
package com.suraj.missedcall;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends Activity
{
    TextView tv_version;
    String terms_id;
    String token="1";
    // MediaPlayer sound;
    Context mContext;
    public static SplashActivity smCurrentActivity=null;
    SharedPreferences sharedPreferences;
    public static SplashActivity getCurrentActivity()
    {
        return smCurrentActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }
        mContext = this;
        sharedPreferences = this.getSharedPreferences("verify", Context.MODE_PRIVATE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        if (android.os.Build.VERSION.SDK_INT >= 21) {

        }

        try
        {

            // Toast.makeText(getApplicationContext(),"Token="+token, Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        smCurrentActivity=this;
        tv_version=(TextView)findViewById(R.id.tv_version);
        try {
            String appVersion=getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            tv_version.setText("Version: " + appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Thread timer = new Thread() {

            public void run() {

                try {
                    sleep(3000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                finally
                {
                    {
                        {
                            if (token.equalsIgnoreCase("1"))
                            {
                                if(sharedPreferences.getString("status","").equals("1")){
                                    Intent intent = new Intent(SplashActivity.this, HomeActivityy.class); //SignUPActivity
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Intent intent = new Intent(SplashActivity.this, MessageActivity.class); //SignUPActivity
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                if(sharedPreferences.getString("status","").equals("1")){
                                    Intent intent = new Intent(SplashActivity.this, HomeActivityy.class); //SignUPActivity
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Intent intent = new Intent(SplashActivity.this, MessageActivity.class); //SignUPActivity
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }
                }

            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
// TODO Auto-generated method stub
        super.onPause();
        // sound.release();
        finish();
    }

}
















