package gereh.pira.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import gereh.pira.R;
import gereh.pira.TextView;

public class ViewPagerAdminReserveDate extends PagerAdapter {

    private Context context;
    private String[] area;
    private LayoutInflater inflater;

    public ViewPagerAdminReserveDate(Context context, String[] area) {
        this.context = context;
        this.area = area;
    }

    @Override
    public int getCount() {
        return area.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.filter_viewpager_items, null);

        TextView textView_name = (TextView) view.findViewById(R.id.text_name);
        textView_name.setText(area[position]);

        ((ViewPager) container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ((ViewPager) container).removeView((RelativeLayout) object);

    }

}
