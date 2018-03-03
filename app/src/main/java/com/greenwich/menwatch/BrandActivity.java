package com.greenwich.menwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.greenwich.adapter.BrandProductAdapter;
import com.greenwich.model.Product;
import com.greenwich.utils.Connection;
import com.greenwich.utils.MenwatchServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrandActivity extends AppCompatActivity {
    Toolbar tbBrand;
    ListView lvBrandProduct;
    BrandProductAdapter brandProductAdapter;
    ArrayList<Product> arrProduct;
    int idBrand = 0;
    int page = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        addControls();
        if (Connection.checkNetworkConnection(getApplicationContext())) {

            addToolbarBrandEvents();
            getBrandProductData(getIntent().getIntExtra("idBrand", -1));
        } else {
            Toast.makeText(this, "Please, check your connection", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void getBrandProductData(int _page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String getDataLink = MenwatchServer.linkProductByBrand + String.valueOf(_page);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, getDataLink, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int productId = 0;
                        int productIdStyle = 0;
                        int productIdBrand = 0;
                        String productName = "";
                        Double productPrice = 0.0;
                        String productDescription = "";
                        String productImage1 = "";
                        String productImage2 = "";
                        String productImage3 = "";
                        String productMT = "";
                        String productCS = "";
                        String productSM = "";
                        String productWR = "";

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                productId = jsonObject.getInt("id");
                                productIdStyle = jsonObject.getInt("idStyle");
                                productIdBrand = jsonObject.getInt("idBrand");
                                productName = jsonObject.getString("name");
                                productPrice = Double.parseDouble(jsonObject.getString("price"));
                                productDescription = jsonObject.getString("description");
//get image jsonb
                                JSONObject jsonObjectImage = jsonObject.getJSONObject("image");
                                productImage1 = MenwatchServer.linkImage + jsonObjectImage.getString("img1");
                                productImage2 = MenwatchServer.linkImage + jsonObjectImage.getString("img2");
                                productImage3 = MenwatchServer.linkImage + jsonObjectImage.getString("img3");
//get details jsonb
                                JSONObject jsonObjectDetails = jsonObject.getJSONObject("details");
                                productCS = jsonObjectDetails.getString("cs");
                                productMT = jsonObjectDetails.getString("mt");
                                productSM = jsonObjectDetails.getString("sm");
                                productWR = jsonObjectDetails.getString("wr");

                                arrProduct.add(new Product(productId, productIdStyle, productIdBrand,
                                        productName, productPrice, productDescription,
                                        productImage1, productImage2, productImage3,
                                        productMT, productCS, productSM, productWR));
                                brandProductAdapter.notifyDataSetChanged();
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idBrand", String.valueOf(idBrand));
                return param;
            }
        };
        requestQueue.add(jsObjRequest);
    }

    private void addToolbarBrandEvents() {
        setSupportActionBar(tbBrand);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbBrand.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    private void getBrandId() {
//        idBrand = getIntent().getIntExtra("idBrand", -1);
//        Log.d("asdf ", idBrand+"");
//    }

    private void addControls() {
        tbBrand = findViewById(R.id.tbBrand);
        lvBrandProduct = findViewById(R.id.lvBrandProduct);
        arrProduct = new ArrayList<>();
        brandProductAdapter = new BrandProductAdapter(getApplicationContext(), arrProduct);
        lvBrandProduct.setAdapter(brandProductAdapter);
    }
}
