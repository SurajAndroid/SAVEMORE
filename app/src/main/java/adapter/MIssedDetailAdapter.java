package adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suraj.missedcall.GetMissedCallDetail;
import com.suraj.missedcall.R;

import utils.Constant;
import utils.Global;
import utils.MarshMallowPermission;


/**
 * Created by Suraj shakya on 11/8/16.
 * shakyasuraj08@mail.com
 */
public class MIssedDetailAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;

    Activity activity;
    MarshMallowPermission marshMallowPermission;

    public MIssedDetailAdapter(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        marshMallowPermission =new MarshMallowPermission(activity);
    }



    @Override
    public int getCount() {
        return  Global.missed_detailList.size();
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
          convertView = layoutInflater.inflate(R.layout.missed_call_count,null);
          holder.numberCountTxt = (TextView)convertView.findViewById(R.id.numberCountTxt);
          holder.numberTxt = (TextView)convertView.findViewById(R.id.numberTxt);
          holder.countTxt = (TextView)convertView.findViewById(R.id.countTxt);
            holder.weekCountTxt = (TextView)convertView.findViewById(R.id.weekCountTxt);
            holder.callTxt = (TextView)convertView.findViewById(R.id.callTxt);
            holder.missedTxt = (TextView)convertView.findViewById(R.id.missedTxt);


          convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.numberCountTxt.setText(""+(position+1));
        holder.numberTxt.setText("+91- "+Global.missed_detailList.get(position).getSourceNumber());
        holder.countTxt.setText("("+Global.missed_detailList.get(position).getTotalCount()+")");
        holder.missedTxt.setText(Global.missed_detailList.get(position).getTimestampString());


        return convertView;
    }

    class ViewHolder{
        TextView numberCountTxt, numberTxt, countTxt, weekCountTxt,callTxt, missedTxt;

    }
}
