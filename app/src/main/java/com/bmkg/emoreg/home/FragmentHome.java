package com.bmkg.emoreg.home;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.bmkg.emoreg.lainlain.Itemspacer;
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

public class FragmentHome extends Fragment {

    RecyclerView rv;
    RecyclerView.LayoutManager rvlayman;
    ArrayList<Combined> model = new ArrayList<>();
    AdapterHome adapter;
    android.support.v7.widget.SearchView svcari;
    ProgressBar pb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rv);
        pb=(ProgressBar) v.findViewById(R.id.pb);
        rvlayman = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(rvlayman);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new Itemspacer(getActivity(),"Status"));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new AdapterHome(model,getActivity());
        rv.setAdapter(adapter);
        loaddata();
        return v;
    }


    private void loaddata() {
        pb.setVisibility(View.VISIBLE);
        model.clear();
        adapter.notifyDataSetChanged();
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "home.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jo = new JSONObject(response);

                    JSONArray jagraph = jo.getJSONArray("datagrafik");
                    model.add(new ModelGraph(jagraph));

                    JSONArray jastatus = jo.getJSONArray("datastatus");
                    for (int i = 0; i < jastatus.length(); i++) {
                        int no = i + 1;
                        JSONObject jodata = jastatus.getJSONObject(i);
                        String judul_peraturan = jodata.getString("judul_peraturan");
                        String tanggal_pembaruan = jodata.getString("tanggal_pembaruan");
                        String nama_status = jodata.getString("nama_status");
                        String persen_selasai = jodata.getString("persen_selesai");
                        model.add(new ModelStatus(tanggal_pembaruan, judul_peraturan, nama_status, persen_selasai, String.valueOf(no)));
                    }

                    model.add(new ModelSpacer("DAFTAR RAPAT"));

                    JSONArray jarapat = jo.getJSONArray("datarapat");
                    for (int i = 0; i < jarapat.length(); i++) {
                        int no = i + 1;
                        JSONObject jodata = jarapat.getJSONObject(i);
                        String judul_peraturan = jodata.getString("judul_peraturan");
                        String tanggal = jodata.getString("tanggal");
                        String judul_rapat = jodata.getString("judul_rapat");
                        model.add(new ModelRapat(String.valueOf(no),judul_peraturan,tanggal,judul_rapat));
                    }

                    model.add(new ModelSpacer2("DAFTAR MASUKAN"));

                    JSONArray jamasukan = jo.getJSONArray("datamasukan");
                    for (int i = 0; i < jamasukan.length(); i++) {
                        int no = i + 1;
                        JSONObject jodata = jamasukan.getJSONObject(i);
                        String judul_peraturan = jodata.getString("judul_peraturan");
                        String tanggal = jodata.getString("tanggal");
                        String nama = jodata.getString("nama");
                        String judul = jodata.getString("judul");
                        model.add(new ModelMasukan(String.valueOf(no),judul_peraturan,tanggal,nama,judul));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onErrorResponse: " + error.getMessage());
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
            }
        });
        rq.add(sr);
    }


}
