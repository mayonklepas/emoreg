package com.bmkg.emoreg.kendala;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.bmkg.emoreg.lainlain.Mailsender;
import com.bmkg.emoreg.lainlain.Notifsender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class KendalaInputActivity extends AppCompatActivity {
    Calendar cal=Calendar.getInstance();
    Button btsimpan;
    ImageButton btcall;
    EditText edtanggal,edjudul,edketerangan;
    int status;
    String id_master="",judul_pengajuan="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendala_input);
        Bundle ex=getIntent().getExtras();
        id_master=ex.getString("id_master");
        judul_pengajuan=ex.getString("judul_pengajuan");
        btcall=(ImageButton) findViewById(R.id.btanggal);
        btsimpan=(Button) findViewById(R.id.btsimpan);
        edtanggal=(EditText) findViewById(R.id.edtanggal);
        edjudul=(EditText) findViewById(R.id.edjudul);
        edketerangan=(EditText) findViewById(R.id.edketerangan);
        final DatePickerDialog.OnDateSetListener dateset=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String tanggal=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                String formatertext=new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());
                edtanggal.setText(tanggal);
            }
        };
        btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),dateset,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan(id_master);
            }
        });
    }


    private void simpan(final String id_master) {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Sedang Mengirim");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "insert-kendala.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                Toast.makeText(KendalaInputActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("key", Config.key);
                m.put("id_master", id_master);
                m.put("judul_pengajuan",judul_pengajuan);
                m.put("id_pegawai", Config.id);
                m.put("tanggal", edtanggal.getText().toString());
                m.put("judul",edjudul.getText().toString());
                m.put("keterangan",edketerangan.getText().toString());
                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(1000 * 3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
               pd.dismiss();
                if(status==0){
                    new exectast().execute();
                    AlertDialog.Builder adb=new AlertDialog.Builder(KendalaInputActivity.this);
                    adb.setTitle("Informasi");
                    adb.setMessage("Kendala Berhasil Dikirim");
                    adb.setCancelable(false);
                    adb.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    adb.show();

                }else{
                    AlertDialog.Builder adb=new AlertDialog.Builder(KendalaInputActivity.this);
                    adb.setTitle("Informasi");
                    adb.setMessage("Kendala Gagal");
                    adb.setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    adb.show();
                }

            }
        });
        rq.add(sr);
    }


    private class exectast extends AsyncTask<Void,Void,Void> {

        exectast(){
        }

        @Override
        protected Void doInBackground(Void... voids) {
            new Notifsender(judul_pengajuan+"/"+edjudul.getText().toString());
            String subject="Pesan Dari pengguna Aplikasi EMOREG";
            String pesan="Pengirim : "+Config.email+" ("+Config.nama+") "+"\n"+
                    "Perka : "+judul_pengajuan+"\n"+
                    "Isi Kendala : "+edjudul.getText().toString();
            new Mailsender(Config.mastermail,Config.masterpass,
                    subject, pesan,
                    Config.mastertujuan
            );

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Pesan Berhasil Terkirim", Toast.LENGTH_LONG).show();
        }
    }
}
