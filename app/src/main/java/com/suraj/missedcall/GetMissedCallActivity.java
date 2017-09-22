package com.suraj.missedcall;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import DTOs.MissedCall;
import DTOs.MissedCount;
import adapter.MIssedAdapter;
import utils.Constant;
import utils.Global;
import utils.RequestReceiver;
import utils.WebserviceHelper;

import static android.R.id.list;

/**
 * Created by SURAJ on 9/12/2017.
 */

public class GetMissedCallActivity extends Activity implements RequestReceiver {

    ListView missedList;
    RequestReceiver receiver;
    ImageView drawer_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_missed_call);
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
        missedList = (ListView)findViewById(R.id.missedList);
        drawer_nav =(ImageView)findViewById(R.id.drawer_nav);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        Constant.TILLNOWDATE = mdformat.format(calendar.getTime());


        getMissednumber();

    }

    public void clickListener(){

        drawer_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void getMissednumber(){
        WebserviceHelper missedcall= new WebserviceHelper(receiver,GetMissedCallActivity.this);
        missedcall.setAction(Constant.GET_MISSEDCALL);
        missedcall.execute();
    }

    @Override
    public void requestFinished(String[] result) throws Exception {
            if(result[0].equals("101")){

                if(Global.missedList.size()!=0){

                    Global.missedcount.clear();
                    MissedCount missedCount= null;
                    List<String> list = new ArrayList<String>();
                    for(int i=0;i<Global.missedList.size();i++){
                        list.add(Global.missedList.get(i).getSourceNumber());
                    }

                    Set<String> uniqueSet = new HashSet<String>(list);
                    for (String temp : uniqueSet) {
                         missedCount = new MissedCount();
                        System.out.println(temp + ": " + Collections.frequency(list, temp));
                        missedCount.setNumber(temp);
                        missedCount.setCount(""+Collections.frequency(list, temp));
                        Global.missedcount.add(missedCount);
                    }

                    MIssedAdapter mIssedAdapter =new MIssedAdapter(GetMissedCallActivity.this,GetMissedCallActivity.this);
                    missedList.setAdapter(mIssedAdapter);
                }else {
                    Toast.makeText(getApplicationContext(),"No Missed call number found.",Toast.LENGTH_SHORT).show();
                }

            }
    }
}
