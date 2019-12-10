package com.example.freelegalhelp.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.freelegalhelp.Activity.HomePageActivity;
import com.example.freelegalhelp.R;
import com.example.freelegalhelp.VolleySingleton;
import com.example.freelegalhelp.data.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPage extends AppCompatActivity {

    androidx.cardview.widget.CardView   logincard;

    TextView bsignup,tforgottext;
    Button loginbutton;

    com.google.android.material.textfield.TextInputEditText ephone,epassword;


    SharedPreferences.Editor editor;

    public static final String MyPREFERENCES = "LegalHelp" ;
    public static final String uname = "unameKey";
    public static final String uemail = "uemailKey";
    public static final String uid = "uidKey";
    public static final String uphone = "uphoneKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        logincard = findViewById(R.id.logincard);
        logincard.setRadius(40);
        bsignup   =  findViewById(R.id.bsignup);
        tforgottext   =  findViewById(R.id.tforgottext);
        loginbutton   =  findViewById(R.id.blogin);


        ephone   =  findViewById(R.id.e1);
        epassword   =  findViewById(R.id.e2);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String name1 = sharedpreferences.getString("uemailKey",null);
        assert name1 != null;
        if(name1 != null){
            Intent intent = new Intent(LoginPage.this, HomePageActivity.class);
            startActivity(intent);
        }

        else {

        }

        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(LoginPage.this, Signup.class);
                startActivity(i);



            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginmethod();

            }
        });


    }

    private void loginmethod() {
        //first getting the values
        final String username = ephone.getText().toString();
        final String password = epassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if everything is fineusername
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("logresp",response);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();


                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("name"),
//                                        userJson.getString("email"),
//                                        userJson.getString("phone")
//                                );



                                String reid = userJson.getString("id");
                                String rename = userJson.getString("Name");
                                String reemail = userJson.getString("email");
                                String rephone = userJson.getString("phone");
                                Log.e("loginrsstrings",reid+rename+reemail+rephone);

                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                                editor = sharedpreferences.edit();

                                editor.putString(uname, rename);
                                editor.putString(uemail, reemail);
                                editor.putString(uid, reid);
                                editor.putString(uphone, rephone);
                                editor.apply();

                                //storing the user in shared preferences
//                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(LoginPage.this, HomePageActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("exeption", e.toString());
//                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", username);
                params.put("password", password);
                Log.e("requstparram",username+password);


                return params;


            }
        };
        Log.d("sessionput",username);

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
