package com.greenwich.menwatch;

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

import com.greenwich.model.Product;
import com.greenwich.utils.Connection;
import com.greenwich.utils.MenwatchServer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetailsActivity extends AppCompatActivity {

    Toolbar tbProductDetails;
    ViewFlipper vfProductDetailsImage;
    TextView txtPDName, txtPDPrice, txtPDQuantity, txtPDDetails, txtPDDescription;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        addProductDetailsControls();

        if (Connection.checkNetworkConnection(getApplicationContext())) {
            addToolbarProductDetailsEvents();
            getProductDetailsData();
        } else {
            Toast.makeText(this, "Please, check your connection", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void getProductDetailsData() {

        Product product = (Product) getIntent().getSerializableExtra("product_details");
        int productId = product.getId();
        int productIdStyle = product.getIdStyle();
        int productIdBrand = product.getIdBrand();
        String productName = product.getName();
        Double productPrice = product.getPrice();
        String productDescription = product.getDescription();
        String productImage1 = product.getImg1();
        String productImage2 = product.getImg2();
        String productImage3 = product.getImg3();
        String productMT = product.getMt();
        String productCS = product.getCs();
        String productSM = product.getSm();
        String productWR = product.getWr();

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
        vfProductDetailsImage.setFlipInterval(3000);
        vfProductDetailsImage.setAutoStart(true);

        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out);
        vfProductDetailsImage.setInAnimation(slide_in);
        vfProductDetailsImage.setOutAnimation(slide_out);

        txtPDName.setText(productName);
        txtPDPrice.setText("$ " + productPrice);
        txtPDDetails.setText("Movement Type: " + productMT +
        "\n\nCase Size: " + productCS +
        "\n\nStrap Material: "+productSM+
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
