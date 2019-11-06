package com.bmkg.emoreg.lainlain;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bmkg.emoreg.MainActivity;
import com.bmkg.emoreg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edusername, edpassword;
    Button btlogin;
    int jumlahdata;
    public String id = "";
    public String nip = "";
    public String nama = "";
    public String eselon = "";
    public String parent = "";
    public String upt = "";
    public String nama_unit_kerja = "";
    public String email = "";
    public String password = "";
    public  String jabatan ="";
    SharedPreferences sp;
    TextView tvfooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edusername = (EditText) findViewById(R.id.edusername);
        edpassword = (EditText) findViewById(R.id.edpassword);
        btlogin = (Button) findViewById(R.id.btlogin);
        sp=getSharedPreferences("userlog", Context.MODE_PRIVATE);
        String username=sp.getString("username","");
        String password=sp.getString("password","");
        login(username,password);
        tvfooter=(TextView) findViewById(R.id.tvfooter);
        String tahun=new SimpleDateFormat("yyyy").format(new Date());
        tvfooter.setText("Copyright \u00A9 "+tahun+" BMKG");
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor edit=sp.edit();
                edit.putString("username",edusername.getText().toString());
                edit.putString("password",edpassword.getText().toString());
                edit.commit();
                login(edusername.getText().toString(),edpassword.getText().toString());
            }
        });
    }

    private void login(final String username, final String password) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Sedang Memuat Data");
        pd.setCancelable(false);
        pd.show();
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, Config.url + "login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("datalogin");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jodata = ja.getJSONObject(i);
                        jumlahdata = jodata.getInt("jumlah");
                        id = jodata.getString("id");
                        nip = jodata.getString("nip");
                        nama = jodata.getString("nama");
                        eselon = jodata.getString("eselon");
                        parent = jodata.getString("parent");
                        nama_unit_kerja = jodata.getString("nama_unit_kerja");
                        email = jodata.getString("email");
                        jabatan = jodata.getString("jabatan");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map m = new HashMap();
                m.put("key", Config.key);
                m.put("username",username);
                m.put("password", password);
                return m;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(1000 * 3, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        rq.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                pd.dismiss();
                if (jumlahdata == 1) {
                    Config.id = id;
                    Config.nip = nip;
                    Config.nama = nama;
                    Config.eselon = eselon;
                    Config.parent = parent;
                    Config.upt = upt;
                    Config.nama_unit_kerja = nama_unit_kerja;
                    Config.email = email;
                    Config.password=password;
                    Config.users=username;
                    Config.jabatan=jabatan;
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    i.putExtra("key","login");
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Username Atau Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rq.add(sr);

    }
}
