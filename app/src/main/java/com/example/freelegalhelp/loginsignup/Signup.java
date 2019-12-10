package com.example.freelegalhelp.loginsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.freelegalhelp.R;
import com.example.freelegalhelp.VolleySingleton;
import com.example.freelegalhelp.data.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {


    Spinner gender,city;

    String[] genderspinner = { "Select  You  gender", "Male", "Female"};
    String[] cityspinner = { "Select  You  City", "Ariyalur", "Chennai","Coimbatore","Cuddalore","Dharmapuri","Dindigul","Erode","Kanchipuram"
    ,"Karur","Krishnagiri","Madurai","Nagapattinam","Nagercoil","Namakkal","Perambalur","Pudukkottai","Ramanathapuram","Salem","Sivagangai",
    "Thanjavur","Theni","Thiruvallur","Thiruvarur","Thoothukudi","Tiruchirappalli","Tirunelveli","Tiruppur","Tiruvannamalai","Udagamandalam",
    "Vellore","Viluppuram","Virudhunagar"};



    EditText Name,email,password,phone,alt_phone,streetname,landmark,pincode;
    TextView state,dateob;
    Button bback,bsignup;

    String statevalue ="TAMIL  NADU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        bback =  findViewById(R.id.b1);
        bsignup =  findViewById(R.id.b2);

        Name =  findViewById(R.id.tt1);
        email =  findViewById(R.id.tt2);
        password =  findViewById(R.id.tt3);
        gender =  findViewById(R.id.tt4);
        dateob =  findViewById(R.id.tt5);
        phone =  findViewById(R.id.tt6);
        alt_phone =  findViewById(R.id.tt7);
        streetname =  findViewById(R.id.tt8);

        landmark =  findViewById(R.id.tt9);
        state =  findViewById(R.id.tt10);
        city =  findViewById(R.id.tt11);
        pincode =  findViewById(R.id.tt12);


        state.setText(statevalue);

        ArrayAdapter aagender = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genderspinner);
        aagender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        gender.setAdapter(aagender);


        ArrayAdapter aacity = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cityspinner);
        aacity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        city.setAdapter(aacity);


        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Signup.this, LoginPage.class);
                startActivity(i);
            }
        });


        bsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signupmethod();

            }
        });

        dateob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();


            }
        });



    }

    private void showDialog() {

        final Calendar c = Calendar.getInstance();

       int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    private void signupmethod() {

             final String  vName = Name.getText().toString().trim();
             final String vemail = email.getText().toString().trim();
             final String vpassword = password.getText().toString().trim();

            final String    gendervalue = gender.getSelectedItem().toString();
            final String vdateob = dateob.getText().toString().trim();

            final String vphone = phone.getText().toString().trim();
            final String valt_phone = alt_phone.getText().toString().trim();
            final String vstreetname = streetname.getText().toString().trim();
            final String vlandmark = landmark.getText().toString().trim();
            final String vstate = state.getText().toString().trim();

            final String    cityvalue = city.getSelectedItem().toString();
            final String vpincode = pincode.getText().toString().trim();


        Log.v("stringlog",vName+vemail+vpassword+gendervalue+vdateob+vstreetname+vlandmark+vstate+cityvalue+vpincode);


        //first we will do the validations
            if (TextUtils.isEmpty(vName)) {
                Name.setError("Please enter username");
                Name.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(vemail)) {
                email.setError("Please enter your email");
                email.requestFocus();
                return;
            }

        if (TextUtils.isEmpty(vpassword)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }




        if (gendervalue.equals("Select  You  gender")) {
            Toast.makeText(this, "Select  Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vdateob.equals("Date-Of-Birth")) {
            Toast.makeText(this, "Select  Date Of Birth", Toast.LENGTH_SHORT).show();
            return;
        }



            if (TextUtils.isEmpty(vphone)) {
                phone.setError("Enter a phone number");
                phone.requestFocus();
                return;
            }



            if (TextUtils.isEmpty(valt_phone)) {
                alt_phone.setError("Enter a alternate number");
                alt_phone.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(vstreetname)) {
                streetname.setError("Enter a streetname");
                streetname.requestFocus();
                return;
            }
//

            if (TextUtils.isEmpty(vlandmark)) {
                landmark.setError("Enter a landmark");
                landmark.requestFocus();
                return;
            }


        if (cityvalue.equals("Select  You  City")) {
            Toast.makeText(this, "Select  City", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(vpincode)) {
            pincode.setError("Enter a pincode");
            pincode.requestFocus();
            return;
        }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SIGNUP,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);
                                //if no error in response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();


                                    Name.setText("");
                                    email.setText("");
                                    password.setText("");
                                    phone.setText("");
                                    alt_phone.setText("");
                                    streetname.setText("");
                                    landmark.setText("");
                                    pincode.setText("");




                                    finish();
                                    startActivity(new Intent(getApplicationContext(), LoginPage.class));


                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                    params.put("Name", vName);
                    params.put("email", vemail);
                    params.put("password", vpassword);
                    params.put("gender", gendervalue);
                    params.put("doj", vdateob);
                    params.put("phone", vphone);
                    params.put("alt_phone", valt_phone);
                    params.put("streetname", vstreetname);
                    params.put("landmark", vlandmark);
                    params.put("states", vstate);
                    params.put("city", cityvalue);
                    params.put("pincode", vpincode);



                    Log.v("signuplog",vName+vemail+vpassword+gendervalue+vdateob+vstreetname+vlandmark+vstate+cityvalue+vpincode);

//                    Name.setText("");
//                    email.setText("");
//                    password.setText("");
//                    phone.setText("");
//                    alt_phone.setText("");
//                    streetname.setText("");
//                    landmark.setText("");
//                    pincode.setText("");
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



}
