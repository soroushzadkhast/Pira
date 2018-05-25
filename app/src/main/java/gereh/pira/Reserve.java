package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Reserve extends AppCompatActivity {

    private String workHour;
    private Button btnTag;
    private ImageView btn_left, btn_right;
    private TextView textView;
    private int s = 0, t = 0, z = 0, id = 0, h = 0;
    private String URL = "http://piraa.ir/barbershop/api/reserve/?format=json";
    private String date_query, date_day;
    private Button[] btn_array = new Button[48];
    private Map<String, String> params;
    private JSONObject object;
    private int sep, f, k = 0;
    private int j_array[] = new int[48];
    private LinearLayout layout;
    private String[] separated;
    private String dateUrl, res_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserve);

        object = new JSONObject();
        final Intent intent_get = getIntent();

        /*params = new HashMap<String, String>();

        params.put("username", "admin");
        params.put("password", "admin");*/

       /* params.put("userID", 9);
        params.put("barberID", 1);
        params.put("barberName", "نیما قیچی طلا");
        params.put("barbershopID", 2);
        params.put("barbershopName", "نیما");
        params.put("isManual", false);
        params.put("reserveHour", "21:30");
        params.put("reserveDate", "1394/06/06");
        params.put("weekDay", "جمعه");
        params.put("status", "A");*/

        //Log.i("Jsonobject", params+"");


        Button btn_save = (Button) findViewById(R.id.save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

        layout = (LinearLayout) findViewById(R.id.linearLayout);

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        DisplayMetrics dp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dp);

        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = scrollView.getMeasuredWidth();
                Log.i("scrollview", width + "");
            }
        });

        Intent intent = getIntent();
        workHour = intent.getStringExtra("barber_workHour");
        id = intent.getIntExtra("barber_id", 0);
        Log.i("idIdIdId", id+"");

        String[] split = workHour.split(",");
        separated = new String[split.length];

        for (int i = 0; i < split.length; i++) {
            separated[i] = convertToPersianDigits(split[i]);
            Log.i("workHour_separated", separated[i]);
            Log.i("workHour", separated.length + "");
        }

        f = (separated.length * 2) + 4;

        if ((separated.length / 2) % 4 == 0) {
            sep = separated.length / 2;
        } else {
            sep = (separated.length / 2) + 1;
        }

        btnGenerate();

        textView = (TextView) findViewById(R.id.text_date);
        Utilities utilities = new Utilities();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 0);
        textView.setText(utilities.getCurrentShamsidate(c.getTime()));
        date_day = utilities.getCurrentday(c.getTime());
        date_query = utilities.getCurrentdate(c.getTime());
        dateUrl = utilities.getCurrentdate(c.getTime());
        fetchJson();

        btn_left = (ImageView) findViewById(R.id.left_btn);
        btn_right = (ImageView) findViewById(R.id.right_btn);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (t > 0) {
                    t--;

                    Utilities utilities = new Utilities();
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.DATE, t);
                    textView.setText(utilities.getCurrentShamsidate(c.getTime()));
                    date_day = utilities.getCurrentday(c.getTime());
                    date_query = utilities.getCurrentdate(c.getTime());
                    dateUrl = utilities.getCurrentdate(c.getTime());
                    layout.removeAllViews();

                    f = (separated.length * 2) + 4;

                    if ((separated.length / 2) % 4 == 0) {
                        sep = separated.length / 2;
                    } else {
                        sep = (separated.length / 2) + 1;
                    }

                    k = 0;
                    z = 0;
                    btnGenerate();
                    fetchJson();


                    try {

                        object.put("userID", 9);
                        object.put("barberID", intent_get.getIntExtra("barber_id", 0));
                        object.put("barberName", intent_get.getStringExtra("barber_name"));
                        object.put("barbershopID", intent_get.getIntExtra("barbershop_id", 0));
                        object.put("barbershopName", intent_get.getStringExtra("barbershop_name"));
                        object.put("isManual", false);
                        //object.put("reserveHour", res_time);
                        object.put("reserveDate", dateUrl);
                        object.put("weekDay", date_day);
                        object.put("status", "W");

                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    Log.i("object_json", object+"");

                }
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                t++;
                Utilities utilities = new Utilities();
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, t);
                textView.setText(utilities.getCurrentShamsidate(c.getTime()));
                date_day = utilities.getCurrentday(c.getTime());
                date_query = utilities.getCurrentdate(c.getTime());
                dateUrl = utilities.getCurrentdate(c.getTime());
                layout.removeAllViews();

                f = (separated.length * 2) + 4;

                if ((separated.length / 2) % 4 == 0) {
                    sep = separated.length / 2;
                } else {
                    sep = (separated.length / 2) + 1;
                }

                k = 0;
                z = 0;
                btnGenerate();
                fetchJson();


                try {

                    object.put("userID", 9);
                    object.put("barberID", intent_get.getIntExtra("barber_id", 0));
                    object.put("barberName", intent_get.getStringExtra("barber_name"));
                    object.put("barbershopID", intent_get.getIntExtra("barbershop_id", 0));
                    object.put("barbershopName", intent_get.getStringExtra("barbershop_name"));
                    object.put("isManual", false);
                    //object.put("reserveHour", res_time);
                    object.put("reserveDate", dateUrl);
                    object.put("weekDay", date_day);
                    object.put("status", "W");

                } catch (JSONException e){
                    e.printStackTrace();
                }

                Log.i("object_json", object+"");

            }
        });


        try {

            object.put("userID", 9);
            object.put("barberID", intent_get.getIntExtra("barber_id", 0));
            object.put("barberName", intent_get.getStringExtra("barber_name"));
            object.put("barbershopID", intent_get.getIntExtra("barbershop_id", 0));
            object.put("barbershopName", intent_get.getStringExtra("barbershop_name"));
            object.put("isManual", false);
            object.put("reserveHour", "10:30");
            object.put("reserveDate", dateUrl);
            object.put("weekDay", date_day);
            object.put("status", "W");

        } catch (JSONException e){
            e.printStackTrace();
        }

        Log.i("object_json", object+"");

        /*for (int i = 0; i < sep; i++) {
            LinearLayout row = new LinearLayout(this);
            *//*RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            row.setLayoutParams(ll_params);*//*

            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);


            params.gravity = Gravity.CENTER;
            *//*LinearLayout.LayoutParams params_f = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.btn_width),
                    (int) getResources().getDimension(R.dimen.btn_height), 1.0f);*//*

            //params.setMargins(7, (int) getResources().getDimension(R.dimen.btn_margin_right), 7, 0);
            //params_f.setMargins((int) getResources().getDimension(R.dimen.btn_margin_left), (int) getResources().getDimension(R.dimen.btn_margin_right), 0, 0);

            int n;
            if (f - 4 >= 4) {
                n = 4;
                f = f - 4;
            } else {
                n = f - 4;
            }


            for (int j = 0; j < n; j++) {

                *//*if (j % 4 == 0){
                    params.setMargins((int) getResources().getDimension(R.dimen.btn_margin_right), (int) getResources().getDimension(R.dimen.btn_margin_top), 0, 0);
                } else if (j % 4 == 3) {
                    params.setMargins(0, (int) getResources().getDimension(R.dimen.btn_margin_top), 0, 0);
                } else {
                    params.setMargins(0, (int) getResources().getDimension(R.dimen.btn_margin_top), 0, 0);
                }

                if (i == 0){
                    params.setMargins(0, 0, 0, 0);
                }*//*
                if (i == 0 && j % 4 != 3) {
                    params.setMargins(10, 5, 10, 10);
                }
                if (i != 0) {
                    if (j % 4 == 3) {
                        params.setMargins(10, 20, 0, 20);
                    } else {
                        params.setMargins(10, 20, 10, 20);
                    }
                }

                btnTag = new Button(this);
                *//*if (j % 4 == 0){
                    btnTag.setLayoutParams(params_f);
                } else {*//*
                btnTag.setLayoutParams(params);
                //}
                btnTag.setBackgroundResource(R.drawable.reserve_btn_shape);

                if (j % 2 == 0) {
                    Log.i("KkKk", k + "--->" + j);
                    btnTag.setText(separated[k]);
                } else {
                    btnTag.setText(separated[k] + ":۳۰");
                    k++;
                }

                btnTag.setId(j + 1 + (i * 4));
                row.addView(btnTag);
                btn_array[z] = btnClicked(btnTag);
                z++;

                *//*for (int l = 0; l < z; l++) {
                    if (btnTag.getText()+"" == btn_text[z]) {
                        btnTag.setBackgroundResource(R.drawable.btn_tag_shape);
                    }
                }*//*

                *//*((Button) findViewById(j + 1 + (i * 4))).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        *//**//*if (s % 2 == 0) {
                            btnTag.setBackgroundResource(R.drawable.btn_tag_shape);
                            s++;
                        } else {
                            btnTag.setBackgroundResource(R.drawable.reserve_btn_shape);
                            s++;
                        }*//**//*

                        Log.i("btnTag", btnTag.getText()+"");

                    }
                });*//*

                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        btnTag.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        Log.i("btn", btnTag.getText() + "");
                    }
                });
            }
            layout.addView(row);
        }*/
    }


    private void btnGenerate(){
        for (int i = 0; i < sep; i++) {
            LinearLayout row = new LinearLayout(this);

            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

            params.gravity = Gravity.CENTER;

            int n;
            if (f - 4 >= 4) {
                n = 4;
                f = f - 4;
            } else {
                n = f - 4;
            }


            for (int j = 0; j < n; j++) {

                if (i == 0 && j % 4 != 3) {
                    params.setMargins(10, 5, 10, 10);
                }
                if (i != 0) {
                    if (j % 4 == 3) {
                        params.setMargins(10, 20, 0, 20);
                    } else {
                        params.setMargins(10, 20, 10, 20);
                    }
                }

                btnTag = new Button(this);
                btnTag.setLayoutParams(params);

                btnTag.setBackgroundResource(R.drawable.reserve_btn_shape);

                if (j % 2 == 0) {
                    Log.i("KkKk", k + "--->" + j);
                    btnTag.setText(separated[k]);
                } else {
                    btnTag.setText(separated[k] + ":۳۰");
                    k++;
                }

                btnTag.setId(j + 1 + (i * 4));
                row.addView(btnTag);
                btn_array[z] = btnClicked(btnTag);
                z++;
            }
            layout.addView(row);
        }
    }

    private Button btnClicked(final Button btn) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //for (int i = 0; i < h; i++) {
                    Log.i("enter", "enterEnter");
                    //Log.i("btnBtn", btn.getText().toString() + "---" + btn_array[j_array[i]].getText().toString());
                    //if (btn.getText().toString().equals(btn_array[j_array[i]].getText().toString())) {
                        if (s % 2 == 0) {
                            btn.setBackgroundResource(R.drawable.btn_tag_shape);
                            res_time = btn.getText().toString();
                            try {
                                object.put("reserveHour", res_time);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.i("json_object", object+"");
                            s++;
                        } else {
                            btn.setBackgroundResource(R.drawable.reserve_btn_shape);
                            s++;
                        }
                    //}
                //}
            }
        });
        return btn;
    }

    public static String convertToPersianDigits(String value) {
        String newValue = value.replace("1", "۱").replace("2", "۲").replace("3", "۳").replace("4", "۴").replace("5", "۵")
                .replace("6", "۶").replace("7", "۷").replace("8", "۸").replace("9", "۹").replace("0", "۰");

        return newValue;
    }

    public static String convertToEnglishDigits(String value) {
        String newValue = value.replace("۱", "1").replace("۲", "2").replace("۳", "3").replace("۴", "4").replace("۵", "5")
                .replace("۶", "6").replace("۷", "7").replace("۸", "8").replace("۹", "9").replace("۰", "0");

        return newValue;
    }


    private String[] btn_text = new String[48];

    private void fetchJson() {

        JsonArrayRequest req = new JsonArrayRequest(URL + "&barberID=" + id + "&reserveDate=" + dateUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    Log.i("URL_RESPONSE", "TRUE");

                    try {

                        JSONObject object = response.getJSONObject(i);

                        /*for (int j = 0; j < z; j++) {*/

                        btn_text[i] = object.getString("reserveHour");
                        Log.i("btn_text", object.getString("reserveHour"));

                        Utilities utilities = new Utilities();
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.DATE, t);
                        Log.i("object.getString", dateUrl);
                        //if (object.getString("reserveDate").equals("1394/06/06")) {
                            for (int j = 0; j < z; j++) {

                                if (convertToPersianDigits(btn_text[i]).equals(btn_array[j].getText().toString())) {

                                    btn_array[j].setBackgroundResource(R.drawable.btn_shape_reserved);
                                    btn_array[j].setClickable(false);
                                    j_array[h] = j;
                                    h++;

                                }

                            }
                        //}
                            /*if (btn_array[j].getText()+"" == object.getString("reserveHour"))
                                Log.i("objectString", object.getString("reserveHour"));
                        }*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("catch", "error");
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("objectError", "ENTERenter");
                if (error.getMessage() != null)
                    Log.i("Error_Fetch_Json", error.getMessage());
            }
        });
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    private String URL_POST = "http://piraa.ir/barbershop/api/reserve";

    private void sendData() {

        JsonObjectRequest req = new JsonObjectRequest(URL, object,
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

            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("username", "admin");
                params.put("password", "admin");

                *//*params.put("userID", 9);
                params.put("barberID", 1);
                params.put("barberName", "نیما قیچی طلا");
                params.put("barbershopID", 2);
                params.put("barbershopName", "نیما");
                params.put("isManual", false);
                params.put("reserveHour", "21:30");
                params.put("reserveDate", "1394/06/06");
                params.put("weekDay", "جمعه");
                params.put("status", "A");*//*

                Log.i("username", "username");

                return params;
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String auth = "Basic "
                        + Base64.encodeToString(("admin" + ":" + "admin").getBytes(),
                        Base64.NO_WRAP);

                headers.put("Authorization", auth);

                Log.i("username", "password");

                return headers;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(req);
    }
}
