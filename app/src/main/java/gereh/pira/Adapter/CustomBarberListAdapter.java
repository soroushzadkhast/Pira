package gereh.pira.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import gereh.pira.Items.BarberItem;
import gereh.pira.R;
import gereh.pira.TextView;

/**
 * Created by Soroush on 08/08/2015.
 */
public class CustomBarberListAdapter extends BaseAdapter {

    private Activity activity;
    private List<BarberItem> items;
    private LayoutInflater inflater;

    public CustomBarberListAdapter(Activity activity, List<BarberItem> items) {
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
            view = inflater.inflate(R.layout.barber_list_item, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView about = (TextView) view.findViewById(R.id.about);
        android.widget.TextView shortname = (android.widget.TextView) view.findViewById(R.id.barber_name);

        BarberItem item = items.get(i);

        name.setText(item.getName());
        about.setText(item.getAbout());
        shortname.setText(item.getShortName());

        return view;
    }
}
