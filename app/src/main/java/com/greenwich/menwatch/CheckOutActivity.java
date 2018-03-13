package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenwich.utils.CustomerSession;
import com.greenwich.utils.MenwatchServer;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.CardMultilineWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {

    Button btnCheckOut, btnResetCheckOut;
    EditText txtReceiverName, txtReceiverAddress, txtReceiverPhone, txtCardName;
    Toolbar tbCheckOut;
    CustomerSession cSession;
    CardInputWidget mCardInputWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        cSession = new CustomerSession(getApplicationContext());
        addControls();
        addEvents();
    }

    private void addEvents() {
        addTbCheckOutEvent();
        btnResetCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtReceiverName.setText("");
                txtReceiverAddress.setText("");
                txtReceiverPhone.setText("");
                txtCardName.setText("");
            }
        });
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String publishableApiKey = "pk_test_ELqaVegi7UJMKfrsEWgR7jfd";
                final String ReceiverName = txtReceiverName.getText().toString().trim();
                final String ReceiverAddress = txtReceiverAddress.getText().toString().trim();
                final String ReceiverPhone = txtReceiverPhone.getText().toString().trim();
                final Stripe stripe = new Stripe(getApplicationContext(), publishableApiKey);
                Card card = mCardInputWidget.getCard();
                final String CardName = txtCardName.getText().toString().trim();
                if (ReceiverName.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Enter the receiver name.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ReceiverAddress.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Enter the receiver address.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ReceiverPhone.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Enter the receiver email.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (CardName.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Enter the card holder name.", Toast.LENGTH_SHORT).show();
                    return;

                }
                Log.d("card validation", card.validateCard()+"");
                card.setName(CardName);
                stripe.createToken(card, publishableApiKey, new TokenCallback() {
                    public void onSuccess(final Token token) {

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        String getCheckoutLink = MenwatchServer.linkCheckout;
                        StringRequest postRequest = new StringRequest(Request.Method.POST, getCheckoutLink,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response != null) {
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = new JSONObject(response);
                                                if (Integer.parseInt(jsonObject.getString("data")) > 0) {
                                                    MainActivity.arrCart.clear();
                                                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                                                    startActivity(intent);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("Error.Response", error + "");
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams() {
                                JSONArray jsonArray = new JSONArray();
                                int totalPrice = 0;
                                for (int i = 0; i < MainActivity.arrCart.size(); i++) {
                                    totalPrice += (MainActivity.arrCart.get(i).getProductPrice() * MainActivity.arrCart.get(i).getProductQuantity());
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("ProductId", MainActivity.arrCart.get(i).getProductId());
                                        jsonObject.put("ProductQty", MainActivity.arrCart.get(i).getProductQuantity());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("ReceiverName", ReceiverName);
                                params.put("ReceiverAddress", ReceiverAddress);
                                params.put("ReceiverPhone", ReceiverPhone);

                                params.put("CustomerId", cSession.getCustomerDetails().get("CustomerId") + "");
                                params.put("CustomerEmail", cSession.getCustomerDetails().get("CustomerEmail") + "");
                                params.put("CustomerFullname", cSession.getCustomerDetails().get("CustomerName") + "");
                                params.put("totalPrice", totalPrice + "");
                                params.put("OrderDetail", jsonArray.toString());
                                params.put("StripeId", token.getId());

                                params.put("domain", MenwatchServer.host);
                                return params;
                            }
                        };
                        requestQueue.add(postRequest);
                    }

                    public void onError(Exception error) {
                        Log.d("Stripe", error.getLocalizedMessage());
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnCartIcon:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTbCheckOutEvent() {
        setSupportActionBar(tbCheckOut);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbCheckOut.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnResetCheckOut = findViewById(R.id.btnResetCheckOut);
        txtReceiverName = findViewById(R.id.txtReceiverName);
        txtReceiverAddress = findViewById(R.id.txtReceiverAddress);
        txtReceiverPhone = findViewById(R.id.txtReceiverPhone);
        txtCardName = findViewById(R.id.txtCardName);
        tbCheckOut = findViewById(R.id.tbCheckOut);
    }
}
