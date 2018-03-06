package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.greenwich.adapter.CartAdapter;


public class CartActivity extends AppCompatActivity {

    ListView lvCartItems;
    TextView txtCartNotice;
    static TextView txtCartItemsTotalsPrice;
    Button btnCheckOut, btnShopping;
    Toolbar tbCartItems;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addControls();
        addToolbarCartItemEvents();
        addBtnShoppingEvent();
        checkCartItems();
        setTotalPrice();
//
    }

    private void addBtnShoppingEvent() {
        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

     public static void setTotalPrice() {
        double totalPrice = 0;
        for(int i =0; i<MainActivity.arrCart.size(); i++){
            totalPrice += (MainActivity.arrCart.get(i).getProductPrice() * MainActivity.arrCart.get(i).getProductQuantity());
            txtCartItemsTotalsPrice .setText("$ " + totalPrice);
        }
    }

    private void checkCartItems() {
        if(MainActivity.arrCart.size() <= 0){
            cartAdapter.notifyDataSetChanged();
            txtCartNotice.setVisibility(View.VISIBLE);
            lvCartItems.setVisibility(View.INVISIBLE);
        }else{
            cartAdapter.notifyDataSetChanged();
            txtCartNotice.setVisibility(View.INVISIBLE);
            lvCartItems.setVisibility(View.VISIBLE);
        }
    }

    private void addToolbarCartItemEvents() {
        setSupportActionBar(tbCartItems);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbCartItems.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        lvCartItems = findViewById(R.id.lvCartItems);
        txtCartNotice = findViewById(R.id.txtCartNotice);
        txtCartItemsTotalsPrice = findViewById(R.id.txtCartItemsTotalsPrice);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnShopping = findViewById(R.id.btnShopping);
        tbCartItems = findViewById(R.id.tbCartItems);

        cartAdapter = new CartAdapter(CartActivity.this, MainActivity.arrCart);
        lvCartItems.setAdapter(cartAdapter);
    }
}
