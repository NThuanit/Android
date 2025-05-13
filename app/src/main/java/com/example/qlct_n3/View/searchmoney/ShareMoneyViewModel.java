package com.example.qlct_n3.View.searchmoney;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.base.DataBaseManager;

import java.util.List;

public class ShareMoneyViewModel extends ViewModel {
    private static final String TAG = "ShareMoneyViewModel";
    private MutableLiveData<Long> _hoaDon = new MutableLiveData<>();

    public LiveData<Long> hoaDon() {
        return _hoaDon;
    }

    public void addHoaDon(HoaDon hoaDon, Context context) {
        _hoaDon.setValue(DataBaseManager.getInstance(context).getItemDAO().themHoaDon(hoaDon));
    }

    public void addAllMember(List<NguoiDung> nguoiDungs, Context context) {
        DataBaseManager.getInstance(context).getItemDAO().themNguoiDungs(nguoiDungs);
    }

    public void updateMember(NguoiDung nguoiDung, Context context) {
        DataBaseManager.getInstance(context).getItemDAO().chinhSuaNguoiDung(nguoiDung);
    }

    public List<NguoiDung> shareMoney(List<NguoiDung> nguoiDungs,Long totalMoney,Long idHoaDon) {
        nguoiDungs.forEach(nguoiDung -> {
            nguoiDung.setIdHoaDon(idHoaDon);
        });
        Log.e(TAG, "shareMoney: " + totalMoney);
        Long money = totalMoney / nguoiDungs.size();
        nguoiDungs.forEach(nguoiDung -> {
            Long khoanCanTra = nguoiDung.getKhoanChi() - money;
            nguoiDung.setKhoanChi(khoanCanTra);
            nguoiDung.setTrangThai((khoanCanTra < 0) ? false : true);
        });
        return nguoiDungs;
    }

}
