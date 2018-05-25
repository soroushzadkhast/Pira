package gereh.pira.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import gereh.pira.R;
import gereh.pira.Items.ReserveItem;
import gereh.pira.TextView;

public class CustomReserveListAdapter extends BaseAdapter {

    private Activity activity;
    private List<ReserveItem> items;
    private LayoutInflater inflater;

    public CustomReserveListAdapter(Activity activity, List<ReserveItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null){
            view = inflater.inflate(R.layout.list_reserve_items, null);
        }

        TextView text_name = (TextView) view.findViewById(R.id.name);
        TextView text_time = (TextView) view.findViewById(R.id.time);
        android.widget.TextView text_shortName = (android.widget.TextView) view.findViewById(R.id.image_text);
        ImageView image_status = (ImageView) view.findViewById(R.id.status);

        ReserveItem item = items.get(i);

        text_name.setText(item.getBarberShopName());
        text_time.setText(item.getTime());
        text_shortName.setText(item.getShortName());

        if (item.getStatus().equals("W")){

            image_status.setImageResource(R.drawable.ic_close_black_36dp);

        }else {

            image_status.setImageResource(R.drawable.ic_check_black_36dp);

        }

        Log.i("BSName", item.getBarberShopName());
        Log.i("Time", item.getTime());
        //Log.i("ShortName", item.getShortName());
        Log.i("Status", item.getStatus());

        return view;
    }
}
