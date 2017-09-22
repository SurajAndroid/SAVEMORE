package com.suraj.missedcall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.provider.CallLog;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adapter.CustomExpandableListAdapter;
import adapter.ExpandableListDataPump;
import adapter.MissedcallAdapter;
import utils.Global;


/**
 * Created by Gajanand Gupta on 7/25/2017.
 */

public class MissedcallActivity extends Activity implements View.OnClickListener {

    String username_navigation = "";


    TextView username_nav;
    ImageView  drawer_nav;
    LinearLayout  home_drawer, missedCallLayout, campaningLayout, accountLayout, profile, settingLayout;
    DrawerLayout drawer_layout;
    Context mContext;
    ExpandableListView MenuListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList arrayList = new ArrayList();
    ListView listmissedcall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missedcall_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#feb211"));
        }
        mContext = this;
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.setOnClickListener(this);
        arrayList.add("01");
        arrayList.add("02");
        arrayList.add("03");
        arrayList.add("04");
        arrayList.add("05");
        arrayList.add("06");
        arrayList.add("07");

        listmissedcall=(ListView)findViewById(R.id.list_item);


        username_nav = (TextView) findViewById(R.id.username_nav);
        username_nav.setText("USER NAME " + username_navigation);

        drawer_nav = (ImageView) findViewById(R.id.drawer_nav);
        drawer_nav.setOnClickListener(this);

        missedCallLayout = (LinearLayout) findViewById(R.id.missedCallLayout);
        missedCallLayout.setOnClickListener(this);
        campaningLayout = (LinearLayout) findViewById(R.id.campaningLayout);
        campaningLayout.setOnClickListener(this);
        accountLayout = (LinearLayout) findViewById(R.id.accountLayout);
        accountLayout.setOnClickListener(this);
        profile = (LinearLayout) findViewById(R.id.profile);
        profile.setOnClickListener(this);
        settingLayout = (LinearLayout) findViewById(R.id.settingLayout);
        settingLayout.setOnClickListener(this);

        Log.e("","Missed : "+getCallDetails());
        Toast.makeText(getApplicationContext(),"List SIze :  : "+Global.callLogList.size(),Toast.LENGTH_SHORT).show();
        MenuListView= (ExpandableListView)findViewById(R.id.MenuListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        MenuListView.setAdapter(expandableListAdapter);


        MenuListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("Missed Call")){
              /*    Intent  intent =new Intent(MissedcallActivity.this,MissedcallActivity.class);
                    startActivity(intent);*/
                }
                return false;
            }

        });
        getCallDetails();

    }


    private String getCallDetails() {

        Global.callLogList.clear();
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);

            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";

                    DTOs.CallLog callLog = new DTOs.CallLog();
                    callLog.setNumber(phNumber);
                    callLog.setType(callType);
                    callLog.setDatTime(callDayTime.toString());
                    callLog.setDate(callDate);
                    callLog.setDuration(callDuration);

                    Global.callLogList.add(callLog);
                    break;
            }
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
        }
        managedCursor.close();
        Log.e("","Size of List : "+Global.callLogList.size());
        MissedcallAdapter missedcallAdapter = new MissedcallAdapter(MissedcallActivity.this,Global.callLogList);
        listmissedcall.setAdapter(missedcallAdapter);
        return sb.toString();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {

            case R.id.missedCallLayout:
                drawer_layout.closeDrawers();

                Intent intent= new Intent(MissedcallActivity.this,MissedcallActivity.class);
                startActivity(intent);
                Toast.makeText(MissedcallActivity.this, "Missed call clicked", Toast.LENGTH_LONG).show();
                break;
            case R.id.campaningLayout:
                Toast.makeText(MissedcallActivity.this, "Cammpaign clicked", Toast.LENGTH_LONG).show();
                drawer_layout.closeDrawers();
                break;
            case R.id.accountLayout:
                Toast.makeText(MissedcallActivity.this, "Account clicked", Toast.LENGTH_LONG).show();
                drawer_layout.closeDrawers();
                break;

            case R.id.profile:
                Toast.makeText(MissedcallActivity.this, "Profile clicked", Toast.LENGTH_LONG).show();
                drawer_layout.closeDrawers();
                break;
            case R.id.settingLayout:
                Toast.makeText(MissedcallActivity.this, "Default Service clicked", Toast.LENGTH_LONG).show();
                drawer_layout.closeDrawers();
                break;

            case R.id.drawer_nav:
                drawer_layout.openDrawer(GravityCompat.START);

                break;


        }
    }
}
