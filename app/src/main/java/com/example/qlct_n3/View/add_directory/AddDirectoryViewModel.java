package com.example.qlct_n3.View.add_directory;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.base.DataBaseManager;

public class AddDirectoryViewModel extends ViewModel {
    private MutableLiveData<Long> _blDanhMuc = new MutableLiveData<>();
    public LiveData<Long> blDanhMuc(){
        return _blDanhMuc;
    }
    public void addDanhMuc(Context context, DanhMuc danhMuc){
        _blDanhMuc.setValue(DataBaseManager.getInstance(context).getItemDAO().themDanhMuc(danhMuc));
    }
    private MutableLiveData<Integer> _blXoaDanhMuc = new MutableLiveData<>();
    public LiveData<Integer> blXoaDanhMuc(){
        return _blXoaDanhMuc;
    }
    public void deleteDanhMuc(Context context, DanhMuc danhMuc){
        _blXoaDanhMuc.setValue(DataBaseManager.getInstance(context).getItemDAO().xoaDanhMuc(danhMuc));
    }
}