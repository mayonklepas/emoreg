package com.bmkg.emoreg.notif;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bmkg.emoreg.lainlain.Config;
import com.bmkg.emoreg.lainlain.KirimPesanActivity;
import com.bmkg.emoreg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minami on 7/1/2018.
 */

public class FragmentNotif extends Fragment {

    RecyclerView rv;
    RecyclerView.LayoutManager rvlayman;
    ArrayList<Modelnotif> model = new ArrayList<>();
    AdapterNotif adapter;
    ProgressBar pb;
    android.support.v7.widget.SearchView svcari;
    FloatingActionButton fabpesan;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notif, container, false);
        fabpesan=(FloatingActionButton) v.findViewById(R.id.fb);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        rv=(RecyclerView) v.findViewById(R.id.rv);
        rvlayman=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvlayman);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        if(Config.status==0){
            adapter=new AdapterNotif(model,getActivity(),-1);
        }else{
            adapter=new AdapterNotif(model,getActivity(),0);

        }
        rv.setAdapter(adapter);
        fabpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),KirimPesanActivity.class);
                startActivity(i);
            }
        });
        loaddata();
        return v;
    }


    private void loaddata() {
        model.clear();
        adapter.notifyDataSetChanged();
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "notifikasi.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jodata = ja.getJSONObject(i);
                        String id = jodata.getString("id");
                        String tanggal = jodata.getString("tanggal");
                        String judul = jodata.getString("judul");
                        String pesan = jodata.getString("pesan");
                        model.add(new Modelnotif(id,tanggal,judul,pesan) );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onErrorResponse: "+error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("key", Config.key);
                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(1000 * 3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                pb.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                Config.status=0;
            }
        });
        rq.add(sr);
    }


}
