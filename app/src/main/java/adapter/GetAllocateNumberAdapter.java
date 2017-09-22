package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suraj.missedcall.GetMissedCallActivity;
import com.suraj.missedcall.MissedCallDetail;
import com.suraj.missedcall.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import DTOs.CallLog;
import utils.Constant;
import utils.Global;


/**
 * Created by Suraj shakya on 11/8/16.
 * shakyasuraj08@mail.com
 */
public class GetAllocateNumberAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    SharedPreferences sharedPreferences;
    String TAG;
    Activity activity;

    public GetAllocateNumberAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

 /*   @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }*/

    @Override
    public int getCount() {
        return Global.missedcallList.size();
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
          convertView = layoutInflater.inflate(R.layout.missed_call_inflater,null);
          holder.nickNameTxt = (TextView)convertView.findViewById(R.id.nickNameTxt);
          holder.numberTXt = (TextView)convertView.findViewById(R.id.numberTXt);
          holder.monthCountTxt = (TextView)convertView.findViewById(R.id.monthCountTxt);
          holder.totalCountTxt = (TextView)convertView.findViewById(R.id.totalCountTxt);

          convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nickNameTxt.setText(Global.missedcallList.get(position).getNickName());
        if(Global.missedcallList.get(position).getNumber().length()==8){
            holder.numberTXt.setText( Global.missedcallList.get(position).getNumber());
        }else {
            holder.numberTXt.setText( Global.missedcallList.get(position).getNumber());
        }
//        holder.numberTXt.setText("+91- "+Global.missedcallList.get(position).getNumber());
        holder.monthCountTxt.setText(Global.missedcallList.get(position).getCurrentMonthCount());
        holder.totalCountTxt.setText(Global.missedcallList.get(position).getTotalCount());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.NICKPHONE_NO = Global.missedcallList.get(position).getNumber();
                String[] date = Global.missedcallList.get(position).getCreationDate().split("T");
                Constant.CREATIONDATE = date[0];


                Intent intent = new Intent(activity, GetMissedCallActivity.class);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder{
        TextView nickNameTxt, numberTXt, monthCountTxt, totalCountTxt;

    }
}
