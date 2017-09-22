package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.suraj.missedcall.R;

import java.util.ArrayList;

import DTOs.CallLog;


/**
 * Created by Suraj shakya on 11/8/16.
 * shakyasuraj08@mail.com
 */
public class MissedcallAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    Context context;
    Activity activity;
    public ArrayList<CallLog> candidateList;
    SharedPreferences sharedPreferences;
    String TAG;

    public MissedcallAdapter(Context context, ArrayList<CallLog> candidateList){
        this.context = context;
        this.candidateList = candidateList;
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
        return candidateList.size();
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
          convertView = layoutInflater.inflate(R.layout.testing,null);
          holder.textLarge = (TextView) convertView.findViewById(R.id.textLarge);
            holder.imageView=(ImageView)convertView.findViewById(R.id.image_view);
          convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        String firstLetter = String.valueOf(position+1);
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color =
                generator.getColor(candidateList.get(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound("0"+firstLetter, color); // radius in px

        holder.imageView.setImageDrawable(drawable);

        holder.textLarge.setText(candidateList.get(position).getNumber());
        return convertView;
    }

    class ViewHolder{
        TextView textLarge;
        ImageView imageView;

    }
}
