package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AdminSetting extends AppCompatActivity {

    private Button btn_save;
    private NumberPicker numPicker_start, numPicker_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminsetting);

        btn_save = (Button) findViewById(R.id.btn_save_time);
        numPicker_start = (NumberPicker) findViewById(R.id.start_time);
        numPicker_end = (NumberPicker) findViewById(R.id.end_time);

        numPicker_start.setMinValue(10);
        numPicker_start.setMaxValue(22);

        numPicker_end.setMinValue(10);
        numPicker_end.setMaxValue(22);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (numPicker_start.getValue() < numPicker_end.getValue()) {

                    Intent intent = new Intent(AdminSetting.this, AdminReserved.class);
                    intent.putExtra("start_time", numPicker_start.getValue());
                    intent.putExtra("end_time", numPicker_end.getValue());
                    startActivity(intent);

                } else {

                    Toast toast = Toast.makeText(getApplicationContext(), "عدد پایان باید بزرگتر از شروع باشد", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0, 0);
                    toast.show();

                }
            }
        });
    }
}
