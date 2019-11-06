package com.bmkg.emoreg.home;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmkg.emoreg.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Minami on 10/01/2018.
 */

public class AdapterHome extends RecyclerView.Adapter {

    ArrayList<Combined> model=new ArrayList<>();
    Context ct;
    DataPoint[] dp;
    private static int graph=0;
    private static int status=1;
    private static int rapat=2;
    private static int masukan=3;
    private static int spacer=4;
    private static int spacer2=5;



    AdapterHome(ArrayList<Combined> model, Context ct){
        this.model=model;
        this.ct=ct;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lin=LayoutInflater.from(parent.getContext());
        View v;
        if(viewType==graph){
            v=lin.inflate(R.layout.adapter_graph,parent,false);
            return new Holdergraph(v);
        }else if (viewType==status){
            v=lin.inflate(R.layout.adapter_status,parent,false);
            return new Holderstatus(v);
        }else if (viewType==rapat){
            v=lin.inflate(R.layout.adapter_rapat,parent,false);
            return new Holderrapat(v);
        }else if (viewType==masukan){
            v=lin.inflate(R.layout.adapter_masukan,parent,false);
            return new Holdermasukan(v);
        }else if(viewType==spacer){
            v=lin.inflate(R.layout.spacer,parent,false);
            return new Holderspacer(v);
        }else if(viewType==spacer2){
            v=lin.inflate(R.layout.spacer,parent,false);
            return new Holderspacer2(v);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Combined combines=model.get(position);
        if(holder instanceof Holdergraph){
            Holdergraph headhold=(Holdergraph) holder;
            ModelGraph graph=(ModelGraph) combines;
            try {

                 dp=new DataPoint[graph.getJa().length()+1];
                 dp[0]=new DataPoint(0,0);
                for (int i = 0; i < graph.getJa().length()+1; i++) {
                        JSONObject jo=graph.getJa().getJSONObject(i);
                        double y=jo.getDouble("y");
                        dp[i+1] = new DataPoint(i+1,y);
                    }

                }
                catch (JSONException e) {
                e.printStackTrace();
                }
                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(dp);
                series.setSpacing(10);
                series.setAnimated(true);
                series.setTitle("test");
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                    }
                });
                headhold.bar.addSeries(series);

                LineGraphSeries<DataPoint> lineseries = new LineGraphSeries<>(dp);
                lineseries.setAnimated(true);
                lineseries.setThickness(5);
                lineseries.setDrawBackground(true);
                headhold.line.addSeries(lineseries);

        }else if(holder instanceof Holderstatus){
            Holderstatus listhold=(Holderstatus) holder;
            ModelStatus m=(ModelStatus) combines;
            listhold.no.setText(m.getNo());
            listhold.lheader.setText(m.getJudul_peraturan());
            listhold.lstatus.setText(m.getNama_status()+" "+m.getPersen_status()+"%");
            try {
                Date rawtanggalpembaruan=new SimpleDateFormat("yyyy-MM-dd").parse(m.getTanggal_pembaruan());
                listhold.ltanggal.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggalpembaruan));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(holder instanceof Holderrapat){
            Holderrapat listhold=(Holderrapat) holder;
            ModelRapat m=(ModelRapat) combines;
            listhold.no.setText(m.getNo());
            listhold.lheader.setText(m.getJudul_peraturan());
            listhold.ldetail.setText(m.getJudul_rapat());
            try {
                Date rawtanggalpembaruan=new SimpleDateFormat("yyyy-MM-dd").parse(m.getTanggal());
                listhold.ltanggal.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggalpembaruan));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(holder instanceof Holdermasukan){
            Holdermasukan listhold=(Holdermasukan) holder;
            ModelMasukan m=(ModelMasukan) combines;
            listhold.no.setText(m.getNo());
            listhold.lheader.setText(m.getJudul_peraturan());
            listhold.lnama.setText(m.getNama());
            listhold.ldetail.setText(m.getJudul());
            try {
                Date rawtanggalpembaruan=new SimpleDateFormat("yyyy-MM-dd").parse(m.getTanggal());
                listhold.ltanggal.setText(new SimpleDateFormat("dd/MM/yyyy").format(rawtanggalpembaruan));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(holder instanceof Holderspacer){
            Holderspacer space=(Holderspacer) holder;
            ModelSpacer m=(ModelSpacer) combines;
            space.text.setText("DAFTAR RAPAT");

        }else if(holder instanceof Holderspacer2){
            Holderspacer2 space=(Holderspacer2) holder;
            ModelSpacer2 m=(ModelSpacer2) combines;
            space.text.setText("DAFTAR MASUKAN");

        }


    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    @Override
    public int getItemViewType(int position) {
        Combined combines=model.get(position);
        if(combines instanceof ModelGraph){
            return graph;
        }else if(combines instanceof ModelStatus){
            return status;
        }else if(combines instanceof ModelRapat){
            return rapat;
        }else if(combines instanceof ModelMasukan){
            return masukan;
        }else if(combines instanceof ModelSpacer){
            return spacer;
        }else {
            return spacer2;
        }
    }

    public class Holdergraph extends RecyclerView.ViewHolder{
        GraphView bar,line;
        public Holdergraph(View itemView) {
            super(itemView);
            bar=(GraphView) itemView.findViewById(R.id.barchart);
            line=(GraphView) itemView.findViewById(R.id.linechart);

        }
    }


    public class Holderstatus extends RecyclerView.ViewHolder{

        TextView ltanggal,lheader,lstatus,no;
        public Holderstatus(View itemView) {
            super(itemView);
            ltanggal=(TextView) itemView.findViewById(R.id.ltanggal);
            lheader=(TextView) itemView.findViewById(R.id.lheader);
            lstatus=(TextView) itemView.findViewById(R.id.lstatus);
            no=(TextView) itemView.findViewById(R.id.no);


        }
    }


    public class Holderrapat extends RecyclerView.ViewHolder{

        TextView ltanggal,lheader,ldetail,no;
        public Holderrapat(View itemView) {
            super(itemView);
            ltanggal=(TextView) itemView.findViewById(R.id.ltanggal);
            lheader=(TextView) itemView.findViewById(R.id.lheader);
            ldetail=(TextView) itemView.findViewById(R.id.ldetail);
            no=(TextView) itemView.findViewById(R.id.no);


        }
    }


    public class Holdermasukan extends RecyclerView.ViewHolder{
        TextView ltanggal,lheader,lnama,ldetail,no;
        public Holdermasukan(View itemView) {
            super(itemView);
            ltanggal=(TextView) itemView.findViewById(R.id.ltanggal);
            lheader=(TextView) itemView.findViewById(R.id.lheader);
            lnama=(TextView) itemView.findViewById(R.id.lnama);
            ldetail=(TextView) itemView.findViewById(R.id.ldetail);
            no=(TextView) itemView.findViewById(R.id.no);


        }

    }

    public class Holderspacer extends RecyclerView.ViewHolder{
        TextView text;
        public Holderspacer(View itemView) {
            super(itemView);
            text=(TextView) itemView.findViewById(R.id.text);

        }

    }

    public class Holderspacer2 extends RecyclerView.ViewHolder{
        TextView text;
        public Holderspacer2(View itemView) {
            super(itemView);
            text=(TextView) itemView.findViewById(R.id.text);

        }

    }




}
