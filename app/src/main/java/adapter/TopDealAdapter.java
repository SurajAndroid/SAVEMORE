package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.suraj.missedcall.R;

import java.util.List;




public class TopDealAdapter extends BaseAdapter {
	Context context;
	List<String> topDealsList;
	public TopDealAdapter(Context context, List<String> topDealsList ) {
		super();
		this.context = context;
		this.topDealsList=topDealsList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return topDealsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return topDealsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Holder  holder = new Holder();
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.dealrow_item, parent,false);
			holder.messageTxt= (TextView)convertView.findViewById(R.id.messageTxt);
		}

		holder.messageTxt.setText(topDealsList.get(position));

		return convertView;
	}

	public class Holder{
		TextView messageTxt;
	}
}
