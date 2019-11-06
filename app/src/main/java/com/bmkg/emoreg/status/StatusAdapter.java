package com.bmkg.emoreg.status;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmkg.emoreg.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 9/17/2018.
 */

public class StatusAdapter extends RecyclerView.Adapter {
    ArrayList<StatusModel> model=new ArrayList<>();
    Context ct;
    public static int header=0;
    public static int content=1;
    String judul_pengajuan;

    public StatusAdapter(ArrayList<StatusModel> model, Context ct,String judul_pengajuan) {
        this.model = model;
        this.ct = ct;
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
            v=lin.inflate(R.layout.adapter_status_in,parent,false);
            return new Content(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof Header){
            Header h=(Header) holder;
            h.thead.setText("Daftar Data Status "+judul_pengajuan);
        }else if(holder instanceof Content){
            Content c=(Content) holder;
            c.no.setText(model.get(position).getNo());
            c.lstatus.setText(model.get(position).getNama_status()+" ( "+model.get(position).getPersen_status()+"% )");
            try {
                Date rawtanggalpembaruan=new SimpleDateFormat("yyyy-MM-dd").parse(model.get(position).getTanggal_pembaruan());
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
        if(position==0){
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
        TextView ltanggal,lheader,lstatus,no;
        public Content(View itemView) {
            super(itemView);
            ltanggal=(TextView) itemView.findViewById(R.id.ltanggal);
            lstatus=(TextView) itemView.findViewById(R.id.lstatus);
            no=(TextView) itemView.findViewById(R.id.no);
        }

    }

}
