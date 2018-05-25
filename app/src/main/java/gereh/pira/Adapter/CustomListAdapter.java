package gereh.pira.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gereh.pira.Items.Item;
import gereh.pira.R;


public class CustomListAdapter extends BaseAdapter{

    private Activity activity;
    private List<Item> items;
    private LayoutInflater inflater;

    public CustomListAdapter(Activity activity, List<Item> items) {
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
            view = inflater.inflate(R.layout.list_item, null);
        }

        gereh.pira.TextView textView_name = (gereh.pira.TextView) view.findViewById(R.id.name);
        gereh.pira.TextView textView_address = (gereh.pira.TextView) view.findViewById(R.id.address);
        TextView image_text = (TextView) view.findViewById(R.id.image_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);


        Item item = items.get(i);

        textView_name.setText(item.getName());
        textView_address.setText(item.getAddress());
        image_text.setText(item.getShortName());

        if (i % 2 == 0){
            imageView.setImageResource(R.color.md_blue_400);
        } else {
            imageView.setImageResource(R.color.md_blue_700);
        }

        return view;
    }
}
