package com.bmkg.emoreg.usulan;

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
import com.bmkg.emoreg.R;
import com.bmkg.emoreg.home.ModelDaftarUsulan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Minami on 7/1/2018.
 */

public class FragmentDaftarUsulan extends Fragment {

    RecyclerView rv;
    RecyclerView.LayoutManager rvlayman;
    ArrayList<ModelDaftarUsulan> model = new ArrayList<>();
    AdapterDaftarUsulan adapter;
    ProgressBar pb;
    android.support.v7.widget.SearchView svcari;
    FloatingActionButton fabinput;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daftar_usulan, container, false);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        rv=(RecyclerView) v.findViewById(R.id.rv);
        fabinput=(FloatingActionButton) v.findViewById(R.id.fabinput);
        fabinput.setVisibility(View.GONE);
        rvlayman=new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvlayman);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter=new AdapterDaftarUsulan(model,getActivity());
        rv.setAdapter(adapter);
        loaddata();
        fabinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity(),InputUsulanActivity.class);
                startActivity(in);
            }
        });
        return v;
    }


    private void loaddata() {
        model.clear();
        adapter.notifyDataSetChanged();
        pb.setVisibility(View.VISIBLE);
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "daftar-usulan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jodata = ja.getJSONObject(i);
                        String id = jodata.getString("id");
                        String judul_peraturan = jodata.getString("judul_peraturan");
                        String nama_unit_kerja = jodata.getString("nama_unit_kerja");
                        String tanggal_pengajuan = jodata.getString("tanggal_pengajuan");
                        String nomor_peraturan = jodata.getString("nomor_peraturan");
                        String tanggal_pembaruan = jodata.getString("tanggal_pembaruan");
                        String nama_status = jodata.getString("nama_status");
                        String persen_selasai = jodata.getString("persen_selesai");
                        String id_status=jodata.getString("id_status");
                        model.add(new ModelDaftarUsulan(id,judul_peraturan,nama_unit_kerja,tanggal_pengajuan,
                                nomor_peraturan,tanggal_pembaruan,nama_status,persen_selasai,id_status));
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
                m.put("key", "bk201!@#");
                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(1000 * 3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                pb.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });
        rq.add(sr);
    }


}
