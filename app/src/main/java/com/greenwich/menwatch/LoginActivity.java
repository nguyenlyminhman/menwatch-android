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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.greenwich.model.Product;
import com.greenwich.utils.CustomerSession;
import com.greenwich.utils.MenwatchServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Toolbar tbLogin;
    Button btnLogin, btnReset;
    EditText txtEmail, txtPassword;
    CustomerSession cSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
        addBtnResetEvents();
        addBtnLoginEvent();
        addToolbarLoginEvents();
    }

    private void addBtnLoginEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cSession = new CustomerSession(getApplicationContext());
                final String email = txtEmail.getText().toString().trim();
                final String password = txtPassword.getText().toString().trim();


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String getDataLink = MenwatchServer.linkLogin;

                StringRequest postRequest = new StringRequest(Request.Method.POST, getDataLink,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response != null) {
                                        JSONObject jsonObject= new JSONObject(response);
                                        if (jsonObject.getString("data").equals("err_email")) {
                                            Toast.makeText(LoginActivity.this, "Email is not found.", Toast.LENGTH_SHORT).show();
                                            return;
                                        } else if (jsonObject.getString("data").equals("err_pass")) {
                                            Toast.makeText(LoginActivity.this, "Password is wrong.", Toast.LENGTH_SHORT).show();
                                            return;
                                        } else {
                                            JSONObject jsonData = jsonObject.getJSONObject("data");
                                            String fullname = jsonData.getString("fistname") + " " + jsonData.getString("lastname");
                                            String id = jsonData.getInt("id") + "";
                                            String email = jsonData.getString("email");
                                            cSession.createCustomerLoginSession(id, email, fullname);
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            // Add new Flag to start new Activity
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
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
                        params.put("domain", "http://192.168.247.2:3000");
                        return params;
                    }
                };
                requestQueue.add(postRequest);


//                if(email.equals("email") && password.equals("pass")){
//                    cSession.createCustomerLoginSession("Android Login Example",
//                            "nlmman@gmail.com");
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    // Add new Flag to start new Activity
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }

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

    private void addToolbarLoginEvents() {
        setSupportActionBar(tbLogin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addBtnResetEvents() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail.setText("");
                txtPassword.setText("");
            }
        });
    }

    private void addControls() {
        btnLogin = findViewById(R.id.btnLogin);
        btnReset = findViewById(R.id.btnReset);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        tbLogin = findViewById(R.id.tbLogin);
    }
}
