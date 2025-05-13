package com.example.qlct_n3.Model;

public class SpendingInChart {
    private Long Tien;
    private String TenDanhMuc;
    private String Icon;

    public SpendingInChart(Long Tien, String TenDanhMuc, String Icon) {
        this.Tien = Tien;
        this.TenDanhMuc = TenDanhMuc;
        this.Icon = Icon;
    }

    public Long getTien() {
        return Tien;
    }

    public void setTien(Long Tien) {
        this.Tien = Tien;
    }

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String TenDanhMuc) {
        this.TenDanhMuc = TenDanhMuc;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }
}


