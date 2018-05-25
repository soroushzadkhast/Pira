package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Places extends AppCompatActivity{

    private GoogleMap googleMap;
    private double latitude[] = new double[500], longitude[] = new double[500];
    private String name[] = new String[500];
    private ImageView imageView_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places);

        try {
            initilizeMap();
        } catch (Exception e){
            e.printStackTrace();
        }

        imageView_back = (ImageView) findViewById(R.id.image_ic_back);
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*double latitude = 35.744720;
        double longitude = 51.375469;*/

        Intent intent = getIntent();
        latitude = intent.getDoubleArrayExtra("pointLan");
        longitude = intent.getDoubleArrayExtra("pointLon");
        name = intent.getStringArrayExtra("barberShopName");

        MapsInitializer.initialize(getApplicationContext());

        barberPosition(latitude, longitude, name);
        Log.i("lenght", latitude.length+"");
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void barberPosition(double latitude[], double longitude[], String name[]){
        for (int i = 0; i < latitude.length; i++) {
            if (latitude[i] != 0 && longitude[i] != 0) {

                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude[i], longitude[i])).title("\u200e"+ "آرایشگاه " +name[i]);
                Log.i("latitude",  "---->" + marker.getTitle());
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.annotation));

                googleMap.addMarker(marker);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude[i], longitude[i]), 12.0f));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
}
