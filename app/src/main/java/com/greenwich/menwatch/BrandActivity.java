package com.greenwich.menwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.greenwich.adapter.BrandProductAdapter;
import com.greenwich.model.Product;

import java.util.ArrayList;

public class BrandActivity extends AppCompatActivity {
    Toolbar tbBrand;
    ListView lvBrandProduct;
    BrandProductAdapter brandProductAdapter;
    ArrayList<Product> arrProduct;
    int idBrand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        addControls();
        getBrandId();
        addToolbarBrandEvents();
    }

    private void addToolbarBrandEvents() {

    }

    private void getBrandId() {
        idBrand = getIntent().getIntExtra("idBrand",-1);
        Log.d("idbrand",idBrand+"" );
    }

    private void addControls() {
        tbBrand = findViewById(R.id.tbBrand);
        lvBrandProduct = findViewById(R.id.lvBrandProduct);
        arrProduct = new ArrayList<>();
        brandProductAdapter = new BrandProductAdapter(getApplicationContext(), arrProduct);
        lvBrandProduct.setAdapter(brandProductAdapter);
    }
}
