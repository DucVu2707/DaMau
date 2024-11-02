package fpl.datn.damau_ph21672.model;

public class PhieuMuon {
    private int maPM;
    private String ngayMuon;
    private int trangThai;
    private String tenND;
    private int maND;

    public int getMaND() {
        return maND;
    }

    public PhieuMuon(int maPM, String ngayMuon, int trangThai, int maND) {
        this.maPM = maPM;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.maND = maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public PhieuMuon(int maPM, String ngayMuon, int trangThai, String tenND, int maND) {
        this.maPM = maPM;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.tenND = tenND;
        this.maND = maND;
    }

    public PhieuMuon() {
    }

    public PhieuMuon(int maPM, String ngayMuon, int trangThai, String tenND) {
        this.maPM = maPM;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
        this.tenND = tenND;
    }



    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenND() {
        return tenND;
    }

    public void setTenND(String tenND) {
        this.tenND = tenND;
    }
}
