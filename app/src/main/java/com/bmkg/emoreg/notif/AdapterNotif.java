package com.bmkg.emoreg.notif;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmkg.emoreg.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 10/01/2018.
 */

public class AdapterNotif extends RecyclerView.Adapter<AdapterNotif.Holder> {

    ArrayList<Modelnotif> model=new ArrayList<>();
    Context ct;
    public int expandposisition;
    boolean expand=false;

    AdapterNotif(ArrayList<Modelnotif> model, Context ct, int expandposisition){
        this.expandposisition=expandposisition;
        this.model=model;
        this.ct=ct;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notif,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        String header="";
        String detail="";
        String perka="";
        String pesan="";
        try{
            header=model.get(position).getPesan().split(":")[0];
            detail=model.get(position).getPesan().split(":")[1];
            perka=detail.split("/")[0];
            pesan=detail.split("/")[1];
        }catch (Exception ex){
            //Toast.makeText(ct, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        String head[]=header.split("/");
        String headjudul="";
        try {
            if(head.length>0){
                headjudul=head[1]+" : "+head[0];
            }else{
                headjudul=header;
            }
        }catch(Exception ex){
            headjudul=header;
        }

        holder.headernama.setText(headjudul+" - "+model.get(position).getJudul());
        holder.judul.setText(perka.trim());
        holder.pesan.setText(pesan);
        try {
            Date rawtanggal=new SimpleDateFormat("yyyy-MM-dd").parse(model.get(position).getTanggal());
            holder.tanggal.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(position==expandposisition){
            holder.cardexpand.setVisibility(View.VISIBLE);
        }else{
            holder.cardexpand.setVisibility(View.GONE);
        }

        holder.bdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandposisition>=0) {
                    int prev = expandposisition;
                    notifyItemChanged(prev);
                }
                expandposisition=holder.getPosition();
                notifyItemChanged(expandposisition);
                expand=false;

            }
        });
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView tanggal,judul,pesan,headernama;
        LinearLayout llc;
        CardView cardexpand;
        ImageView bdetail;
        public Holder(View itemView) {
            super(itemView);
            tanggal=(TextView) itemView.findViewById(R.id.tanggal);
            judul=(TextView) itemView.findViewById(R.id.judul);
            pesan=(TextView) itemView.findViewById(R.id.pesan);
            headernama=(TextView) itemView.findViewById(R.id.headernama);
            llc=(LinearLayout) itemView.findViewById(R.id.llc);
            bdetail=(ImageView) itemView.findViewById(R.id.bdetail);
            cardexpand=(CardView) itemView.findViewById(R.id.cardexpand);

        }
    }


}
