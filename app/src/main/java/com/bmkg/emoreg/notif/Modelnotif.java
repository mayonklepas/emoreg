package com.bmkg.emoreg.notif;

/**
 * Created by Minami on 7/20/2018.
 */

public class Modelnotif {
    String id,tanggal,judul,pesan;

    public Modelnotif(String id, String tanggal, String judul, String pesan) {
        this.id = id;
        this.tanggal = tanggal;
        this.judul = judul;
        this.pesan = pesan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
