package com.greenwich.menwatch;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.greenwich.adapter.BrandAdapter;
import com.greenwich.model.Brand;
import com.greenwich.utils.Connection;
import com.greenwich.utils.MenwatchServer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout dlHome;
    Toolbar tbHome;
    ViewFlipper vfHome;
    RecyclerView rvLatestProduct;
    NavigationView nvHome;
    ListView lvHome;

    ArrayList<Brand> arrBrand;
    BrandAdapter brandAdapter;

    int id = 0;
    String brandName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        if (Connection.checkNetworkConnection(getApplicationContext())) {
            addToolbarEvents();
            addViewFlipperEvents();
            getBrandData();
        } else {
            Toast.makeText(this, "Please, check your connection", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void getBrandData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, MenwatchServer.linkBrand, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                brandName = jsonObject.getString("brandname").trim().toString();
                                Log.d("vietlot", id + "_" + brandName);
                                arrBrand.add(new Brand(id, brandName));
                                brandAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsObjRequest);
    }

    private void getDataBrand() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(MenwatchServer.linkBrand, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            brandName = jsonObject.getString("brandname");
                            arrBrand.add(new Brand(id, brandName));
                            brandAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("loi o day: ", error.toString());
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void addViewFlipperEvents() {
        ArrayList<String> arrCarousel = new ArrayList<>();
        arrCarousel.add("https://menwatch.herokuapp.com/images/bnr-1.jpg");
        arrCarousel.add("https://menwatch.herokuapp.com/images/bnr-2.jpg");
        arrCarousel.add("https://menwatch.herokuapp.com/images/bnr-3.jpg");
        for (int i = 0; i < arrCarousel.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrCarousel.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            vfHome.addView(imageView);
        }
        vfHome.setFlipInterval(5000);
        vfHome.setAutoStart(true);

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        vfHome.setInAnimation(slide_in);
        vfHome.setOutAnimation(slide_out);
    }

    private void addToolbarEvents() {
        setSupportActionBar(tbHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbHome.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        tbHome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlHome.openDrawer(GravityCompat.START);
            }
        });

    }

    private void addControls() {
        dlHome = findViewById(R.id.dlHome);
        tbHome = findViewById(R.id.tbHome);
        vfHome = findViewById(R.id.vfHome);
        rvLatestProduct = findViewById(R.id.rvLatestProduct);
        nvHome = findViewById(R.id.nvHome);
        lvHome = findViewById(R.id.lvHome);
        //init Brand adapter
        arrBrand = new ArrayList<>();
//        arrBrand.add(0,new Brand(0,"Ok"));
        brandAdapter = new BrandAdapter(arrBrand, getApplicationContext());
        lvHome.setAdapter(brandAdapter);
        //init
    }
}
