package com.example.qlct_n3.View.calendar;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.Model.SpendingInCalendar;
import com.example.qlct_n3.base.DataBaseManager;

import java.util.List;

public class CalendarViewModel extends ViewModel {
    private MutableLiveData<List<Long>> _giaoDichChiThang = new MutableLiveData<>();

    public LiveData<List<Long>> giaoDichChiThang() {
        return _giaoDichChiThang;
    }

    private MutableLiveData<List<Long>> _giaoDichThuThang = new MutableLiveData<>();

    public LiveData<List<Long>> giaoDichThuThang() {
        return _giaoDichThuThang;
    }

    private MutableLiveData<List<SpendingInCalendar>> _danhmuc = new MutableLiveData<>();

    public LiveData<List<SpendingInCalendar>> danhMuc() {
        return _danhmuc;
    }

    public void getGiaoDichChiThang(Context context, int month) {
        _giaoDichChiThang.setValue(DataBaseManager.getInstance(context).getItemDAO()
                .timKiemGiaoDichChiTheoThang(month));
    }

    public void getGiaoDichThuThang(Context context, int month) {
        _giaoDichThuThang.setValue(DataBaseManager.getInstance(context).getItemDAO()
                .timKiemGiaoDichThuTheoThang(month));
    }

    public void delete(Context context, SpendingInCalendar spendingInCalendar) {
        DataBaseManager.getInstance(context).getItemDAO().xoaGiaoDich(spendingInCalendar.getId());
    }

    public void getDanhMuc(Context context, int day, int month, int year) {
        _danhmuc.setValue(DataBaseManager.getInstance(context).getItemDAO().timKiemDanhMuc(day, month, year));
    }

}
