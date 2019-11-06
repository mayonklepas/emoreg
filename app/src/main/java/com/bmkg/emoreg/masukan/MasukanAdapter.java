package com.bmkg.emoreg.masukan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bmkg.emoreg.R;
import com.bmkg.emoreg.masukan.MasukanModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 9/17/2018.
 */

public class MasukanAdapter extends RecyclerView.Adapter {
    ArrayList<MasukanModel> model=new ArrayList<>();
    Context ct;
    public static int header=0;
    public static int content=1;
    public static String id_master,judul_pengajuan;

    public MasukanAdapter(ArrayList<MasukanModel> model, Context ct, String id_master,String judul_pengajuan) {
        this.model = model;
        this.ct = ct;
        this.id_master=id_master;
        this.judul_pengajuan=judul_pengajuan;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lin=LayoutInflater.from(parent.getContext());
        View v;
        if (viewType==header){
            v=lin.inflate(R.layout.header,parent,false);
            return new Header(v);
        }else{
            v=lin.inflate(R.layout.adapter_masukan_in,parent,false);
            return new Content(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Header){
            Header h=(Header) holder;
            h.thead.setText("Daftar Data Masukan "+ judul_pengajuan);
        }else if(holder instanceof Content){
            Content c=(Content) holder;
            c.no.setText(model.get(position).getNo());
            c.lnama_pegawai.setText(model.get(position).getNama_pegawai());
            c.lunit_kerja.setText(model.get(position).getUnit_kerja());
            c.ljudul.setText(model.get(position).getJudul());
            c.lketerangan.setText(model.get(position).getKeterangan());
            try {
                Date rawtanggalpembaruan=new SimpleDateFormat("yyyy-MM-dd").parse(model.get(position).getTanggal());
                c.ltanggal.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggalpembaruan));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position <= 0){
            return header;
        }else{
            return content;
        }
    }

    public class Header extends RecyclerView.ViewHolder{
        TextView thead;
        public Header(View itemView) {
            super(itemView);
            thead=(TextView) itemView.findViewById(R.id.thead);
        }
    }

    public class Content extends RecyclerView.ViewHolder{
        TextView no,ltanggal,lnama_pegawai,lunit_kerja,ljudul,lketerangan,lattachment;
        public Content(View itemView) {
            super(itemView);
            ltanggal=(TextView) itemView.findViewById(R.id.ltanggal);
            lnama_pegawai=(TextView) itemView.findViewById(R.id.lnamapegawai);
            ltanggal=(TextView) itemView.findViewById(R.id.ltanggal);
            lunit_kerja=(TextView) itemView.findViewById(R.id.lunit_kerja);
            ljudul=(TextView) itemView.findViewById(R.id.ljudul);
            lketerangan=(TextView) itemView.findViewById(R.id.lketerangan);
            no=(TextView) itemView.findViewById(R.id.no);
        }

    }

}
