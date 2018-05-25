package gereh.pira;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivity extends android.app.TabActivity{


    public static final String FavStr = "مورد علاقه";
    public static final String NearStr = "نزدبک ترین";
    public static final String OfferStr = "پیشنهاد ما";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);

        TabHost mTabHost = (TabHost) findViewById(R.id.tabHost);

        TabHost.TabSpec fav_spec = mTabHost.newTabSpec(FavStr);
        Intent fav_Intent = new Intent(this, Favorit.class);
        fav_spec.setContent(fav_Intent);


        TabHost.TabSpec near_spec = mTabHost.newTabSpec(NearStr);
        Intent near_Intent = new Intent(this, Near.class);
        near_spec.setContent(near_Intent);

        TabHost.TabSpec offer_spec = mTabHost.newTabSpec(OfferStr);
        Intent offer_Intent = new Intent(this, Offer.class);
        offer_spec.setContent(offer_Intent);

        mTabHost.addTab(fav_spec);
        mTabHost.addTab(near_spec);
        mTabHost.addTab(offer_spec);
    }
}