package com.bmkg.emoreg.lainlain;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KirimPesanActivity extends AppCompatActivity {

    EditText edsatker,edemail,edphone,edpesan;
    Button bkirim;
    ProgressDialog pd;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kirim_pesan);
        edsatker=(EditText) findViewById(R.id.edsatker);
        edemail=(EditText) findViewById(R.id.edemail);
        edphone=(EditText) findViewById(R.id.edphone);
        edpesan=(EditText) findViewById(R.id.edpesan);
        bkirim=(Button) findViewById(R.id.bkirim);
        edsatker.setText(Config.nama_unit_kerja);
        edemail.setText(Config.email);
        bkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimdata();
            }
        });
    }


    private void kirimdata(){
        pd=new ProgressDialog(this);
        pd.setMessage("Sedang Mengirim..");
        pd.show();
        RequestQueue rq=Volley.newRequestQueue(this);
        StringRequest jor=new StringRequest(Request.Method.POST, Config.url+"insert-pesan.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
                try {
                    JSONObject jo=new JSONObject(response);
                    JSONArray ja=jo.getJSONArray("status");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject injo=ja.getJSONObject(i);
                        status=injo.getInt("kode");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(KirimPesanActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map=new HashMap();
                map.put("key",Config.key);
                map.put("nama",Config.nama);
                map.put("satker",edsatker.getText().toString());
                map.put("email",edemail.getText().toString());
                map.put("nohp",edphone.getText().toString());
                map.put("pesan",edpesan.getText().toString());
                return map;
            }
        };
        jor.setRetryPolicy(new DefaultRetryPolicy(1000*3,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                if(status==0){
                    new exectast(pd).execute();
                }else{
                    pd.dismiss();
                    Toast.makeText(KirimPesanActivity.this, "Pesan Gagal Dikirim", Toast.LENGTH_LONG).show();
                }

            }
        });
        rq.add(jor);

    }

    private class exectast extends AsyncTask<Void,Void,Void>{
        ProgressDialog pd;

        exectast(ProgressDialog pd){
            this.pd=pd;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String subject="Pesan Dari pengguna Aplikasi EMOREG";
            String pesan="Pengirim : "+edemail.getText().toString()+" ("+Config.nama+") "+"\n"+
                    "Isi Pesan : \n"+edpesan.getText().toString();
            new Mailsender(Config.mastermail,Config.masterpass,
                    subject, pesan,
                    Config.mastertujuan
            );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            Toast.makeText(KirimPesanActivity.this, "Pesan Berhasil Terkirim", Toast.LENGTH_LONG).show();
        }
    }

}
