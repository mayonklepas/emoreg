package com.bmkg.emoreg.home;

/**
 * Created by Minami on 7/1/2018.
 */

public class ModelStatus extends Combined{
    String tanggal_pembaruan,judul_peraturan,nama_status,persen_status,no;

    public ModelStatus(String tanggal_pembaruan, String judul_peraturan, String nama_status, String persen_status, String no) {
        this.tanggal_pembaruan = tanggal_pembaruan;
        this.judul_peraturan = judul_peraturan;
        this.nama_status = nama_status;
        this.persen_status = persen_status;
        this.no = no;
    }

    public String getTanggal_pembaruan() {
        return tanggal_pembaruan;
    }

    public void setTanggal_pembaruan(String tanggal_pembaruan) {
        this.tanggal_pembaruan = tanggal_pembaruan;
    }

    public String getJudul_peraturan() {
        return judul_peraturan;
    }

    public void setJudul_peraturan(String judul_peraturan) {
        this.judul_peraturan = judul_peraturan;
    }

    public String getNama_status() {
        return nama_status;
    }

    public void setNama_status(String nama_status) {
        this.nama_status = nama_status;
    }

    public String getPersen_status() {
        return persen_status;
    }

    public void setPersen_status(String persen_status) {
        this.persen_status = persen_status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
