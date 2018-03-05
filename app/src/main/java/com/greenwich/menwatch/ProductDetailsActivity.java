package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    private void addToCartEvents() {
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.arrCart.size() > 0) {
                    int qty = Integer.parseInt(txtPDQuantity.getText().toString());
                    boolean exist = false;
                    for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                        if (MainActivity.arrCart.get(i).getProductId() == productId) {
                            MainActivity.arrCart.get(i).setProductQuantity(MainActivity.arrCart.get(i).getProductQuantity() + qty);
                            if (MainActivity.arrCart.get(i).getProductQuantity() > productQuantity) {
                                Toast.makeText(ProductDetailsActivity.this, "This product only have " + productQuantity + " items in stock.", Toast.LENGTH_SHORT).show();
                            }
                            MainActivity.arrCart.get(i).setProductPrice(productPrice * MainActivity.arrCart.get(i).getProductQuantity());
                            exist = true;
                        }
                    }
                    if (exist == false) {
                        int quantity = Integer.parseInt(txtPDQuantity.getText().toString());
                        Double itemPrice = quantity * productPrice;
                        MainActivity.arrCart.add(new Cart(productId, productName, itemPrice, productImage1, quantity));
                    }
                } else {
                    int quantity = Integer.parseInt(txtPDQuantity.getText().toString());
                    Double itemPrice = quantity * productPrice;
                    MainActivity.arrCart.add(new Cart(productId, productName, itemPrice, productImage1, quantity));
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
