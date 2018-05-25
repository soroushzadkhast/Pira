package gereh.pira.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import gereh.pira.Items.FavItem;
import gereh.pira.R;
import gereh.pira.TextView;

public class CustomFavListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FavItem> items;

    public CustomFavListAdapter(Activity activity, List<FavItem> items) {
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
            view = inflater.inflate(R.layout.fav_list_item, null);
        }

        TextView text_name = (TextView) view.findViewById(R.id.name);
        TextView text_address = (TextView) view.findViewById(R.id.address);
        android.widget.TextView text_image = (android.widget.TextView) view.findViewById(R.id.image_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        FavItem item = items.get(i);

        text_name.setText(item.getName());
        text_address.setText(item.getAddress());
        text_image.setText(item.getShortName());

        if (i % 2 == 0){
            imageView.setImageResource(R.color.md_blue_400);
        } else {
            imageView.setImageResource(R.color.md_blue_700);
        }

        return view;
    }
}