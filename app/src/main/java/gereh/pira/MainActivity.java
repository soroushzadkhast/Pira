package gereh.pira;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gereh.pira.Adapter.CustomFavListAdapter;
import gereh.pira.Adapter.CustomListAdapter;
import gereh.pira.Items.FavItem;
import gereh.pira.Items.Item;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private TextView tv;

    private String[] names = {
            "آرایشگاه نیما",
            "آرایشگاه شاهرخ",
            "آرایشگاه نیما",
            "آرایشگاه شاهرخ",
            "آرایشگاه نیما",
            "آرایشگاه شاهرخ",
            "آرایشگاه نیما",
            "آرایشگاه شاهرخ",
            "آرایشگاه نیما",
            "آرایشگاه شاهرخ"
    };

    private String address = "تهران | منطقه 2 |";

    private CustomListAdapter adapter;
    private List<Item> itemList = new ArrayList<Item>();

    private CustomFavListAdapter adapter_fav;
    private List<FavItem> itemList_fav = new ArrayList<FavItem>();

    public static final String FavStr = "مورد علاقه";
    public static final String NearStr = "نزدیک ترین";
    public static final String OfferStr = "پیشنهاد ما";

    private CircleImageView img_user;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_user = (CircleImageView) findViewById(R.id.user_image);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserPage.class);
                startActivity(intent);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        if (navigationView != null){
            setupNavigationDrawerContent(navigationView);
        }

        setupNavigationDrawerContent(navigationView);

        imageView = (ImageView) findViewById(R.id.image_ic_drawer);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });

        final TabHost mTabHost = (TabHost) findViewById(R.id.tabHost);
        mTabHost.setup();

        TabHost.TabSpec fav_spec = mTabHost.newTabSpec(FavStr);
        fav_spec.setIndicator(FavStr, getResources().getDrawable(R.drawable.inbox_selector));
        fav_spec.setContent(R.id.tab1);

        TabHost.TabSpec near_spec = mTabHost.newTabSpec(NearStr);
        near_spec.setIndicator(NearStr, getResources().getDrawable(R.drawable.inbox_selector));
        near_spec.setContent(R.id.tab2);

        TabHost.TabSpec offer_spec = mTabHost.newTabSpec(OfferStr);
        offer_spec.setIndicator(OfferStr, getResources().getDrawable(R.drawable.inbox_selector));
        offer_spec.setContent(R.id.tab3);

        mTabHost.addTab(fav_spec);
        mTabHost.addTab(near_spec);
        mTabHost.addTab(offer_spec);

        /*View view = mTabHost.getTabWidget();
        view.setBackgroundResource(R.drawable.tab_shape);*/

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
           /* View v = mTabHost.getTabWidget().getChildAt(i);
            v.setBackgroundResource(R.drawable.inbox_selector);*/

            /*tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(R.drawable.inbox_selector);*/
        }

        mTabHost.setCurrentTab(1);
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                .setBackgroundResource(R.drawable.tab_unselected);

        mTabHost.getTabWidget().setStripEnabled(false);
        mTabHost.getTabWidget().setLeftStripDrawable(R.color.md_blue_500);
        mTabHost.getTabWidget().setRightStripDrawable(R.color.md_blue_500);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                /*if (mTabHost.getCurrentTab() == 0){
                    View v = mTabHost.getTabWidget().getChildAt(0);
                    v.setBackgroundResource(R.drawable.icon_fav);

                    tv = (TextView) mTabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                    tv.setTextColor(R.drawable.icon_fav);
                } else if (mTabHost.getCurrentTab() == 1){
                    View v = mTabHost.getTabWidget().getChildAt(1);
                    v.setBackgroundResource(R.drawable.icon_fav);

                    tv = (TextView) mTabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
                    tv.setTextColor(R.drawable.icon_fav);
                } else {
                    View v = mTabHost.getTabWidget().getChildAt(2);
                    v.setBackgroundResource(R.drawable.inbox_selector);

                    tv = (TextView) mTabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
                    tv.setTextColor(R.drawable.icon_fav);
                }*/

                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    mTabHost.getTabWidget().getChildAt(i)
                            .setBackgroundResource(R.drawable.tab_selected);
                }
                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                        .setBackgroundResource(R.drawable.tab_unselected);

                //mTabHost.getTabWidget().setBackgroundResource(R.drawable.tab_shape);
                mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_shape);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView_near);
        adapter = new CustomListAdapter(this, itemList);
        listView.setAdapter(adapter);

        fetchJson();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, BarberPage.class);
                Item items = itemList.get(i);

                intent.putExtra("name_barbershop", items.getName());
                intent.putExtra("address_barbershop", items.getAddress());
                intent.putExtra("shortname_barbershop", items.getShortName());
                intent.putExtra("fulladdress_barbershop", items.getFullAddress());
                intent.putExtra("phone_barbershop", items.getPhone());
                intent.putExtra("id_barbershop", items.getId());

                startActivity(intent);
            }
        });

        ListView list_fav = (ListView) findViewById(R.id.listView_fav);
        adapter_fav = new CustomFavListAdapter(this, itemList_fav);
        list_fav.setAdapter(adapter_fav);

        fetchJsonFav();

        final ImageView filter_image = (ImageView) findViewById(R.id.image_ic_filter);
        filter_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_in);
                Fragment newFragment = new Filter();

                ft.replace(R.id.frame, newFragment, "Fragment");
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        ImageView imageView1 = (ImageView) findViewById(R.id.img_pira);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminSetting.class);
                startActivity(intent);
            }
        });

    }

    private String URL = "http://piraa.ir/barbershop/api/barbershop/?format=json";
    private String URL_Fav = "http://piraa.ir/barbershop/api/favorite/?format=json";
    private String URL_Fav_Barber = "http://piraa.ir/barbershop/api/barber/?format=json";
    private String URL_Fav_Barber_Shop = "http://piraa.ir/barbershop/api/barbershop/?format=json";

    int user_id = 9, barber_id, barberShop_id;
    int s = 0, k = 0;

    private void fetchJson() {

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.i("enter", "response");

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);

                        Item item = new Item();
                        item.setName(object.getString("name"));
                        item.setAddress(object.getString("city") + " | " + object.getString("area"));
                        item.setShortName(object.getString("shortName"));
                        item.setFullAddress(object.getString("address"));
                        item.setPhone(object.getString("phoneNumber"));
                        item.setId(object.getInt("id"));

                        Log.i("true",object.getString("city"));

                        itemList.add(item);

                    } catch (JSONException e){
                        e.printStackTrace();
                        Log.i("catch", "error");
                    }

                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "error");
                if (error.getMessage() != null) {
                    Log.i("Error_Fetch_Json", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    private void fetchJsonFav() {

        JsonArrayRequest req = new JsonArrayRequest(URL_Fav, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getInt("userID") == user_id) {

                            barberShop_id = object.getInt("barbershopID");
                            fetchJsonFavBarberShop(barberShop_id);
                            Log.i("kkk", k++ + "");

                        }


                    } catch (JSONException e){
                        e.printStackTrace();
                        Log.i("catch", "error");
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Log.i("Error_Fetch_Json", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    /*private void fetchJsonFavBarber() {

        JsonArrayRequest req = new JsonArrayRequest(URL_Fav_Barber, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getInt("id") == barber_id) {

                            barberShop_id = object.getInt("barbershopID");

                            fetchJsonFavBarberShop();
                        }


                    } catch (JSONException e){
                        e.printStackTrace();
                        Log.i("catch", "error");
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Log.i("Error_Fetch_Json", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }*/

    private void fetchJsonFavBarberShop(final int id) {

        JsonArrayRequest req = new JsonArrayRequest(URL_Fav_Barber_Shop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);

                        Log.i("barbershop", id+"");

                        if (object.getInt("id") == id) {

                            FavItem fav_item = new FavItem();
                            fav_item.setName(object.getString("name"));
                            fav_item.setAddress(object.getString("city") + " | " + object.getString("area"));
                            fav_item.setShortName(object.getString("shortName"));
                            fav_item.setFullAddress(object.getString("address"));
                            fav_item.setPhone(object.getString("phoneNumber"));
                            fav_item.setId(object.getInt("id"));

                            itemList_fav.add(fav_item);
                        }

                    } catch (JSONException e){
                        e.printStackTrace();
                        Log.i("catch", "error");
                    }

                }
                adapter_fav.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Log.i("Error_Fetch_Json", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    private void setupNavigationDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.item_navigation_drawer_firstpage :

                        Intent intent = new Intent(MainActivity.this, PhoneNumber.class);
                        startActivity(intent);

                        return true;
                    case R.id.item_navigation_drawer_about :
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                        return true;
                    case R.id.item_navigation_drawer_contact :
                        drawerLayout.closeDrawer(Gravity.RIGHT);
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
