package com.example.freelegalhelp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.freelegalhelp.Adapterclass.viewcasedataAdapter;
import com.example.freelegalhelp.R;
import com.example.freelegalhelp.VolleySingleton;
import com.example.freelegalhelp.data.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.freelegalhelp.loginsignup.LoginPage.MyPREFERENCES;

public class newcaseActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    EditText  cdes;
    viewcasedataAdapter adapter;


    Spinner casetype,casefor;
    String[] ctypedata = { "Select  Type of  Law", "Criminak Law", "Family Law", "Intellectual Property Rights", "Civil Law", "Labour Law","Real Estate & Property Laws","Debts Recovery Tribunal (DRT)","Bank Dispute"};
    String[] cfordata = { "Select  To Whom  You  Consulting For", "MySelf" ,"Father", "Mother","Sister","Brother","Friend's Circle" ,"Relatives" };
    Button bsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcase);


        bsubmit = findViewById(R.id.bsubmit);
        casetype = findViewById(R.id.s1);
        casefor = findViewById(R.id.s2);

        cdes = findViewById(R.id.e1);



        ArrayAdapter aactypedata = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ctypedata);
        aactypedata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        casetype.setAdapter(aactypedata);


        ArrayAdapter aacfordata = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cfordata);
        aacfordata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        casefor.setAdapter(aacfordata);


        bsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                String uid = sharedpreferences.getString("uidKey",null);
                submitdata(uid);



            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        adapter.notifyDataSetChanged();

    }

    private void submitdata(final String uid) {


        final String    vcasetype = casetype.getSelectedItem().toString();
        final String    vcrelation = casefor.getSelectedItem().toString();
        final String  vcdes = cdes.getText().toString().trim();

        Log.e("gettextdata",vcasetype+vcrelation+vcdes);


        if (vcasetype.equals("Select  Type of  Law")) {
            Toast.makeText(this, "Select  Type of  Law", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vcrelation.equals("Select  To Whom  You  Consulting For")) {
            Toast.makeText(this, "Select  To Hoo'm You  Conselting For", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(vcdes)) {
            cdes.setError("Enter Complaint Description");
            cdes.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_NEWCASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.e("newcaseresponce1",response);



                        try{
                            JSONObject jsonObject=new JSONObject(response);

                            Log.d("newcaseresponce2",response);
//                            JSONArray jsonArray=jsonObject.getJSONArray("message");
//                            for(int i=0;i<jsonArray.length();i++){
//                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                                String   jcity=jsonObject1.getString("city");
//                                String   jname=jsonObject1.getString("name");
//                                String   jid=jsonObject1.getString("id");
//                                String   jusertype=jsonObject1.getString("usertype");
//
//                            }

                            casetype.setSelection(0);
                            casefor.setSelection(0);
                            cdes.setText("");


                            Toast.makeText(newcaseActivity.this, "Complaint successfully  Submited", Toast.LENGTH_SHORT).show();

                        }catch (JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cus_id", uid);
                params.put("complaint_type", vcasetype);
                params.put("complaint_for", vcrelation);
                params.put("complaint_remark", vcdes);
                Log.e("requstid",uid+vcasetype+vcrelation+vcdes);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


}
