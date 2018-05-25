package gereh.pira;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.TextView;

import gereh.pira.ViewPagerAdapter.ViewPagerAdapter;
import gereh.pira.ViewPagerAdapter.ViewPagerAreaAdapter;

public class Filter extends Fragment {

    private ImageView btn_close;
    private RelativeLayout btn_city_left, btn_city_right, btn_area_left, btn_area_right, btn_cost_left, btn_cost_right;
    private Button btn_filter;

    private String city[] = new String[]{
            "مهم نیست",
            "تهران"
    };

    private String area[] = new String[]{
            "مهم نیست",
            "۱",
            "۲",
            "۳",
            "۴",
            "۵",
            "۶",
            "۷",
            "۸",
            "۹",
            "۱۰",
            "۱۱",
            "۱۲",
            "۱۳",
            "۱۴",
            "۱۵",
            "۱۶",
            "۱۷",
            "۱۸",
            "۱۹",
            "۲۰",
            "۲۱",
            "۲۲"
    };

    private String cost[] = new String[]{
            "مهم نیست",
            "پایین",
            "متوسط",
            "بالا"
    };

    private ViewPager viewPager, pager_area, pager_cost;

    private ViewPagerAdapter adapter;
    private ViewPagerAreaAdapter area_adapter;
    private ViewPagerAreaAdapter cost_adapter;
    private EditText barberShop_edt, barber_edt;

    private RelativeLayout layout_city_left;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.filter, container, false);

        barberShop_edt = (EditText) rootView.findViewById(R.id.arayesh_text);
        barber_edt = (EditText) rootView.findViewById(R.id.name_text);

        btn_filter = (Button) rootView.findViewById(R.id.filter);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        pager_area = (ViewPager) rootView.findViewById(R.id.pager_area);
        pager_cost = (ViewPager) rootView.findViewById(R.id.pager_cost);

        adapter = new ViewPagerAdapter(getActivity(), city);
        area_adapter = new ViewPagerAreaAdapter(getActivity(), area);
        cost_adapter = new ViewPagerAreaAdapter(getActivity(), cost);

        viewPager.setAdapter(adapter);
        pager_area.setAdapter(area_adapter);
        pager_cost.setAdapter(cost_adapter);

        btn_city_left = (RelativeLayout) rootView.findViewById(R.id.btn_city_left);
        btn_city_right = (RelativeLayout) rootView.findViewById(R.id.btn_city_right);

        btn_area_left = (RelativeLayout) rootView.findViewById(R.id.btn_area_left);
        btn_area_right = (RelativeLayout) rootView.findViewById(R.id.btn_area_right);

        btn_cost_left = (RelativeLayout) rootView.findViewById(R.id.btn_cost_left);
        btn_cost_right = (RelativeLayout) rootView.findViewById(R.id.btn_cost_right);

        btn_city_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                } else {
                    viewPager.setCurrentItem(city.length - 1, true);
                }

            }
        });

        btn_city_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() != city.length - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                } else {
                    viewPager.setCurrentItem(0, true);
                }
            }
        });


        btn_area_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager_area.getCurrentItem() != 0) {
                    pager_area.setCurrentItem(pager_area.getCurrentItem() - 1, true);
                } else {
                    pager_area.setCurrentItem(area.length - 1, true);
                }

            }
        });

        btn_area_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager_area.getCurrentItem() != area.length - 1) {
                    pager_area.setCurrentItem(pager_area.getCurrentItem() + 1, true);
                } else {
                    pager_area.setCurrentItem(0, true);
                }
            }
        });

        btn_cost_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager_cost.getCurrentItem() != 0) {
                    pager_cost.setCurrentItem(pager_cost.getCurrentItem() - 1, true);
                } else {
                    pager_cost.setCurrentItem(cost.length - 1, true);
                }
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_cost_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pager_cost.getCurrentItem() != cost.length - 1) {
                    pager_cost.setCurrentItem(pager_cost.getCurrentItem() + 1, true);
                } else {
                    pager_cost.setCurrentItem(0, true);
                }
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchResult.class);

                String barbershop, barber, city_search, area_search, cost_search, item;
                String str = barberShop_edt.getText().toString().replace(" ","");

                if (str.length() != 0){

                    barbershop = barberShop_edt.getText().toString();
                    intent.putExtra("barbershop", barbershop);
                    startActivity(intent);

                } else {

                    str = barber_edt.getText().toString().replace(" ", "");
                    if (str.length() != 0){

                        barber = barber_edt.getText().toString();
                        intent.putExtra("barber", barber);
                        startActivity(intent);

                    } else {

                        if (!city[viewPager.getCurrentItem()].equals("مهم نیست")){

                            city_search = city[viewPager.getCurrentItem()];
                            intent.putExtra("city", city_search);
                            startActivity(intent);

                        } else {

                            if (!area[pager_area.getCurrentItem()].equals("مهم نیست")){

                                area_search = area[pager_area.getCurrentItem()];
                                intent.putExtra("area", area_search);
                                startActivity(intent);

                            } else {

                                if (!cost[pager_cost.getCurrentItem()].equals("مهم نیست")){

                                    cost_search = cost[pager_cost.getCurrentItem()];
                                    switch (cost_search){
                                        case "پایین":
                                            intent.putExtra("cost", "L");
                                            break;
                                        case "متوسط":
                                            intent.putExtra("cost", "M");
                                            break;
                                        case "بالا":
                                            intent.putExtra("cost", "H");
                                            break;
                                    }
                                    startActivity(intent);

                                } else {

                                    item = "";
                                    intent.putExtra("item", item);
                                    startActivity(intent);
                                }

                            }

                        }

                    }

                }

                /*intent.putExtra("city", city[viewPager.getCurrentItem()]);
                intent.putExtra("area", area[pager_area.getCurrentItem()]);
                intent.putExtra("costLevel", cost[pager_cost.getCurrentItem()]);*/

            }
        });

        btn_close = (ImageView) rootView.findViewById(R.id.image_ic_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_out, R.anim.slide_out);
                ft.remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.frame)).commit();
            }
        });

       barberShop_edt.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

           }

           @Override
           public void afterTextChanged(Editable editable) {

               String str = barberShop_edt.getText().toString().replace(" ","");

               if (str.length() != 0) {

                   btn_cost_left.setEnabled(false);
                   btn_cost_right.setEnabled(false);
                   btn_city_right.setEnabled(false);
                   btn_city_left.setEnabled(false);
                   btn_area_left.setEnabled(false);
                   btn_area_right.setEnabled(false);
                   barber_edt.setEnabled(false);
                   pager_area.setEnabled(false);
                   pager_cost.setEnabled(false);


               } else {

                   btn_cost_left.setEnabled(true);
                   btn_cost_right.setEnabled(true);
                   btn_city_right.setEnabled(true);
                   btn_cost_left.setEnabled(true);
                   btn_area_left.setEnabled(true);
                   btn_area_right.setEnabled(true);
                   barber_edt.setEnabled(true);

               }
           }
       });

        barberShop_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (barberShop_edt.getText().toString() != ""){

                    viewPager.setEnabled(false);

                }

                return false;
            }
        });

        return rootView;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_out, R.anim.slide_out);
                ft.remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.frame)).commit();*/
                Toast.makeText(getActivity().getApplicationContext(), "back pressed", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }
}

