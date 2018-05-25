package gereh.pira;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gereh.pira.Adapter.CustomReserveListAdapter;
import gereh.pira.Items.FavItem;
import gereh.pira.Items.ReserveItem;

public class UserPage extends AppCompatActivity {

    private ImageView img_back;
    private Toolbar toolbar;
    private String name, phoneNumber;
    private int id;
    private TextView text_name, text_number;

    private ListView listView;
    private CustomReserveListAdapter adapter;
    private List<ReserveItem> itemList = new ArrayList<ReserveItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        img_back = (ImageView) findViewById(R.id.image_ic_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        id = 9;

        text_name = (TextView) findViewById(R.id.name);
        text_number = (TextView) findViewById(R.id.user);

        listView = (ListView) findViewById(R.id.list_res);
        adapter = new CustomReserveListAdapter(this, itemList);
        listView.setAdapter(adapter);

        fetchJsonUser();
        fetchJsonTime();
    }

    private String URL = "http://piraa.ir/barbershop/api/user/?format=json";
    private String URL_Reserve = "http://piraa.ir/barbershop/api/reserve/?format=json";
    private String URL_Barber = "http://piraa.ir/barbershop/api/barber/?format=json";
    private String URL_BarberShop = "http://piraa.ir/barbershop/api/barbershop/?format=json";

    private void fetchJsonUser() {

        final JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getInt("id") == id) {

                            name = object.getString("name");
                            phoneNumber = object.getString("phoneNo");

                            text_name.setText(name);
                            text_number.setText("کاربر معمولی | " + phoneNumber);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null){
                    Log.i("volleyError", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }


    private int barber_id;
    private int barberShop_id;
    private String res_hour;
    private String res_date;
    private String res_weekDay;
    private String status;
    private String barber_name, shortName;
    private String barberShop_name;

    int j = 0;
    int s = 0;

    private void fetchJsonTime() {

        final JsonArrayRequest req = new JsonArrayRequest(URL_Reserve, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);

                        if (object.getInt("userID") == id) {


                            ReserveItem item = new ReserveItem();

                            barber_id = object.getInt("barberID");
                            res_hour = object.getString("reserveHour");
                            res_date = object.getString("reserveDate");
                            res_weekDay = object.getString("weekDay");
                            status = object.getString("status");
                            barber_name = object.getString("barberName");

                            item.setBarberShopName(object.getString("barbershopName"));
                            item.setTime(barber_name + " | " + res_weekDay + " " + res_date + " ساعت " + res_hour);
                            //item.setShortName(object.getString("shortName"));
                            item.setBarberName(object.getString("barberName"));
                            item.setStatus(status);
                            item.setShortName(fetchJsonShortName(barber_id));

                            j++;
                            itemList.add(item);
                        }
                    }
                adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null){
                    Log.i("volleyError", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }


    private String fetchJsonShortName(final int id) {

        JsonArrayRequest req = new JsonArrayRequest(URL_BarberShop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);


                        if (object.getInt("id") == id) {

                            shortName = object.getString("shortName");
                            Log.i("shortName",shortName);
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
        if (shortName != null) {
            return shortName;
        } else {
            return "";
        }
    }



    /*private void fetchJsonReserve() {

        JsonArrayRequest req = new JsonArrayRequest(URL_Reserve, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);


                        if (object.getInt("id") == id) {

                            ReserveItem item = new ReserveItem();

                            item.setBarberShopName(object.getString("name"));
                            item.setTime(barber_name + " | " + res_weekDay + " " + res_date + " ساعت " + res_hour);
                            item.setShortName(object.getString("shortName"));
                            item.setStatus(status);

                            itemList.add(item);
                        }

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
                if (error.getMessage() != null) {
                    Log.i("Error_Fetch_Json", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }




    private void fetchJsonBarber(final int id) {

        final JsonArrayRequest req = new JsonArrayRequest(URL_Barber, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int m = 0; m < j; m++) {

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject object = response.getJSONObject(i);

                            if (object.getInt("id") == id) {
                                Log.i("objectid", m + "..." + i);
                                barber_name = object.getString("name");
                                barberShop_id = object.getInt("barbershopID");

                                Log.i("barbername", barber_name + m + "..." + j);
                                fetchJsonBarberShop(barberShop_id);
                                s++;
                                break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null){
                    Log.i("volleyError", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    int k = 0;

    private void fetchJsonBarberShop(final int id) {

        final JsonArrayRequest req = new JsonArrayRequest(URL_BarberShop, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getInt("id") == id) {

                            ReserveItem item = new ReserveItem();

                            item.setBarberShopName(object.getString("name"));
                            item.setTime(barber_name + " | " + res_weekDay + " " + res_date + " ساعت " + res_hour);
                            item.setShortName(object.getString("shortName"));
                            item.setStatus(status);

                            //Log.i("barbershop", barber_name[k]);
                            Log.i("test", barber_name + " | " + res_weekDay + " " + res_date + " ساعت " + res_hour);

                            k++;

                            itemList.add(item);
                        }


                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null){
                    Log.i("volleyError", error.getMessage());
                }
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }*/
}
