package adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suraj.missedcall.GetMissedCallActivity;
import com.suraj.missedcall.GetMissedCallDetail;
import com.suraj.missedcall.R;

import utils.Constant;
import utils.Global;
import utils.MarshMallowPermission;


/**
 * Created by Suraj shakya on 11/8/16.
 * shakyasuraj08@mail.com
 */
public class MIssedAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;

    Activity activity;
    MarshMallowPermission marshMallowPermission;

    public MIssedAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        marshMallowPermission =new MarshMallowPermission(activity);
    }



    @Override
    public int getCount() {
        return  Global.missedList.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater = LayoutInflater.from(context);
        final ViewHolder holder;
        if(convertView==null){
          holder = new ViewHolder();
          convertView = layoutInflater.inflate(R.layout.missed_call,null);
          holder.numberCountTxt = (TextView)convertView.findViewById(R.id.numberCountTxt);
          holder.numberTxt = (TextView)convertView.findViewById(R.id.numberTxt);
          holder.countTxt = (TextView)convertView.findViewById(R.id.countTxt);
            holder.weekCountTxt = (TextView)convertView.findViewById(R.id.weekCountTxt);
            holder.callTxt = (TextView)convertView.findViewById(R.id.callTxt);


          convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.numberCountTxt.setText(""+(position+1));
        holder.numberTxt.setText("+91- "+Global.missedList.get(position).getSourceNumber());
        holder.countTxt.setText("("+Global.missedList.get(position).getTodayCount()+")");
        holder.weekCountTxt.setText("("+Global.missedList.get(position).getTotalCount()+")");


        holder.callTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Global.missedList.get(position).getSourceNumber()));
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                        if(!marshMallowPermission.checkPermissionForCall()){
                            marshMallowPermission.requestPermissionForCall();
                        }
                        return;
                    }
                    activity.startActivity(callIntent);

                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.SOURCE_NUMBER = Global.missedList.get(position).getSourceNumber();
                Intent intent =new Intent(activity, GetMissedCallDetail.class);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView numberCountTxt, numberTxt, countTxt, weekCountTxt,callTxt;

    }
}
