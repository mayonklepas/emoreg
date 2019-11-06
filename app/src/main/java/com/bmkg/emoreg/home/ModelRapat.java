package com.bmkg.emoreg.home;

/**
 * Created by Minami on 7/25/2018.
 */

public class ModelRapat extends Combined {

    String no,judul_peraturan,tanggal,judul_rapat;

    public ModelRapat(String no, String judul_peraturan, String tanggal, String judul_rapat) {
        this.no = no;
        this.judul_peraturan = judul_peraturan;
        this.tanggal = tanggal;
        this.judul_rapat = judul_rapat;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getJudul_peraturan() {
        return judul_peraturan;
    }

    public void setJudul_peraturan(String judul_peraturan) {
        this.judul_peraturan = judul_peraturan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul_rapat() {
        return judul_rapat;
    }

    public void setJudul_rapat(String judul_rapat) {
        this.judul_rapat = judul_rapat;
    }
}
