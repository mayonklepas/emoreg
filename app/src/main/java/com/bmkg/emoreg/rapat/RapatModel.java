package com.bmkg.emoreg.rapat;

/**
 * Created by Minami on 9/17/2018.
 */

public class RapatModel {
    String no,tanggal,judul,keterangan,attachment;

    public RapatModel(String no, String tanggal, String judul, String keterangan, String attachment) {
        this.no = no;
        this.tanggal = tanggal;
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
