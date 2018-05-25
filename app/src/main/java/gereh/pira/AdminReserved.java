package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import gereh.pira.ViewPagerAdapter.ViewPagerAdminReserveDate;

public class AdminReserved extends AppCompatActivity {

    private TextView text_date;
    private Button btnTag;
    ImageButton btn_left, btn_right;
    private String workHour;

    private ViewPager pager;
    private ViewPagerAdminReserveDate adapter;
    private String[] date = new String[366];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminreserved);

        Intent intent = getIntent();
        int start_time = intent.getIntExtra("start_time", 10);
        int end_time = intent.getIntExtra("end_time", 20);

        for (int i = 0; i <= end_time - start_time; i++) {

            if (i == 0) {

                workHour = start_time + ",";

            } else if (i == (end_time - start_time)) {

                workHour = workHour + (start_time + i);

            } else {

                workHour = workHour + (start_time + i) + ",";

            }
        }

        Log.i("workhour", workHour);

        text_date = (TextView) findViewById(R.id.text_date);
        btn_left = (ImageButton) findViewById(R.id.btn_left);
        btn_right = (ImageButton) findViewById(R.id.btn_right);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

        String[] separated = workHour.split(",");

        int k = 0, f = (separated.length * 2) + 4;
        int sep;

        if ((separated.length / 2) % 4 == 0){
            sep = separated.length / 2;
        } else {
            sep = (separated.length / 2) + 1;
        }

        for (int i = 0; i < sep; i++) {
            LinearLayout row = new LinearLayout(this);

            row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);

            params.gravity = Gravity.CENTER;

            int n;
            if (f - 4 >= 4){
                n = 4;
                Log.i("nnnn", n + "");
                f = f - 4;
            } else {
                n = f - 4;
                Log.i("nnn", n+"");
            }

            for (int j = 0; j < n; j++) {

                if (i == 0 && j % 4 != 3){
                    params.setMargins(10, 5, 10, 10);
                }
                if (i != 0){
                    if (j % 4 == 3) {
                        params.setMargins(10, 20, 0, 20);
                    }else {
                        params.setMargins(10, 20, 10, 20);
                    }
                }

                btnTag = new Button(this);

                btnTag.setLayoutParams(params);
                btnTag.setBackgroundResource(R.drawable.reserve_btn_shape);

                if (j % 2 == 0) {
                    Log.i("kkk", k+"");
                    btnTag.setText(separated[k]);
                } else {
                    btnTag.setText(separated[k] + ":30");
                    k++;

                }
                btnTag.setId(j + 1 + (i * 4));
                row.addView(btnTag);
            }
            layout.addView(row);
        }

        Calendar calendar = Calendar.getInstance();
        final JalaliCalendar jCalendar = new JalaliCalendar(TimeZone.getDefault(), Locale.ENGLISH);
        String str_date = jCalendar.get(Calendar.DATE) + "/" + jCalendar.get(Calendar.MONTH)+1 + "/" + jCalendar.get(Calendar.YEAR);
        int day = jCalendar.get(Calendar.DAY_OF_WEEK);

        switch (day){
            case 1:
                str_date = "یکشنبه"+ "\n" + str_date;
                break;
            case 2:
                str_date = "دوشنبه"+ "\n" + str_date;
                break;
            case 3:
                str_date = "سه شنبه"+ "\n" + str_date;
                break;
            case 4:
                str_date = "چهار شنبه"+ "\n" + str_date;
                break;
            case 5:
                str_date = "پنج شنبه"+ "\n" + str_date;
                break;
            case 6:
                str_date = "جمعه"+ "\n" + str_date;
                break;
            case 7:
                str_date = "شنبه"+ "\n" + str_date;
                break;
        }



        for (int i = 0; i < 366; i++) {
            str_date = jCalendar.get(Calendar.DATE) + i + "/" + jCalendar.get(Calendar.MONTH) + 1 + "/" + jCalendar.get(Calendar.YEAR);
            day = jCalendar.get(Calendar.DAY_OF_WEEK);
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd");
        SimpleDateFormat df_MM = new SimpleDateFormat("MM");
        SimpleDateFormat df_yy = new SimpleDateFormat("yyyy");

        c.add(Calendar.DATE, 1);
        String str = df.format(c.getTime());
        //int i = Integer.parseInt(str);
        //str = jCalendar.get() + "";
        Log.i("format", str+"");
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

        text_date.setText(str_date);
        /*Calendar c = Calendar.getInstance();
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jCalendar.get(Calendar.DATE, 1);
            }
        });*/

        /*pager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdminReserveDate(this, );*/

    }
}