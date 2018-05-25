package gereh.pira;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class PhoneNumber extends AppCompatActivity{

    private Button btn_continue, btn_enterBarber;
    private EditText edt_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonenumber);

        btn_continue = (Button) findViewById(R.id.btn_continue);
        btn_enterBarber = (Button) findViewById(R.id.enter_barber);
        edt_number = (EditText) findViewById(R.id.edt_number);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_number = edt_number.getText().toString();
                Log.i("str_num", str_number);
                if (str_number.length() > 3) {
                    int rand_verify;
                    Random generate_verify = new Random();
                    rand_verify = generate_verify.nextInt(10000 - 1000) + 1000;
                    Log.i("random", rand_verify+"");

                    Intent intent = new Intent(PhoneNumber.this, VerifyCode.class);
                    intent.putExtra("verifyNo", rand_verify);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا شماره تلفن خود را وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_enterBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNumber.this, BarberEnter.class);
                startActivity(intent);
            }
        });

    }
}
