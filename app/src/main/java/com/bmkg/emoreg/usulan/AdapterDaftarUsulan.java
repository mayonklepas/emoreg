package com.bmkg.emoreg.usulan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bmkg.emoreg.R;
import com.bmkg.emoreg.home.ModelDaftarUsulan;
import com.bmkg.emoreg.kendala.KendalaActivity;
import com.bmkg.emoreg.masukan.MasukanActivity;
import com.bmkg.emoreg.rapat.RapatActivity;
import com.bmkg.emoreg.status.StatusActivity;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 10/01/2018.
 */

public class AdapterDaftarUsulan extends RecyclerView.Adapter<AdapterDaftarUsulan.Holder> {

    public int expandposisition;
    ArrayList<ModelDaftarUsulan> model = new ArrayList<>();
    Context ct;
    NumberFormat nf = NumberFormat.getInstance();
    SimpleDateFormat sdf;
    boolean expand = false;

    AdapterDaftarUsulan(ArrayList<ModelDaftarUsulan> model, Context ct) {
        expandposisition = -1;
        this.model = model;
        this.ct = ct;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_daftar_usulan, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.headernama.setText(model.get(position).getJudul_peraturan());
        holder.id.setText(model.get(position).getId());
        holder.judul_peraturan.setText(model.get(position).getJudul_peraturan());
        holder.nama_unit_kerja.setText(model.get(position).getNama_unit_kerja());
        holder.tanggal_pengajuan.setText(model.get(position).getTanggal_pengajuan());
        holder.nomor_peraturan.setText(model.get(position).getNomor_peraturan());
        holder.tanggal_pembaruan.setText(model.get(position).getTanggal_pembaruan());
        holder.nama_status.setText(model.get(position).getNama_status());
        holder.persen_selesai.setText(model.get(position).getPersen_selesai() + "%");
        try {
            Date rawtanggalpengajuan = new SimpleDateFormat("yyyy-MM-dd").parse(model.get(position).getTanggal_pengajuan());
            holder.tanggal_pengajuan.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggalpengajuan));
            Date rawtanggalpembaruan = new SimpleDateFormat("yyyy-MM-dd").parse(model.get(position).getTanggal_pembaruan());
            holder.tanggal_pembaruan.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggalpembaruan));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int curpos = position;
        if (position == expandposisition) {
            holder.cardexpand.setVisibility(View.VISIBLE);
        } else {
            holder.cardexpand.setVisibility(View.GONE);
        }

        holder.bdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandposisition >= 0) {
                    int prev = expandposisition;
                    notifyItemChanged(prev);
                }
                expandposisition = holder.getPosition();
                notifyItemChanged(expandposisition);
                expand = false;

            }
        });

        String[] option = {"Status", "Masukan", "Kendala", "Rapat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ct, android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle("Pilih Operasi");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    Intent in = new Intent(ct, StatusActivity.class);
                    in.putExtra("id", model.get(position).getId());
                    in.putExtra("judul_pengajuan", model.get(position).getJudul_peraturan());
                    ct.startActivity(in);
                } else if (i == 1) {
                    Intent in = new Intent(ct, MasukanActivity.class);
                    in.putExtra("id", model.get(position).getId());
                    in.putExtra("judul_pengajuan", model.get(position).getJudul_peraturan());
                    in.putExtra("id_status",model.get(position).getId_status());
                    ct.startActivity(in);
                    /*if (model.get(position).getId_status().equals("4") ||
                            model.get(position).getId_status().equals("7") ||
                            model.get(position).getId_status().equals("8") ||
                            model.get(position).getId_status().equals("9") ||
                            model.get(position).getId_status().equals("13")) {
                        Intent in = new Intent(ct, MasukanActivity.class);
                        in.putExtra("id", model.get(position).getId());
                        in.putExtra("judul_pengajuan", model.get(position).getJudul_peraturan());
                        ct.startActivity(in);
                    }else{
                        AlertDialog.Builder adb=new AlertDialog.Builder(ct);
                        adb.setTitle("Informasi");
                        adb.setMessage("Input masukan belum bisa dilakukan");
                        adb.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        adb.show();
                    }*/

                } else if (i == 2) {
                    Intent in = new Intent(ct, KendalaActivity.class);
                    in.putExtra("id", model.get(position).getId());
                    in.putExtra("judul_pengajuan", model.get(position).getJudul_peraturan());
                    ct.startActivity(in);
                } else if (i == 3) {
                    Intent in = new Intent(ct, RapatActivity.class);
                    in.putExtra("id", model.get(position).getId());
                    in.putExtra("judul_pengajuan", model.get(position).getJudul_peraturan());
                    ct.startActivity(in);
                }
            }
        });
        final AlertDialog a = builder.create();
        holder.boperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView id, judul_peraturan, nama_unit_kerja, tanggal_pengajuan,
                nomor_peraturan, tanggal_pembaruan, nama_status, persen_selesai, headernama;
        LinearLayout llc;
        CardView cardexpand;
        Button boperasi;
        ImageView bdetail;

        public Holder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            judul_peraturan = (TextView) itemView.findViewById(R.id.judul_peraturan);
            nama_unit_kerja = (TextView) itemView.findViewById(R.id.nama_unit_kerja);
            tanggal_pengajuan = (TextView) itemView.findViewById(R.id.tanggal_pengajuan);
            nomor_peraturan = (TextView) itemView.findViewById(R.id.nomor_peraturan);
            tanggal_pembaruan = (TextView) itemView.findViewById(R.id.tanggal_pembaruan);
            nama_status = (TextView) itemView.findViewById(R.id.nama_status);
            persen_selesai = (TextView) itemView.findViewById(R.id.persen_selesai);
            headernama = (TextView) itemView.findViewById(R.id.headernama);
            llc = (LinearLayout) itemView.findViewById(R.id.llc);
            bdetail = (ImageView) itemView.findViewById(R.id.bdetail);
            boperasi = (Button) itemView.findViewById(R.id.boperasi);
            cardexpand = (CardView) itemView.findViewById(R.id.cardexpand);

        }
    }


}
