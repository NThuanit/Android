package com.example.qlct_n3.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.qlct_n3.Model.DanhMuc;

@Entity(tableName = "NguoiDung", foreignKeys = @ForeignKey(
        entity = HoaDon.class,
        parentColumns = "Id",
        childColumns = "IdHoaDon",
        onDelete = ForeignKey.NO_ACTION
))
public class NguoiDung {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Ten")
    private String Ten;
    @ColumnInfo(name = "Sdt")
    private String Sdt;
    @ColumnInfo(name = "KhoanChi")
    private Long KhoanChi;
    @ColumnInfo(name = "TrangThai")
    private Boolean TrangThai;
    // khoa ngoai
    @ColumnInfo(name = "IdHoaDon")
    private Long IdHoaDon;

    public NguoiDung(int id, String Ten, String Sdt, Long KhoanChi, Boolean TrangThai, Long IdHoaDon) {
        this.id = id;
        this.Ten = Ten;
        this.Sdt = Sdt;
        this.KhoanChi = KhoanChi;
        this.TrangThai = TrangThai;
        this.IdHoaDon = IdHoaDon;
    }

    public NguoiDung() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String Ten) {
        this.Ten = Ten;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String Sdt) {
        this.Sdt = Sdt;
    }

    public Long getKhoanChi() {
        return KhoanChi;
    }

    public void setKhoanChi(Long KhoanChi) {
        this.KhoanChi = KhoanChi;
    }

    public Boolean getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public Long getIdHoaDon() {
        return IdHoaDon;
    }

    public void setIdHoaDon(Long IdHoaDon) {
        this.IdHoaDon = IdHoaDon;
    }
}


