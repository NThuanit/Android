package com.example.qlct_n3.Model;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "HoaDon")
public class HoaDon {
    @PrimaryKey(autoGenerate = true)
    private Long Id;

    @ColumnInfo(name = "TongTien")
    private Long TongTien;

    @ColumnInfo(name = "Mota")
    private String Mota;

    @ColumnInfo(name = "SoLuong")
    private Integer SoLuong;

    @ColumnInfo(name = "Ngay")
    private String Ngay;

    public HoaDon() {
    }

    public HoaDon( Long TongTien, String Mota, Integer SoLuong, String Ngay) {
        this.TongTien = TongTien;
        this.Mota = Mota;
        this.SoLuong = SoLuong;
        this.Ngay = Ngay;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Long getTongTien() {
        return TongTien;
    }

    public void setTongTien(Long TongTien) {
        this.TongTien = TongTien;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    public Integer getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(Integer SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String Ngay) {
        this.Ngay = Ngay;
    }
}


