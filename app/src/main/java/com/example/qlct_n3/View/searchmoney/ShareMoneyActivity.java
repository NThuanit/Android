package com.example.qlct_n3.View.searchmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.Constants;
import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.shareing_detail.MoneySharingDetailsActivity;
import com.example.qlct_n3.databinding.ActivityShareMoneyBinding;
import com.example.qlct_n3.databinding.DialogAddMemberBinding;
import com.example.qlct_n3.databinding.DialogUpdateUserBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShareMoneyActivity extends AppCompatActivity {
    private static final String TAG = "ShareMoneyActivity";
    private ShareMoneyViewModel viewModel;
    private ActivityShareMoneyBinding binding;
    private List<NguoiDung> listMember;
    private ShareMoneyAdapter adapter;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listMember = new ArrayList<>();
        adapter = new ShareMoneyAdapter(new ShareMoneyAdapter.ClickListener() {
            @Override
            public void onClickMember(NguoiDung nguoiDung, int position) {
                clickMember(nguoiDung, position);
            }
        });
        calendar = Calendar.getInstance();
        viewModel = new ViewModelProvider(this).get(ShareMoneyViewModel.class);
        onClick();
    }
    private void clickMember(NguoiDung nguoiDung, int position) {
        Dialog hhe = new Dialog(this);
        DialogUpdateUserBinding dialogBinding = DialogUpdateUserBinding.inflate(getLayoutInflater());
        dialogBinding.edtName.getEditText().setText(nguoiDung.getTen());
        dialogBinding.edtSpending.getEditText().setText(nguoiDung.getKhoanChi().toString());
        dialogBinding.edtPhone.getEditText().setText(nguoiDung.getSdt());
        hhe.setContentView(dialogBinding.getRoot());
        hhe.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        hhe.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams attributes = hhe.getWindow().getAttributes();
        attributes.y = -170;
        attributes.gravity = Gravity.BOTTOM;
        hhe.getWindow().setAttributes(attributes);
        dialogBinding.btnUpdateMember.setOnClickListener(view -> {
            if (dialogBinding.edtName.getEditText().getText().toString().isEmpty()
                    || dialogBinding.edtSpending.getEditText().getText().toString().isEmpty()
                    || dialogBinding.edtPhone.getEditText().getText().toString().isEmpty())
                Constants.showToast("Vui lòng nhập đầy đủ thông tin", getApplicationContext());
            else {
                String name = dialogBinding.edtName.getEditText().getText().toString();
                String phone = dialogBinding.edtPhone.getEditText().getText().toString();
                try {
                    long spending = Long.parseLong(dialogBinding.edtSpending.getEditText().
                            getText().toString());
                    nguoiDung.setTen(name);
                    nguoiDung.setKhoanChi(spending);
                    nguoiDung.setSdt(phone);
                    listMember.set(position, nguoiDung);
                    viewModel.updateMember(nguoiDung, this);
                    adapter.setAdapter(listMember);
                    hhe.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "onClickAddMember: " + e.getMessage());
                }
            }
        });

        hhe.setCancelable(true);
        hhe.show();
    }
    private void onClick() {
        binding.btnAddMember.setOnClickListener(view -> onClickAddMember());
        binding.btnShareMoney.setOnClickListener(view -> onClickShareMoney());
        binding.recyclerview.setAdapter(adapter);
        binding.imbtnBack.setOnClickListener(view -> {
            finish();
        });
    }
    // Hàm Xử lý chia tiền
    private void onClickShareMoney() {
        if (checkShareMoney()) {
            try {
                HoaDon hoaDon = new HoaDon(
                        Long.parseLong(binding.edtTotalMoney.getEditText().getText().toString()),
                        binding.edtDescribe.getEditText().getText().toString(),
                        listMember.size(),
                        calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) +
                                "/" + calendar.get(Calendar.DAY_OF_MONTH)
                );
                viewModel.addHoaDon(hoaDon, this);
                viewModel.hoaDon().observe(this, new Observer<Long>() {
                    @Override
                    public void onChanged(Long aLong) {
                        Intent intent = new Intent(ShareMoneyActivity.this,
                                MoneySharingDetailsActivity.class);
                        intent.putExtra("id", aLong);
                        startActivity(intent);
                        viewModel.addAllMember(viewModel.shareMoney(listMember,
                                Long.parseLong(binding.edtTotalMoney.
                                getEditText().getText().toString()), aLong),
                                ShareMoneyActivity.this);
                        Log.e(TAG, "onChanged: ");
                        startActivity(intent);
                        finish();
                    }
                });

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void onClickAddMember() {
        Dialog hhe = new Dialog(this);
        DialogAddMemberBinding dialogBinding = DialogAddMemberBinding.inflate(getLayoutInflater());
        dialogBinding.btnBack.setOnClickListener(view -> hhe.dismiss());
        hhe.setContentView(dialogBinding.getRoot());
        hhe.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        hhe.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        WindowManager.LayoutParams attributes = hhe.getWindow().getAttributes();
        attributes.y = -170;
        attributes.gravity = Gravity.BOTTOM;
        hhe.getWindow().setAttributes(attributes);
        dialogBinding.btnAddMember.setOnClickListener(view -> {
            if (dialogBinding.edtName.getEditText().getText().toString().isEmpty() ||
                    dialogBinding.edtSpending.getEditText().getText().toString().isEmpty()) {
                Constants.showToast("Vui lòng nhập đầy đủ thông tin",
                        getApplicationContext());
            } else {
                String name = dialogBinding.edtName.getEditText().getText().toString();
                String phone = dialogBinding.edtPhone.getEditText().getText().toString();
                try {
                    long spending = Long.parseLong(dialogBinding.edtSpending.getEditText().
                            getText().toString());
                    NguoiDung nguoiDung = new NguoiDung();
                    nguoiDung.setTen(name);
                    nguoiDung.setKhoanChi(spending);
                    nguoiDung.setSdt(phone);
                    listMember.add(nguoiDung);
                    adapter.setAdapter(listMember);
                    hhe.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "onClickAddMember: " + e.getMessage());
                }
            }
        });

        hhe.setCancelable(true);
        hhe.show();
    }

    private Boolean checkShareMoney() {
        if (binding.edtDescribe.getEditText().getText().toString().isEmpty()) {
            binding.edtDescribe.getEditText().requestFocus();
            Constants.showToast("Vui lòng nhập mô tả", this);
            return false;
        }
        if (binding.edtTotalMoney.getEditText().getText().toString().isEmpty()) {
            binding.edtDescribe.getEditText().requestFocus();
            Constants.showToast("Vui lòng nhập tổng tiền", this);
            return false;
        }
        if (listMember.size() < 2) {
            onClickAddMember();
            Constants.showToast(
                    "Vui lòng nhập thêm thành viên đề chia tiền (tối thiều 2 người) !"
                    , this);
            return false;
        }
        return true;
    }
}