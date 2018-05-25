package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import gereh.pira.Adapter.CustomListAdapter;
import gereh.pira.Items.Item;

public class SearchResult extends AppCompatActivity{

    private ListView listView;
    private List<Item> itemList = new ArrayList<Item>();
    private CustomListAdapter adapter;
    private String cost, area, city, name[] = new String[500];
    private double latitude, longitude;
    private ImageView imageView_map;
    private String filter, search;
    private double pointLat[] = new double[500], pointLon[] = new double[500];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult);

        listView = (ListView) findViewById(R.id.list_result);
        adapter = new CustomListAdapter(this, itemList);
        listView.setAdapter(adapter);

        Intent intent = getIntent();
        /*city = intent.getStringExtra("city");
        area = intent.getStringExtra("area");
        cost = intent.getStringExtra("costLevel");*/

        if (intent.getStringExtra("barbershop") != null){
            filter = "name";
            search = intent.getStringExtra("barbershop");
            fetchJsonCity();

        } else if (intent.getStringExtra("barber") != null) {
            filter = "name";
            search = intent.getStringExtra("barber");
            fetchJsonBarber();

        } else if (intent.getStringExtra("city") != null) {
            filter = "city";
            search = intent.getStringExtra("city");
            fetchJsonCity();

        } else if (intent.getStringExtra("area") != null) {
            filter = "area";
            search = intent.getStringExtra("area");
            Log.i("areaArea", search);
            fetchJsonCity();

        } else if (intent.getStringExtra("cost") != null) {
            filter = "costLevel";
            search = intent.getStringExtra("cost");
            fetchJsonCity();

        } else {
            fetchJson();
        }

        imageView_map = (ImageView) findViewById(R.id.map);
        imageView_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_map = new Intent(SearchResult.this, Places.class);
                intent_map.putExtra("pointLan", pointLat);
                intent_map.putExtra("pointLon", pointLon);
                intent_map.putExtra("barberShopName", name);
                startActivity(intent_map);

            }
        });
    }

    private String URL = "http://piraa.ir/barbershop/api/barbershop/?format=json";
    private int k = 0;

    private void fetchJsonCity() {

        final JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getString(filter).equals(search)) {

                            Item item = new Item();

                            item.setName(object.getString("name"));
                            item.setAddress(object.getString("city") + " | " + object.getString("area"));
                            item.setShortName(object.getString("shortName"));
                            item.setFullAddress(object.getString("address"));
                            item.setPhone(object.getString("phoneNumber"));
                            item.setId(object.getInt("id"));
                            item.setLat(object.getDouble("pointLat"));
                            item.setLon(object.getDouble("pointLon"));

                            pointLat[k] = object.getDouble("pointLat");
                            pointLon[k] = object.getDouble("pointLon");
                            name[k] = object.getString("name");
                            k++;

                            itemList.add(item);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
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

    private void fetchJson() {

        final JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        Item item = new Item();

                        item.setName(object.getString("name"));
                        item.setAddress(object.getString("city") + " | " + object.getString("area"));
                        item.setShortName(object.getString("shortName"));
                        item.setFullAddress(object.getString("address"));
                        item.setPhone(object.getString("phoneNumber"));
                        item.setId(object.getInt("id"));
                        item.setLat(object.getDouble("pointLat"));
                        item.setLon(object.getDouble("pointLon"));

                        pointLat[k] = object.getDouble("pointLat");
                        pointLon[k] = object.getDouble("pointLon");
                        name[k] = object.getString("name");
                        k++;

                        itemList.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
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

    private String URL_BARBER = "http://piraa.ir/barbershop/api/barber/?format=json";
    private int id = 0, id_array[] = new int[500], z = 0;

    private void fetchJsonBarber() {

        final JsonArrayRequest req = new JsonArrayRequest(URL_BARBER, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getString(filter).equals(search)) {

                            id = object.getInt("barbershopID");
                            Log.i("IdBarber", id+"");
                            id_array[z] = id;
                            z++;

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
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

    private void fetchJsonId() {

        final JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int j = 0; j < z; j++) {

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject object = response.getJSONObject(i);

                            if (object.getString("id").equals(id_array[j]+"")) {

                                Item item = new Item();

                                item.setName(object.getString("name"));
                                item.setAddress(object.getString("city") + " | " + object.getString("area"));
                                item.setShortName(object.getString("shortName"));
                                item.setFullAddress(object.getString("address"));
                                item.setPhone(object.getString("phoneNumber"));
                                item.setId(object.getInt("id"));
                                item.setLat(object.getDouble("pointLat"));
                                item.setLon(object.getDouble("pointLon"));

                                pointLat[k] = object.getDouble("pointLat");
                                pointLon[k] = object.getDouble("pointLon");
                                name[k] = object.getString("name");
                                k++;

                                itemList.add(item);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
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

}