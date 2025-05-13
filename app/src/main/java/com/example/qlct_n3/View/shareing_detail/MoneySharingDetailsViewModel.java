package com.example.qlct_n3.View.shareing_detail;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.base.DataBaseManager;

import java.util.List;

public class MoneySharingDetailsViewModel extends ViewModel {

    private MutableLiveData<List<NguoiDung>> _nguoiDungs = new MutableLiveData<>();
    public LiveData<List<NguoiDung>> nguoiDungs() {
        return _nguoiDungs;
    }
    public void getAllMember(Long id,Context context){
        _nguoiDungs.setValue(DataBaseManager.getInstance(context).getItemDAO().timKiemNguoiDungTheoHoaDon(id));
    }

    private MutableLiveData<HoaDon> _hoaDon = new MutableLiveData<>();
    public LiveData<HoaDon> hoaDon() {
        return _hoaDon;
    }
    public void getHoaDon(Long id,Context context){
        _hoaDon.setValue(DataBaseManager.getInstance(context).getItemDAO().timKiemHoaDon(id));
    }
    public void editMember(NguoiDung nguoiDung,Context context){
        DataBaseManager.getInstance(context).getItemDAO().chinhSuaNguoiDung(nguoiDung);
    }
}
