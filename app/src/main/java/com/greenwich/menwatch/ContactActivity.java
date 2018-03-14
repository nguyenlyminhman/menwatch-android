package com.greenwich.menwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
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
import com.greenwich.utils.MenwatchServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactActivity extends AppCompatActivity {
    EditText txtContactEmail, txtContactFullname, txtContactPhone, txtContactContent;
    Button btnContactSend, btnContactReset;
    Toolbar tbContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        addControls();
        addBtnContactSendEvent();
        addBtnContactResetEvent();
        addToolbarEvents();
    }

    private void addBtnContactResetEvent() {
        btnContactReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtContactEmail.setText("");
                txtContactFullname.setText("");
                txtContactPhone.setText("");
                txtContactContent.setText("");
            }
        });
    }

    private void addBtnContactSendEvent() {
        btnContactSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = txtContactEmail.getText().toString().trim();
                final String fullname = txtContactFullname.getText().toString().trim();
                final String phone = txtContactPhone.getText().toString().trim();
                final String content = txtContactContent.getText().toString().trim();

                if(!checkEmailValid(email)){
                    Toast.makeText(ContactActivity.this, "Email is wrong format.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fullname.isEmpty()){
                    Toast.makeText(ContactActivity.this, "Enter the fullname.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phone.isEmpty()){
                    Toast.makeText(ContactActivity.this, "Enter the phone.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(content.isEmpty()){
                    Toast.makeText(ContactActivity.this, "Enter the content.", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String getDataLink = MenwatchServer.linkContact;

                StringRequest postRequest = new StringRequest(Request.Method.POST, getDataLink,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    if (response != null) {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("data").equals("success")) {
                                            Toast.makeText(ContactActivity.this, "Thank for your contact.", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            return;
                                        } else if(jsonObject.getString("data").equals("fail")) {
                                            Toast.makeText(ContactActivity.this, "Can not send to menwatch.", Toast.LENGTH_LONG).show();
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
                        params.put("fullname", fullname);
                        params.put("phone", phone);
                        params.put("content", content);
                        params.put("domain", MenwatchServer.host);
                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }
        });
    }
    //check email valid
    boolean checkEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
        setSupportActionBar(tbContact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addControls() {
        txtContactEmail = findViewById(R.id.txtContactEmail);
        txtContactFullname = findViewById(R.id.txtContactFullname);
        txtContactPhone = findViewById(R.id.txtContactPhone);
        txtContactContent = findViewById(R.id.txtContactContent);
        btnContactSend = findViewById(R.id.btnContactSend);
        btnContactReset = findViewById(R.id.btnContactReset);
        tbContact = findViewById(R.id.tbContact);
    }
}
