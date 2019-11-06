package com.bmkg.emoreg.home;

/**
 * Created by Minami on 7/1/2018.
 */

public class ModelDaftarUsulan {
    String id,judul_peraturan,nama_unit_kerja,tanggal_pengajuan,nomor_peraturan,tanggal_pembaruan,nama_status,persen_selesai,id_status;

    public ModelDaftarUsulan(String id, String judul_peraturan, String nama_unit_kerja, String tanggal_pengajuan, String nomor_peraturan, String tanggal_pembaruan, String nama_status, String persen_selesai, String id_status) {
        this.id = id;
        this.judul_peraturan = judul_peraturan;
        this.nama_unit_kerja = nama_unit_kerja;
        this.tanggal_pengajuan = tanggal_pengajuan;
        this.nomor_peraturan = nomor_peraturan;
        this.tanggal_pembaruan = tanggal_pembaruan;
        this.nama_status = nama_status;
        this.persen_selesai = persen_selesai;
        this.id_status = id_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul_peraturan() {
        return judul_peraturan;
    }

    public void setJudul_peraturan(String judul_peraturan) {
        this.judul_peraturan = judul_peraturan;
    }

    public String getNama_unit_kerja() {
        return nama_unit_kerja;
    }

    public void setNama_unit_kerja(String nama_unit_kerja) {
        this.nama_unit_kerja = nama_unit_kerja;
    }

    public String getTanggal_pengajuan() {
        return tanggal_pengajuan;
    }

    public void setTanggal_pengajuan(String tanggal_pengajuan) {
        this.tanggal_pengajuan = tanggal_pengajuan;
    }

    public String getNomor_peraturan() {
        return nomor_peraturan;
    }

    public void setNomor_peraturan(String nomor_peraturan) {
        this.nomor_peraturan = nomor_peraturan;
    }

    public String getTanggal_pembaruan() {
        return tanggal_pembaruan;
    }

    public void setTanggal_pembaruan(String tanggal_pembaruan) {
        this.tanggal_pembaruan = tanggal_pembaruan;
    }

    public String getNama_status() {
        return nama_status;
    }

    public void setNama_status(String nama_status) {
        this.nama_status = nama_status;
    }

    public String getPersen_selesai() {
        return persen_selesai;
    }

    public void setPersen_selesai(String persen_selesai) {
        this.persen_selesai = persen_selesai;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }
}
