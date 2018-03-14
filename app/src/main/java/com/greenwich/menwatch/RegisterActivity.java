package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText txtRegisterEmail, txtRegisterPassword, txtRegisterConfirmPassword;
    EditText txtRegisterFirtsname, txtRegisterLastname, txtRegisterAddress, txtRegisterPhone;
    Button btnRegisterNew, btnRegisterReset;
    Toolbar tbRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        addToolbarEvents();
        addBtnRegisterResetEvent();
        addBtnRegisterNewEvent();
    }

    private void addBtnRegisterNewEvent() {
        btnRegisterNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtRegisterEmail.getText().toString().trim();
                final String password = txtRegisterPassword.getText().toString().trim();
                final String confirmPassword = txtRegisterConfirmPassword.getText().toString().trim();
                final String firstname = txtRegisterFirtsname.getText().toString().trim();
                final String lastname = txtRegisterLastname.getText().toString().trim();
                final String address = txtRegisterAddress.getText().toString().trim();
                final String phone = txtRegisterPhone.getText().toString().trim();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String getDataLink = MenwatchServer.linkRegister;

                StringRequest postRequest = new StringRequest(Request.Method.POST, getDataLink,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response != null) {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("data").equals("fail_email")) {
                                            Toast.makeText(RegisterActivity.this, "Email is already exists.", Toast.LENGTH_LONG).show();
                                            return;
                                        } else if(jsonObject.getString("data").equals("success")) {
                                            Toast.makeText(RegisterActivity.this, "Register successfully.", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                            return;
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", password);
                        params.put("firstname", firstname);
                        params.put("lastname", lastname);
                        params.put("address", address);
                        params.put("phone", phone);
                        params.put("domain", MenwatchServer.host);
                        return params;
                    }
                };
                requestQueue.add(postRequest);


            }
        });
    }

    private void addBtnRegisterResetEvent() {
        btnRegisterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRegisterEmail.setText("");
                txtRegisterPassword.setText("");
                txtRegisterConfirmPassword.setText("");
                txtRegisterFirtsname.setText("");
                txtRegisterLastname.setText("");
                txtRegisterAddress.setText("");
                txtRegisterPhone.setText("");
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

    private void addToolbarEvents() {
        setSupportActionBar(tbRegister);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        txtRegisterEmail = findViewById(R.id.txtRegisterEmail);
        txtRegisterPassword = findViewById(R.id.txtRegisterPassword);
        txtRegisterConfirmPassword = findViewById(R.id.txtRegisterConfirmPassword);
        txtRegisterFirtsname = findViewById(R.id.txtRegisterFirtsname);
        txtRegisterLastname = findViewById(R.id.txtRegisterLastname);
        txtRegisterAddress = findViewById(R.id.txtRegisterAddress);
        txtRegisterPhone = findViewById(R.id.txtRegisterPhone);

        btnRegisterNew = findViewById(R.id.btnRegisterNew);
        btnRegisterReset = findViewById(R.id.btnRegisterReset);
        tbRegister = findViewById(R.id.tbRegister);
    }
}
