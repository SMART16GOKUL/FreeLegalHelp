package com.example.freelegalhelp.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.freelegalhelp.Activity.elaboratedprofile.Pmyprofile;
import com.example.freelegalhelp.Adapterclass.viewcasedataAdapter;
import com.example.freelegalhelp.R;
import com.example.freelegalhelp.VolleySingleton;
import com.example.freelegalhelp.data.URLs;
import com.example.freelegalhelp.Adapterclass.viewcasedata;
import com.example.freelegalhelp.loginsignup.LoginPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.freelegalhelp.loginsignup.LoginPage.MyPREFERENCES;

public class HomePageActivity extends AppCompatActivity {

    androidx.recyclerview.widget.RecyclerView recyclerView;
    private viewcasedataAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    SharedPreferences sharedpreferences;
//    TextView  newcase;

    List<viewcasedata> personUtilsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        recyclerView = findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        personUtilsList = new ArrayList<>();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.hometoolbar);
        View view =getSupportActionBar().getCustomView();

        TextView logout= (TextView)view.findViewById(R.id.logout);
        ImageView profile= view.findViewById(R.id.img);
        TextView newcase= (TextView)view.findViewById(R.id.newt);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String uid = sharedpreferences.getString("uidKey",null);
        viewcasedetile(uid);

//        newcase = findViewById(R.id.newcase);

        Intent intent = getIntent();
        String refdata = intent.getStringExtra("data1");


        newcase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           Intent i = new Intent(HomePageActivity.this,newcaseActivity.class);
           startActivity(i);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

//                sharedpreferences = (SharedPreferences) getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
//                editor.putString("MyPrefs", "0");editor.apply();

                Intent i = new Intent(HomePageActivity.this, LoginPage.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomePageActivity.this, elaboratedActivity.class);
                startActivity(i);
            }
        });



    }



    private void viewcasedetile(final String uid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_VIEWCASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("viewcase1",response);

                        try{
                            JSONObject jsonObject=new JSONObject(response);

                            Log.d("viewcase2",response);
                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                                String   jid=jsonObject1.getString("id");
                                String   jcomplaint_type=jsonObject1.getString("complaint_type");
                                String   jcomplaint_for=jsonObject1.getString("complaint_for");
                                String   jcomplaint_remark=jsonObject1.getString("complaint_remark");
                                String   jcomplaint_date=jsonObject1.getString("complaint_date");
                                String   jcomplaint_id=jsonObject1.getString("complaint_id");



                                viewcasedata dataman = new viewcasedata();
                                dataman.setCtype(jsonObject1.getString("complaint_type"));
                                dataman.setCfor(jsonObject1.getString("complaint_for"));
                                dataman.setCremark(jsonObject1.getString("complaint_remark"));
                                dataman.setCdate(jsonObject1.getString("complaint_date"));
                                dataman.setCeditvalue(jsonObject1.getString("status"));
                                dataman.setCaseid(jsonObject1.getInt("complaint_id"));

//                                Log.e("StateCityid", String.valueOf(jSid)+jCid);

                                personUtilsList.add(dataman);

                            }


                            mAdapter = new viewcasedataAdapter(HomePageActivity.this, personUtilsList);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setHasFixedSize(true);

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
                Log.e("requstid",uid);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    @Override
    public void onBackPressed() {


        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
