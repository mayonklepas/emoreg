package com.bmkg.emoreg.masukan;

/**
 * Created by Minami on 9/17/2018.
 */

public class MasukanModel {
    String no,tanggal,nama_pegawai,unit_kerja,judul,keterangan,attachment;

    public MasukanModel(String no, String tanggal, String nama_pegawai, String unit_kerja, String judul, String keterangan, String attachment) {
        this.no = no;
        this.tanggal = tanggal;
        this.nama_pegawai = nama_pegawai;
        this.unit_kerja = unit_kerja;
        this.judul = judul;
        this.keterangan = keterangan;
        this.attachment = attachment;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getUnit_kerja() {
        return unit_kerja;
    }

    public void setUnit_kerja(String unit_kerja) {
        this.unit_kerja = unit_kerja;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
