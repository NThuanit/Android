package com.example.qlct_n3.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.Model.SpendingInCalendar;
import com.example.qlct_n3.Model.SpendingInChart;

import java.util.List;

@Dao
public interface Daoo {
    // GIAO DỊCH
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long themNguoiGiaoDich(GiaoDich giaoDich);

    @Query("select * from GiaoDich where NgayGiaoDich = :ngay and ThangGiaoDich =:thang and NamGiaoDich = :nam")
    public List<GiaoDich> timKiemGiaoDichChiTheoNgayThangNam(int ngay, int thang, int nam);

    @Query("select * from GiaoDich where NgayGiaoDich = :ngay and ThuChi = 1")
    public List<GiaoDich> timKiemGiaoDichChiTheoNgay(int ngay);

    @Query("select Tien from GiaoDich where ThangGiaoDich = :thang and ThuChi = 1")
    public List<Long> timKiemGiaoDichChiTheoThang(int thang);

    @Query("select * from GiaoDich where NamGiaoDich = :nam and ThuChi = 1")
    public List<GiaoDich> timKiemGiaoDichChiTheoNam(int nam);

    @Query("select * from GiaoDich where NgayGiaoDich = :ngay and ThuChi = 0")
    public List<GiaoDich> timKiemGiaoDichThuTheoNgay(int ngay);

    @Query("select Tien from GiaoDich where ThangGiaoDich = :thang and ThuChi = 0")
    public List<Long> timKiemGiaoDichThuTheoThang(int thang);

    @Query("select * from GiaoDich where NamGiaoDich = :nam and ThuChi = 0")
    public List<GiaoDich> timKiemGiaoDichThuTheoNam(int nam);

    @Query("select * from GiaoDich where Id = :id")
    public GiaoDich timKiemGiaoDichTheoId(Long id);

    @Query("SELECT GiaoDich.Tien, DanhMuc.TenDanhMuc, DanhMuc.Icon FROM GiaoDich INNER JOIN DanhMuc ON GiaoDich.IdDanhMuc = DanhMuc.Id WHERE GiaoDich.ThangGiaoDich = :thang AND GiaoDich.ThuChi = 1")
    public List<SpendingInChart> timKiemGiaoDichChiBieuDo(int thang);

    @Query("SELECT GiaoDich.Tien, DanhMuc.TenDanhMuc, DanhMuc.Icon FROM GiaoDich INNER JOIN DanhMuc ON GiaoDich.IdDanhMuc = DanhMuc.Id where GiaoDich.ThangGiaoDich = :thang and GiaoDich.ThuChi = 0")
    public List<SpendingInChart> timKiemGiaoDichThuBieuDo(int thang);

    @Update
    public void capNhatGiaoDich(GiaoDich giaoDich);

    @Query("Delete from GiaoDich where Id = :id")
    public void xoaGiaoDich(int id);

    // DANH MỤC
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long themDanhMuc(DanhMuc danhMuc);

    @Query("select  GiaoDich.Id, DanhMuc.Icon,DanhMuc.TenDanhMuc,GiaoDich.Tien,GiaoDich.GhiChu,GiaoDich.ThuChi from GiaoDich inner join DanhMuc on GiaoDich.IdDanhMuc = DanhMuc.Id where GiaoDich.NgayGiaoDich = :ngay and GiaoDich.ThangGiaoDich =:thang and GiaoDich.NamGiaoDich = :nam ")
    public List<SpendingInCalendar> timKiemDanhMuc(int ngay, int thang, int nam);

    @Query("select * from DanhMuc where ThuChi = 1")
    public List<DanhMuc> timKiemDanhMucChi();

    @Query("select * from DanhMuc where ThuChi = 0")
    public List<DanhMuc> timKiemDanhMucThu();

    @Delete
    public Integer xoaDanhMuc(DanhMuc danhMuc);
    // HÓA ĐƠN

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Long themHoaDon(HoaDon hoaDon);

    @Query("SELECT * from HoaDon where id = :id")
    public HoaDon timKiemHoaDon(Long id);

    @Query("SELECT * from HoaDon")
    public List<HoaDon> getAllHoaDon();

    @Delete
    public Integer xoaHoaDon(HoaDon hoaDon);
    //NGƯỜI DÙNG

    @Query("SELECT * from NguoiDung where IdHoaDon = :id")
    public List<NguoiDung> timKiemNguoiDungTheoHoaDon(Long id);

    @Query("Delete from NguoiDung where IdHoaDon = :id")
    public Integer xoaNguoiDung(Long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void themNguoiDungs(List<NguoiDung> nguoiDungs);

    @Update
    public void chinhSuaNguoiDung(NguoiDung nguoiDung);
}