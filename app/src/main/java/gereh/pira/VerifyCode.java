package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifyCode extends AppCompatActivity {

    private Button btn_continue;
    private String url = "http://pastaa.ir/api/r-comment/?format=json";
    private static final String TAG = VerifyCode.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifycode);

        Intent intent_get = getIntent();
        intent_get.getIntExtra("verifyNo", 0000);
        Toast.makeText(getApplicationContext(), intent_get.getIntExtra("verifyNo", 0000)+"", Toast.LENGTH_SHORT).show();

        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyCode.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
