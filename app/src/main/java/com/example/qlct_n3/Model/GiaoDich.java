package com.example.qlct_n3.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.qlct_n3.Model.DanhMuc;

@Entity(tableName = "GiaoDich", foreignKeys = @ForeignKey(
        entity = DanhMuc.class,
        parentColumns = "Id",
        childColumns = "IdDanhMuc",
        onDelete = ForeignKey.NO_ACTION
))
public class GiaoDich {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "NgayGiaoDich")
    private int ngayGiaoDich;
    @ColumnInfo(name = "ThangGiaoDich")
    private int thangGiaoDich;
    @ColumnInfo(name = "NamGiaoDich")
    private int namGiaoDich;
    @ColumnInfo(name = "Tien")
    private Long tien;
    @ColumnInfo(name = "GhiChu")
    private String ghiChu;
    @ColumnInfo(name = "ThuChi")
    private Boolean thuChi;
    // khoa ngoai
    @ColumnInfo(name = "IdDanhMuc")
    private int idDanhMuc;

    public GiaoDich() {
    }

    public GiaoDich(int Id, int ngayGiaoDich, int thangGiaoDich, int namGiaoDich, Long tien, String ghiChu, Boolean thuChi, int idDanhMuc) {
        this.Id = Id;
        this.ngayGiaoDich = ngayGiaoDich;
        this.thangGiaoDich = thangGiaoDich;
        this.namGiaoDich = namGiaoDich;
        this.tien = tien;
        this.ghiChu = ghiChu;
        this.thuChi = thuChi;
        this.idDanhMuc = idDanhMuc;
    }
    public GiaoDich( int ngayGiaoDich, int thangGiaoDich, int namGiaoDich, Long tien, String ghiChu, Boolean thuChi) {
        this.Id = Id;
        this.ngayGiaoDich = ngayGiaoDich;
        this.thangGiaoDich = thangGiaoDich;
        this.namGiaoDich = namGiaoDich;
        this.tien = tien;
        this.ghiChu = ghiChu;
        this.thuChi = thuChi;
        this.idDanhMuc = idDanhMuc;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(int ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public int getThangGiaoDich() {
        return thangGiaoDich;
    }

    public void setThangGiaoDich(int thangGiaoDich) {
        this.thangGiaoDich = thangGiaoDich;
    }

    public int getNamGiaoDich() {
        return namGiaoDich;
    }

    public void setNamGiaoDich(int namGiaoDich) {
        this.namGiaoDich = namGiaoDich;
    }

    public Long getTien() {
        return tien;
    }

    public void setTien(Long tien) {
        this.tien = tien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Boolean getThuChi() {
        return thuChi;
    }

    public void setThuChi(Boolean thuChi) {
        this.thuChi = thuChi;
    }

    public int getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(int idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }
}


