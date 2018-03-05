package com.greenwich.menwatch;

import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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
import com.greenwich.adapter.LatestProductAdapter;
import com.greenwich.adapter.StyleAdapter;
import com.greenwich.model.Brand;
import com.greenwich.model.Cart;
import com.greenwich.model.Product;
import com.greenwich.model.Style;
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
    ListView lvBrand, lvStyle;


    ArrayList<Brand> arrBrand;
    BrandAdapter brandAdapter;
    int brandId = 0;
    String brandName = "";

    ArrayList<Style> arrStyle;
    StyleAdapter styleAdapter;
    int styleId = 0;
    String styleName = "";

    ArrayList<Product> arrProduct;
    LatestProductAdapter latestProductAdapter;
    int productId = 0;
    int productIdStyle = 0;
    int productIdBrand = 0;
    String productName = "";
    Double productPrice = 0.0;
    int productQuantity = 0;
    String productDescription="";
    String productImage1 = "";
    String productImage2 = "";
    String productImage3 = "";
    String productMT ="";
    String productCS ="";
    String productSM ="";
    String productWR ="";

    public static ArrayList<Cart> arrCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        if (Connection.checkNetworkConnection(getApplicationContext())) {
            addToolbarEvents();
            addViewFlipperEvents();

            //add brand name to slide menu
            getBrandData();
            //add style name to slide menu
            getStyleData();
            //add style name to slide menu
            getLatestProductData();
            //set event on Brand and Style
            setBrandItemEvent();
            //set event on Brand and Style
            setStyleItemEvent();
//            addRecyclerViewLatestProductEvents();
        } else {
            Toast.makeText(this, "Please, check your connection", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnCartIcon:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    //    private void addRecyclerViewLatestProductEvents() {
//        rvLatestProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }


    private void setStyleItemEvent() {
        lvStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, StyleActivity.class);
                            intent.putExtra("idStyle", arrStyle.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
//                        if (Connection.checkNetworkConnection(getApplicationContext())) {
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
//                        }
//                        dlHome.closeDrawer(GravityCompat.START);
//                        break;
                    case 1:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, StyleActivity.class);
                            intent.putExtra("idStyle", arrStyle.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, StyleActivity.class);
                            intent.putExtra("idStyle", arrStyle.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, StyleActivity.class);
                            intent.putExtra("idStyle", arrStyle.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, StyleActivity.class);
                            intent.putExtra("idStyle", arrStyle.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void setBrandItemEvent() {
        lvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            intent.putExtra("idBrand", arrBrand.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
//                        if (Connection.checkNetworkConnection(getApplicationContext())) {
//                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
//                        }
//                        dlHome.closeDrawer(GravityCompat.START);
//                        break;
                    case 1:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            intent.putExtra("idBrand", arrBrand.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            intent.putExtra("idBrand", arrBrand.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            intent.putExtra("idBrand", arrBrand.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (Connection.checkNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, BrandActivity.class);
                            intent.putExtra("idBrand", arrBrand.get(i).getId());
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Please! Check your connection", Toast.LENGTH_SHORT).show();
                        }
                        dlHome.closeDrawer(GravityCompat.START);
                        break;
                }

            }
        });
    }

    private void getLatestProductData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, MenwatchServer.linkLatestProduct, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                productId = jsonObject.getInt("id");
                                productIdStyle = jsonObject.getInt("idStyle");
                                productIdBrand = jsonObject.getInt("idBrand");
                                productName = jsonObject.getString("name");
                                productPrice = Double.parseDouble(jsonObject.getString("price"));
                                productQuantity = jsonObject.getInt("quantity");
                                productDescription = jsonObject.getString("description");
                                //get image jsonb
                                JSONObject jsonObjectImage = jsonObject.getJSONObject("image");
                                productImage1  = MenwatchServer.linkImage + jsonObjectImage.getString("img1");
                                productImage2  = MenwatchServer.linkImage + jsonObjectImage.getString("img2");
                                productImage3  = MenwatchServer.linkImage + jsonObjectImage.getString("img3");
//get image jsonb
                                JSONObject jsonObjectDetails = jsonObject.getJSONObject("details");
                                productCS  = jsonObjectDetails.getString("cs");
                                productMT  = jsonObjectDetails.getString("mt");
                                productSM= jsonObjectDetails.getString("sm");
                                productWR  = jsonObjectDetails.getString("wr");

                                arrProduct.add(new Product(productId, productIdStyle, productIdBrand,
                                        productName, productPrice, productQuantity, productDescription,
                                        productImage1, productImage2, productImage3,
                                        productMT, productCS, productSM, productWR ));
                                latestProductAdapter.notifyDataSetChanged();
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

    private void getStyleData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, MenwatchServer.linkStyle, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                styleId = jsonObject.getInt("id");
                                styleName = jsonObject.getString("stylename");
                                arrStyle.add(new Style(styleId, styleName));
                                styleAdapter.notifyDataSetChanged();
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
                                brandId = jsonObject.getInt("id");
                                brandName = jsonObject.getString("brandname");
                                arrBrand.add(new Brand(brandId, brandName));
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
        vfHome.setFlipInterval(3000);
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
        lvBrand = findViewById(R.id.lvBrand);
        lvStyle = findViewById(R.id.lvStyle);
        //init Brand adapter
        arrBrand = new ArrayList<>();
//        arrBrand.add(0,new Brand(0,"Ok"));
        brandAdapter = new BrandAdapter(arrBrand, getApplicationContext());
        lvBrand.setAdapter(brandAdapter);

        //init Brand adapter
        arrStyle = new ArrayList<>();
        styleAdapter = new StyleAdapter(arrStyle, getApplicationContext());
        lvStyle.setAdapter(styleAdapter);

        //init Latest Product Adapter
        arrProduct = new ArrayList<>();
        latestProductAdapter = new LatestProductAdapter(getApplicationContext(), arrProduct);
        rvLatestProduct.setHasFixedSize(true);
        rvLatestProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rvLatestProduct.setAdapter(latestProductAdapter);

        if(arrCart!= null){

        }else{
            arrCart = new ArrayList<>();
        }
    }
}
