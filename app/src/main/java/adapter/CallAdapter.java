package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.suraj.missedcall.R;

import java.util.ArrayList;

import utils.Constant;
import utils.Global;
import utils.RequestReceiver;
import utils.WebserviceHelper;


/**
 * Created by Suraj shakya on 11/8/16.
 * shakyasuraj08@mail.com
 */
public class CallAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    Activity activity;
    RequestReceiver receiver;
    public ArrayList<String> candidateList;
    SharedPreferences sharedPreferences;
    String TAG;


    public CallAdapter(Context context, Activity activity, RequestReceiver receiver){
        this.context = context;
        this.activity = activity;
        this.receiver = receiver;
        sharedPreferences = context.getSharedPreferences("verify", Context.MODE_PRIVATE);
        Constant.PHONE_NO = sharedPreferences.getString("phone","");
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

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
          convertView = layoutInflater.inflate(R.layout.call_inflater,null);
          holder.numberTXt = (TextView)convertView.findViewById(R.id.numberTXt);
          holder.updateNicNameTxt  = (TextView)convertView.findViewById(R.id.updateNicNameTxt);
          holder.nickNameTxt = (EditText) convertView.findViewById(R.id.nickNameTxt);
          holder.editNumberImg = (ImageView)convertView.findViewById(R.id.editNumberImg);

          convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(Global.missedcallList.get(position).getNumber().length()==8){
            holder.numberTXt.setText( Global.missedcallList.get(position).getNumber());
        }else {
            holder.numberTXt.setText( Global.missedcallList.get(position).getNumber());
        }

        holder.nickNameTxt.setText(Global.missedcallList.get(position).getNickName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent intent = new Intent(context, CampaignActivity.class);
                context.startActivity(intent);*/

            }
        });

        holder.editNumberImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.updateNicNameTxt.setVisibility(View.VISIBLE);
                holder.nickNameTxt.setEnabled(true);
            }
        });

        holder.updateNicNameTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.NICKPHONE_NO = Global.missedcallList.get(position).getNumber();
                if(holder.nickNameTxt.getText().length()!=0){
                    Constant.NICK_NAME = holder.nickNameTxt.getText().toString();
                    updateNickName();
                    holder.updateNicNameTxt.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(context,"Please Enter nick name.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    public void updateNickName(){
        WebserviceHelper nickName= new WebserviceHelper(receiver,activity);
        nickName.setAction(Constant.UPDATE_NICK_NAME);
        nickName.execute();
    }
    class ViewHolder{
        TextView numberTXt,updateNicNameTxt;
        EditText nickNameTxt;
        ImageView editNumberImg;

    }
}
