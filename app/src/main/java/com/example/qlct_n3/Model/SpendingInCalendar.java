package com.example.qlct_n3.Model;

public class SpendingInCalendar {
    public String getIcon() {
        return Icon;
    }

    public void setIcon(String Icon) {
        this.Icon = Icon;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    private Integer Id;
    private String Icon;
    private String TenDanhMuc;
    private Long Tien;
    private String GhiChu;
    private Boolean ThuChi;
    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String TenDanhMuc) {
        this.TenDanhMuc = TenDanhMuc;
    }
    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    public SpendingInCalendar(Integer Id,String Icon,String TenDanhMuc, Long Tien,String GhiChu, Boolean ThuChi) {
        this.Id = Id;
        this.Icon = Icon;
        this.TenDanhMuc = TenDanhMuc;
        this.Tien = Tien;
        this.GhiChu = GhiChu;
        this.ThuChi = ThuChi;
    }

    public Long getTien() {
        return Tien;
    }

    public void setTien(Long Tien) {
        this.Tien = Tien;
    }

    public Boolean getThuChi() {
        return ThuChi;
    }

    public void setThuChi(Boolean ThuChi) {
        this.ThuChi = ThuChi;
    }
}


