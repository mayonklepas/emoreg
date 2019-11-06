package com.bmkg.emoreg.status;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.bmkg.emoreg.rapat.RapatInputActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InputstatusActivity extends AppCompatActivity {

    Calendar cal=Calendar.getInstance();
    Button btsimpan;
    ImageButton btcall,btenggang,bdateline;
    EditText edtanggal,edketerangan,edtenggang,eddateline;
    int status;
    Spinner spstatus;
    String id_master="",judul_pengajuan="";
   public ArrayList<String> nama_status=new ArrayList<>();
   public ArrayList<String> id_status=new ArrayList<>();
   public ArrayList<Integer> tenggang=new ArrayList<>();
   public String nama_status_send="",id_status_send="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_status);
        Bundle ex=getIntent().getExtras();
        id_master=ex.getString("id_master");
        judul_pengajuan=ex.getString("judul_pengajuan");
        btcall=(ImageButton) findViewById(R.id.btanggal);
        btenggang=(ImageButton) findViewById(R.id.btenggang);
        bdateline=(ImageButton) findViewById(R.id.bdateline);
        btsimpan=(Button) findViewById(R.id.btsimpan);
        edtanggal=(EditText) findViewById(R.id.edtanggal);
        edtenggang=(EditText) findViewById(R.id.edtenggang);
        eddateline=(EditText) findViewById(R.id.eddateline);
        spstatus=(Spinner) findViewById(R.id.spstatus);
        edketerangan=(EditText) findViewById(R.id.edketerangan);
        String curdate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        edtanggal.setText(curdate);
        loadspinner();
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
        final DatePickerDialog.OnDateSetListener tenggangset=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String tanggal=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                String formatertext=new SimpleDateFormat("dd MMMM yyyy").format(cal.getTime());
                edtenggang.setText(tanggal);
            }
        };

        final DatePickerDialog.OnDateSetListener datelineset=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                String tanggal=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                eddateline.setText(tanggal);
            }
        };
        btcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),dateset,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btenggang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(),dateset,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bdateline.setOnClickListener(new View.OnClickListener() {
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

    private void loadspinner(){
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "daftar-status.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                nama_status.add("Pilih Status");
                tenggang.add(0);
                id_status.add("null");
                try {
                    JSONObject jo=new JSONObject(response);
                    JSONArray ja=jo.getJSONArray("data");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject injo=ja.getJSONObject(i);
                        nama_status.add(injo.getString("nama_status")+" ("+injo.getString("persen_selesai")+")");
                        tenggang.add(injo.getInt("tanggang"));
                        id_status.add(injo.getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InputstatusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                ArrayAdapter<String> ardap=new ArrayAdapter<String>(InputstatusActivity.this,android.R.layout.simple_spinner_dropdown_item,nama_status);
                ardap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spstatus.setAdapter(ardap);
            }
        });
        rq.add(sr);

        spstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Date haripilih=new SimpleDateFormat("yyyy-MM-dd").parse(edtanggal.getText().toString());

                    if(tenggang.get(position)!=0) {
                        Calendar caltenggang = Calendar.getInstance();
                        caltenggang.setTime(haripilih);
                        caltenggang.add(Calendar.DAY_OF_MONTH, tenggang.get(position));
                        String tenggangset = new SimpleDateFormat("yyyy-MM-dd").format(caltenggang.getTime());

                        int datelineplus = tenggang.get(position) + 10;
                        Calendar caldateline = Calendar.getInstance();
                        caldateline.setTime(haripilih);
                        caldateline.add(Calendar.DAY_OF_MONTH, datelineplus);
                        String datelineset = new SimpleDateFormat("yyyy-MM-dd").format(caldateline.getTime());

                        id_status_send=id_status.get(position);
                        nama_status_send=nama_status.get(position);
                        edtenggang.setText(tenggangset);
                        eddateline.setText(datelineset);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void simpan(final String id_master) {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Sedang Mengirim");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "insert-status.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
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
                Toast.makeText(InputstatusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("key", Config.key);
                m.put("id_master", id_master);
                m.put("judul_pengajuan",judul_pengajuan);
                m.put("id_status", id_status_send);
                m.put("nama_status", nama_status_send);
                m.put("tanggal", edtanggal.getText().toString());
                m.put("tenggang", edtenggang.getText().toString());
                m.put("dateline", eddateline.getText().toString());
                m.put("detail",edketerangan.getText().toString());
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
                    AlertDialog.Builder adb=new AlertDialog.Builder(InputstatusActivity.this);
                    adb.setTitle("Informasi");
                    adb.setMessage("Status Berhasil Dikirim");
                    adb.setCancelable(false);
                    adb.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    adb.show();

                }else{
                    AlertDialog.Builder adb=new AlertDialog.Builder(InputstatusActivity.this);
                    adb.setTitle("Informasi");
                    adb.setMessage("Status Gagal");
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
            new Notifsender(judul_pengajuan+"/"+nama_status_send);
            String subject="Pesan Dari pengguna Aplikasi EMOREG";
            String pesan="Pengirim : "+Config.email+" ("+Config.nama+") "+"\n"+
                    "Perka : "+judul_pengajuan+"\n"+
                    "Status Terbaru  : "+nama_status_send+"\n"+
                    "Isi Detail : "+edketerangan.getText().toString();
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
