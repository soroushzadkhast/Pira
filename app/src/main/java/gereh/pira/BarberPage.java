package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gereh.pira.Adapter.CustomBarberListAdapter;
import gereh.pira.Items.BarberItem;

public class BarberPage extends AppCompatActivity {

    ImageView img_back;
    private ListView listView;
    private CustomBarberListAdapter adapter;
    private List<BarberItem> itemList = new ArrayList<BarberItem>();
    private int barbershop_id;

    private TextView text_name;
    private TextView text_address;
    private android.widget.TextView image_text;
    private TextView text_fulladdress;
    private TextView text_phone;
    private JSONObject object;
    private ImageView img_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barber);

        object = new JSONObject();

        img_back= (ImageView) findViewById(R.id.ic_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.list_barber);
        adapter = new CustomBarberListAdapter(this, itemList);
        listView.setAdapter(adapter);

        text_name = (TextView) findViewById(R.id.name);
        text_address = (TextView) findViewById(R.id.addres);
        image_text = (android.widget.TextView) findViewById(R.id.image_text);
        text_fulladdress = (TextView) findViewById(R.id.address);
        text_phone = (TextView) findViewById(R.id.phone);


        Intent intent = getIntent();

        final String barbershop_name = intent.getStringExtra("name_barbershop");
        String barbershop_address = intent.getStringExtra("address_barbershop");
        String barbershop_short = intent.getStringExtra("shortname_barbershop");
        String barbershop_fulladdress = intent.getStringExtra("fulladdress_barbershop");
        String barbershop_phone = intent.getStringExtra("phone_barbershop");
        barbershop_id = intent.getIntExtra("id_barbershop", 0);

        text_name.setText("آرایشگاه " + barbershop_name);
        text_address.setText(barbershop_address);
        image_text.setText(barbershop_short);
        text_fulladdress.setText(barbershop_fulladdress);
        text_phone.setText(barbershop_phone);

        fetchJson();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent_get = new Intent(BarberPage.this, Reserve.class);
                BarberItem items = itemList.get(i);

                intent_get.putExtra("barber_workHour", items.getWorkHour());
                intent_get.putExtra("barber_id", items.getId());
                intent_get.putExtra("barber_name", items.getName());
                intent_get.putExtra("barbershop_id", barbershop_id);
                intent_get.putExtra("barbershop_name", barbershop_name);

                startActivity(intent_get);
            }
        });


        try {

            object.put("userID", 8);
            object.put("barbershopID", barbershop_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        img_add = (ImageView) findViewById(R.id.image_ic_fav);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("object_shop", object+"");

                addFav();
            }
        });

    }

    private String URL = "http://piraa.ir/barbershop/api/barber/?format=json";

    private void fetchJson() {

        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++){

                    try {

                        JSONObject object = response.getJSONObject(i);

                        if (object.getInt("barbershopID") == barbershop_id) {

                            BarberItem item = new BarberItem();

                            item.setId(object.getInt("id"));
                            item.setName(object.getString("name"));
                            item.setShortName(object.getString("shortName"));
                            item.setWorkHour(object.getString("workHours"));

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
                if (error.getMessage() != null)
                Log.i("Error_Fetch_Json", error.getMessage());
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    private String URL_FAV = "http://piraa.ir/barbershop/api/favorite/";

    private void addFav() {

        JsonObjectRequest req = new JsonObjectRequest(URL_FAV, object,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("Response_Send", response.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Error", error.getMessage()+"");

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String auth = "Basic "
                        + Base64.encodeToString(("admin" + ":" + "admin").getBytes(),
                        Base64.NO_WRAP);

                headers.put("Authorization", auth);

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(req);
    }
}
