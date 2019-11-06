package com.bmkg.emoreg.home;

/**
 * Created by Minami on 7/25/2018.
 */

public class ModelMasukan extends Combined {

    String no,judul_peraturan,tanggal,nama,judul;

    public ModelMasukan(String no, String judul_peraturan, String tanggal, String nama, String judul) {
        this.no = no;
        this.judul_peraturan = judul_peraturan;
        this.tanggal = tanggal;
        this.nama = nama;
        this.judul = judul;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }
}
