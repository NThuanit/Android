package com.example.qlct_n3.View.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.base.DataBaseManager;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<DanhMuc>> _danhMucThu= new MutableLiveData<>();
    public LiveData<List<DanhMuc>> danhMucThu(){
        return _danhMucThu;
    }
    private MutableLiveData<List<DanhMuc>> _danhMucChi= new MutableLiveData<>();
    public LiveData<List<DanhMuc>> danhMucChi(){
        return _danhMucChi;
    }
    private MutableLiveData<Long> _giaoDich= new MutableLiveData<>();
    public LiveData<Long> giaoDich(){
        return _giaoDich;
    }
    private MutableLiveData<GiaoDich> _gd= new MutableLiveData<>();
    public LiveData<GiaoDich> gd(){
        return _gd;
    }

    public void _getDanhMucThu(Context context){
        _danhMucThu.setValue(DataBaseManager.getInstance(context).getItemDAO().timKiemDanhMucThu());
    }
    public void _getDanhMucChi(Context context){
        _danhMucChi.setValue(DataBaseManager.getInstance(context).getItemDAO().timKiemDanhMucChi());
    }
    public void set_giaoDich(Context context,GiaoDich giaoDich){
        _giaoDich.setValue(DataBaseManager.getInstance(context).getItemDAO().themNguoiGiaoDich(giaoDich));
    }
    public void get_giaoDich(Context context,Long id){
        _gd.setValue(DataBaseManager.getInstance(context).getItemDAO().timKiemGiaoDichTheoId(id));
    }
    public void update_giaoDich(Context context,GiaoDich giaoDich){
        DataBaseManager.getInstance(context).getItemDAO().capNhatGiaoDich(giaoDich);
    }

}
