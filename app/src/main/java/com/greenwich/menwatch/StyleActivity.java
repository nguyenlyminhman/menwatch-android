package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.greenwich.adapter.StyleProductAdapter;
import com.greenwich.model.Product;
import com.greenwich.utils.Connection;
import com.greenwich.utils.MenwatchServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StyleActivity extends AppCompatActivity {
    Toolbar tbStyle;
    ListView lvStyleProduct;
    StyleProductAdapter styleProductAdapter;
    ArrayList<Product> arrProduct;
    int idStyle = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style);
        addControls();
        if (Connection.checkNetworkConnection(getApplicationContext())) {

            addToolbarBrandEvents();
            getStyleProductData(getIntent().getIntExtra("idStyle", -1));
            addListViewStyleProductEvents();
        } else {
            Toast.makeText(this, "Please, check your connection", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void addListViewStyleProductEvents() {
        lvStyleProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("product_details", arrProduct.get(i));
                startActivity(intent);
            }
        });
    }

    private void getStyleProductData(int styleId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String getDataLink = MenwatchServer.linkProductByStyle + String.valueOf(styleId);
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
                                styleProductAdapter.notifyDataSetChanged();
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
                param.put("idBrand", String.valueOf(idStyle));
                return param;
            }
        };
        requestQueue.add(jsObjRequest);
    }

    private void addToolbarBrandEvents() {
        setSupportActionBar(tbStyle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbStyle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        tbStyle = findViewById(R.id.tbStyle);
        lvStyleProduct = findViewById(R.id.lvStyleProduct);
        arrProduct = new ArrayList<>();
        styleProductAdapter = new StyleProductAdapter(getApplicationContext(), arrProduct);
        lvStyleProduct.setAdapter(styleProductAdapter);
    }
}
