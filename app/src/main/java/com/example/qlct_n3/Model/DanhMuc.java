package com.example.qlct_n3.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "DanhMuc")
public class DanhMuc {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "Icon")
    private String icon;
    @ColumnInfo(name = "TenDanhMuc")
    private String tenDanhMuc;
    @ColumnInfo(name = "ThuChi")
    private Boolean thuChi;

    public DanhMuc() {
    }

    public DanhMuc(int Id, String icon, String tenDanhMuc, Boolean thuChi) {
        this.Id = Id;
        this.icon = icon;
        this.tenDanhMuc = tenDanhMuc;
        this.thuChi = thuChi;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public Boolean getThuChi() {
        return thuChi;
    }

    public void setThuChi(Boolean thuChi) {
        this.thuChi = thuChi;
    }
}


