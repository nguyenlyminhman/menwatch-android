package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.greenwich.model.Cart;
import com.greenwich.model.Product;
import com.greenwich.utils.Connection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    Toolbar tbProductDetails;
    ViewFlipper vfProductDetailsImage;
    TextView txtPDName, txtPDPrice, txtPDQuantity, txtPDDetails, txtPDDescription;
    Button btnAddToCart;

    int productId = 0;
    int productIdStyle = 0;
    int productIdBrand = 0;
    String productName = "";
    Double productPrice = 0.0;
    int productQuantity = 0;
    String productDescription = "";
    String productImage1 = "";
    String productImage2 = "";
    String productImage3 = "";
    String productMT = "";
    String productCS = "";
    String productSM = "";
    String productWR = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        addProductDetailsControls();

        if (Connection.checkNetworkConnection(getApplicationContext())) {
            addToolbarProductDetailsEvents();
            getProductDetailsData();
            addToCartEvents();
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
    private void addToCartEvents() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qty = txtPDQuantity.getText().toString();

                if(qty.isEmpty()){
                    Toast.makeText(ProductDetailsActivity.this, "Please, Enter the quantity to buy.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity = Integer.parseInt(qty);
                if(quantity == 0){
                    Toast.makeText(ProductDetailsActivity.this, "Product quantity is not a zero", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MainActivity.arrCart.size() > 0) {
                    boolean exist = false;
                    for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                        if (MainActivity.arrCart.get(i).getProductId() == productId) {
                            int newQuantity = MainActivity.arrCart.get(i).getProductQuantity() + quantity;
                            if(newQuantity > productQuantity){
                                Toast.makeText(ProductDetailsActivity.this, "This product only has " + productQuantity + " items in stock.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            MainActivity.arrCart.get(i).setProductQuantity(newQuantity);
                            exist = true;
                        }
                    }
                    if (!exist) {
                        MainActivity.arrCart.add(new Cart(productId, productName, productPrice, productImage1, quantity, productQuantity));
                    }
                } else {
                    if (txtPDQuantity.getText().toString().isEmpty()) {
                        Toast.makeText(ProductDetailsActivity.this, "Please! Enter the quantity to buy.", Toast.LENGTH_LONG).show();
                        return;
                    } else if(Integer.parseInt(txtPDQuantity.getText().toString()) == 0){
                        Toast.makeText(ProductDetailsActivity.this, "Product quantity is not a zero.", Toast.LENGTH_LONG).show();
                        return;
                    }else if(Integer.parseInt(txtPDQuantity.getText().toString()) > 5) {
                        Toast.makeText(ProductDetailsActivity.this, "The maximum quantity allowed is 5 products", Toast.LENGTH_LONG).show();
                        return;
                    }else if(Integer.parseInt(txtPDQuantity.getText().toString()) > productQuantity) {
                        Toast.makeText(ProductDetailsActivity.this, "This product only has " + productQuantity + " items.", Toast.LENGTH_LONG).show();
                        return;
                    }else {
                        MainActivity.arrCart.add(new Cart(productId, productName, productPrice, productImage1, quantity, productQuantity));
                    }
                }
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProductDetailsData() {

        Product product = (Product) getIntent().getSerializableExtra("product_details");
        productId = product.getId();
        productIdStyle = product.getIdStyle();
        productIdBrand = product.getIdBrand();
        productName = product.getName();
        productPrice = product.getPrice();
        productQuantity = product.getQuantity();
        productDescription = product.getDescription();
        productImage1 = product.getImg1();
        productImage2 = product.getImg2();
        productImage3 = product.getImg3();
        productMT = product.getMt();
        productCS = product.getCs();
        productSM = product.getSm();
        productWR = product.getWr();

        ArrayList<String> arrCarousel = new ArrayList<>();
        arrCarousel.add(productImage1);
        arrCarousel.add(productImage2);
        arrCarousel.add(productImage3);
        for (int i = 0; i < arrCarousel.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrCarousel.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            vfProductDetailsImage.addView(imageView);
        }
        vfProductDetailsImage.setFlipInterval(4000);
        vfProductDetailsImage.setAutoStart(true);

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        vfProductDetailsImage.setInAnimation(slide_in);
        vfProductDetailsImage.setOutAnimation(slide_out);

        txtPDName.setText(productName);

        txtPDPrice.setText("$ " + productPrice);

        txtPDDetails.setText("Movement Type: " + productMT +
                "\n\nCase Size: " + productCS +
                "\n\nStrap Material: " + productSM +
                "\n\nWater Resistant: " + productWR);
        txtPDDescription.setText(productDescription);

    }

    private void addToolbarProductDetailsEvents() {
        setSupportActionBar(tbProductDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbProductDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addProductDetailsControls() {
        tbProductDetails = findViewById(R.id.tbProductDetails);
        vfProductDetailsImage = findViewById(R.id.vfProductDetailsImage);
        txtPDName = findViewById(R.id.txtPDName);
        txtPDPrice = findViewById(R.id.txtPDPrice);
        txtPDQuantity = findViewById(R.id.txtPDQuantity);
        txtPDDetails = findViewById(R.id.txtPDDetails);
        txtPDDescription = findViewById(R.id.txtPDDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }
}
