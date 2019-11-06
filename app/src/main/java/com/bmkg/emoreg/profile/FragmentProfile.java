package com.bmkg.emoreg.profile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bmkg.emoreg.R;
import com.bmkg.emoreg.lainlain.Config;

/**
 * Created by Minami on 7/1/2018.
 */

public class FragmentProfile extends Fragment {

    TextView user,email,nip,nama,jabatan,nama_unit_kerja;
    Button btlogout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        user=(TextView) v.findViewById(R.id.user);
        email=(TextView) v.findViewById(R.id.email);
        nip=(TextView) v.findViewById(R.id.nip);
        nama=(TextView) v.findViewById(R.id.nama);
        jabatan=(TextView) v.findViewById(R.id.jabatan);
        nama_unit_kerja=(TextView) v.findViewById(R.id.nama_unit_kerja);
        btlogout=(Button) v.findViewById(R.id.btlogout);
        user.setText(Config.users);
        email.setText(Config.email);
        nip.setText(Config.nip);
        nama.setText(Config.nama);
        jabatan.setText(Config.jabatan);
        nama_unit_kerja.setText(Config.nama_unit_kerja);
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                adb.setTitle("Konfirmasi");
                adb.setMessage("Yakin Ingin Keluar?");
                adb.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp= getActivity().getSharedPreferences("userlog", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.remove("username");
                        edit.remove("passowrd");
                        edit.commit();
                        System.exit(0);
                    }
                });
                adb.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                adb.show();

            }
        });
        return v;
    }



}
