package com.example.freelegalhelp.Adapterclass;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewcasedataAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.example.freelegalhelp.Adapterclass.viewcasedataAdapter.ViewHolder> {

    private Context context;
    private List<viewcasedata> personUtils;
    viewcasedataAdapter adapter;
     Dialog dialog;
    Spinner casetype,casefor;
    EditText cdes;
    ArrayAdapter<String> aactypedata;
    ArrayAdapter<String> aacfordata;


    public viewcasedataAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }

    @Override
    public com.example.freelegalhelp.Adapterclass.viewcasedataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcasedata_list, parent, false);
        com.example.freelegalhelp.Adapterclass.viewcasedataAdapter.ViewHolder viewHolder = new com.example.freelegalhelp.Adapterclass.viewcasedataAdapter.ViewHolder(v);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(com.example.freelegalhelp.Adapterclass.viewcasedataAdapter.ViewHolder holder, int position) {

        final int pos = position;
        holder.itemView.setTag(personUtils.get(position));

        final viewcasedata pu = personUtils.get(position);

        String s = String.valueOf(position+1);
        holder.lsn.setText(s);


        holder.lcastype.setText(pu.getCtype());

        holder.lcasdes.setText(pu.getCremark());
        holder.lcasfor.setText(pu.getCfor());
        holder.lcasdate.setText(pu.getCdate());

        String editvalue = pu.getCeditvalue();

        if (editvalue.equals("Opened")){

            holder.leimg.setVisibility(View.VISIBLE);
//            holder.ledttxt.setVisibility(View.GONE);
            holder.ledttxt.setText(editvalue);


        }

        else {


            holder.leimg.setVisibility(View.GONE);
//            holder.ledttxt.setVisibility(View.VISIBLE);

            holder.ledttxt.setText(editvalue);

        }


        holder.leimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Toast.makeText(context, "clicked  position"+pos, Toast.LENGTH_SHORT).show();


                Integer caseid = pu.getCaseid();

                showDialog(caseid);

//                Toast.makeText(context, "caseid"+caseid, Toast.LENGTH_SHORT).show();

            }


        });


    }


        public void showDialog(final Integer msg){
             dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.case_upd_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


             dialogcasedata(msg);

            Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.bc);
            dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            Button dialogBtn_okay = (Button) dialog.findViewById(R.id.bsu);
            dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
//                    dialog.cancel();

                    updatecaseiteam(msg);
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();


                }
            });

            dialog.show();

    }


    private void dialogcasedata(Integer msg) {


        String[] ctypedata = {  "Criminak Law", "Family Law", "Intellectual Property Rights", "Civil Law", "Labour Law","Real Estate & Property Laws","Debts Recovery Tribunal (DRT)","Bank Dispute"};
        String[] cfordata = { "MySelf" ,"Father", "Mother","Sister","Brother","Friend's Circle" ,"Relatives" };

        casetype = dialog.findViewById(R.id.s1);
        casefor = dialog.findViewById(R.id.s2);

        cdes = dialog.findViewById(R.id.e1);

        TextView date = dialog.findViewById(R.id.e2);

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it
        date.setText(currentDateTimeString);

        autoviewcase(msg);

         aactypedata = new ArrayAdapter(context,android.R.layout.simple_spinner_item,ctypedata);
        aactypedata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        casetype.setAdapter(aactypedata);


         aacfordata = new ArrayAdapter(context,android.R.layout.simple_spinner_item,cfordata);
        aacfordata.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        casefor.setAdapter(aacfordata);


    }

    private void autoviewcase(final Integer msg) {

        final String id = String.valueOf(msg);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_VIEWCOMPLITEAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("viewcaseiteam1",response);

                        try{
                            JSONObject jsonObject=new JSONObject(response);

                            Log.d("viewcaseiteam2",response);
                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                String   jcomplaint_type=jsonObject1.getString("complaint_type");
                                String   jcomplaint_for=jsonObject1.getString("complaint_for");
                                String   jcomplaint_remark=jsonObject1.getString("complaint_remark");


                                setadapter(jcomplaint_type,jcomplaint_for);


//                                Log.v("spinervalue", jgender+jcity);


                                cdes.setText(jcomplaint_remark);


                            }

                        }catch (JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                Log.e("requstid",id);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void updatecaseiteam(Integer msg) {

        final String id = String.valueOf(msg);

        final String    ctypevalue = casetype.getSelectedItem().toString();
        final String    cforvalue = casefor.getSelectedItem().toString();
        final String    cdescvalue = cdes.getText().toString().trim();


        if (TextUtils.isEmpty(cdescvalue)) {
            cdes.setError("Please enter username");
            cdes.requestFocus();
            return;
        }



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATECOMPLITEAM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("viewcaseiteam1",response);

                        try{
                            JSONObject jsonObject=new JSONObject(response);

//                            Log.d("viewcaseiteam2",response);
                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                            for(int i=0;i<jsonArray.length();i++){

                                Toast.makeText(context, "UPDATE  SUCCESS", Toast.LENGTH_LONG).show();



                            }


                        }catch (JSONException e){e.printStackTrace();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("complaint_type", ctypevalue);
                params.put("complaint_for", cforvalue);
                params.put("complaint_remark", cdescvalue);

                Log.e("requstid",id);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    private void setadapter(String jcomplaint_type, String jcomplaint_for) {

        int  ctypevalue = aactypedata.getPosition(jcomplaint_type);
        casetype.setSelection(ctypevalue);

        int  cforvalue = aacfordata.getPosition(jcomplaint_for);
        casefor.setSelection(cforvalue);

        Log.v("complintdetiles",jcomplaint_for+jcomplaint_type);

    }


    @Override
    public int getItemCount() {
        return personUtils.size();
    }

    public void filterlist(ArrayList<viewcasedata> filterdlistdata) {

        personUtils  = filterdlistdata;
        notifyDataSetChanged();


    }

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder{

        public TextView lsn;
        public TextView lcastype;
        public TextView lcasdes;
        public TextView lcasfor;
        public TextView lcasdate;
        public TextView ledttxt;
        public ImageView leimg;


        public ViewHolder(View itemView) {
            super(itemView);

            lsn = (TextView) itemView.findViewById(R.id.dt1);
            lcastype = (TextView) itemView.findViewById(R.id.dt2);

            lcasdes = (TextView) itemView.findViewById(R.id.dt3);
            lcasfor = (TextView) itemView.findViewById(R.id.dt4);
            lcasdate = (TextView) itemView.findViewById(R.id.dt5);

            ledttxt = (TextView) itemView.findViewById(R.id.dt6);
            leimg = (ImageView) itemView.findViewById(R.id.eimg);



        }

    }



}


