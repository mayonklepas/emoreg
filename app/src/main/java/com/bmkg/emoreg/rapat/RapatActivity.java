package com.bmkg.emoreg.rapat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.bmkg.emoreg.R;
import com.bmkg.emoreg.lainlain.Config;
import com.bmkg.emoreg.masukan.MasukanActivity;
import com.bmkg.emoreg.masukan.MasukanInputActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RapatActivity extends AppCompatActivity {

    RecyclerView rv;
    RecyclerView.LayoutManager rvlayman;
    ArrayList<RapatModel> model = new ArrayList<>();
    RapatAdapter adapter;
    ProgressBar pb;
    FloatingActionButton fbadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapat);
        Bundle ex=getIntent().getExtras();
        final String id_master=ex.getString("id");
        final String judul_pengajuan=ex.getString("judul_pengajuan");
        pb=(ProgressBar) findViewById(R.id.pb);
        fbadd=(FloatingActionButton) findViewById(R.id.fbadd);
        rv = (RecyclerView) findViewById(R.id.rv);
        rvlayman = new LinearLayoutManager(this);
        rvlayman.isSmoothScrolling();
        rv.setLayoutManager(rvlayman);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new RapatAdapter(model,this, judul_pengajuan);
        rv.setAdapter(adapter);
        loaddata(id_master);
        if(!Config.nama_unit_kerja.equals("Admin")){
            fbadd.setVisibility(View.INVISIBLE);
        }
        fbadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(RapatActivity.this,RapatInputActivity.class);
                in.putExtra("id_master",id_master);
                in.putExtra("judul_pengajuan",judul_pengajuan);
                startActivity(in);
            }
        });
    }

    private void loaddata(final String id) {
        pb.setVisibility(View.VISIBLE);
        model.clear();
        adapter.notifyDataSetChanged();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "daftar-rapat.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jo=new JSONObject(response);
                    JSONArray jastatus = jo.getJSONArray("data");
                    model.add(new RapatModel("","","","",""));
                    for (int i = 0; i < jastatus.length(); i++) {
                        int no = i+1;
                        JSONObject jodata = jastatus.getJSONObject(i);
                        String judul= jodata.getString("judul_rapat");
                        String tanggal = jodata.getString("tanggal");
                        String keterangan = jodata.getString("keterangan");
                        String attachment = jodata.getString("attachment");
                        model.add(new RapatModel(String.valueOf(no),tanggal,judul,keterangan,attachment));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RapatActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("key", Config.key);
                m.put("id", id);
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
