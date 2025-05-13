package com.example.qlct_n3.View.edit_spending;

import static com.example.qlct_n3.ViewExtention.showToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.Constants;
import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.home.HomeViewModel;
import com.example.qlct_n3.databinding.ActivityEditSpendingBinding;

import java.util.Calendar;
import java.util.List;

public class EditSpendingActivity extends AppCompatActivity {
    private ActivityEditSpendingBinding binding;

    private static final String TAG = "HomeFragment";
    private HomeViewModel viewModel;
    private boolean hieu;
    Calendar calendar;
    private DirectoryAdapter adapter;
    private GiaoDich giaoDich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditSpendingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        giaoDich = new GiaoDich();
        calendar = Calendar.getInstance();
        hieu = true;
        adapter = new DirectoryAdapter();
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        loadView();
        checkUpdate();
    }

    private void loadView() {
        binding.tvDay.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        binding.recyclerview.setAdapter(adapter);
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtSpendingMoney.getEditText().getText().toString().isEmpty()) {
                    showToast(EditSpendingActivity.this, Constants.PLS_ENTER_THE_AMOUNT);
                } else {
                    updateSpending();
                }
            }
        });
        binding.imvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtSpendingMoney.getEditText().getText().toString().isEmpty()) {
                    showToast(EditSpendingActivity.this, Constants.PLS_ENTER_THE_AMOUNT);
                } else {
                    updateSpending();
                }
            }
        });
        binding.btnTienChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giaoDich.setThuChi(true);
                onClickTienChi();
            }
        });
        binding.btnTienThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giaoDich.setThuChi(false);
                onClickTienThu();
            }
        });
        binding.tvDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickday();
            }
        });
        binding.imvBackDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                String selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR); // Month is zero-based
                binding.tvDay.setText(selectedDate + "");
            }
        });
        binding.imvIncreaseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR); // Month is zero-based
                binding.tvDay.setText(selectedDate + "");
            }
        });
        binding.imbtnBack.setOnClickListener(view -> {
            finish();
        });

    }

    private void onClickday() {
        DatePickerDialog datePicker = new DatePickerDialog(
                EditSpendingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearPicker, int monthPicker,
                                  int dayOfMonthPicker) {
                calendar.set(Calendar.YEAR, yearPicker);
                calendar.set(Calendar.MONTH, monthPicker );
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthPicker);
                String selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                        (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
                binding.tvDay.setText(selectedDate);
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
                (Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }

    private void onClickTienThu() {
        getDanhMucThu();
        binding.btnTienThu.setBackgroundResource(R.color.pink);
        binding.btnTienThu.setTextColor(ContextCompat.getColor(EditSpendingActivity.this, R.color.white));
        binding.btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu);
        binding.btnTienChi.setTextColor(ContextCompat.getColor(EditSpendingActivity.this, R.color.pink));
        binding.tvSpendingMoneyOrRevenue.setText(Constants.TIEN_THU);
    }

    private void onClickTienChi() {
        getDanhMucChi();
        binding.btnTienChi.setBackgroundResource(R.color.pink);
        binding.btnTienChi.setTextColor(ContextCompat.getColor(EditSpendingActivity.this, R.color.white));
        binding.btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu);
        binding.btnTienThu.setTextColor(ContextCompat.getColor(EditSpendingActivity.this, R.color.pink));
        binding.tvSpendingMoneyOrRevenue.setText(Constants.TIEN_CHI);
    }

    private void getDanhMucChi() {
        viewModel._getDanhMucChi(EditSpendingActivity.this);
        viewModel.danhMucChi().observe(this, new Observer<List<DanhMuc>>() {
            @Override
            public void onChanged(List<DanhMuc> danhMucs) {
                adapter.setAdapter(danhMucs);
            }
        });
    }

    private void getDanhMucThu() {
        viewModel._getDanhMucThu(EditSpendingActivity.this);
        viewModel.danhMucThu().observe(this, new Observer<List<DanhMuc>>() {
            @Override
            public void onChanged(List<DanhMuc> danhMucs) {
                adapter.setAdapter(danhMucs);
            }
        });
    }

    private void updateSpending() {
        giaoDich.setNgayGiaoDich(calendar.get(Calendar.DAY_OF_MONTH));
        giaoDich.setThangGiaoDich(calendar.get(Calendar.MONTH) + 1);
        giaoDich.setNamGiaoDich(calendar.get(Calendar.YEAR));
        giaoDich.setTien(Long.parseLong(binding.edtSpendingMoney.getEditText().getText().toString()));
        giaoDich.setGhiChu(binding.edtNote.getEditText().getText().toString());
        giaoDich.setIdDanhMuc(adapter.getDanhMuc().getId());
        viewModel.update_giaoDich(EditSpendingActivity.this, giaoDich);
        showToast(EditSpendingActivity.this, Constants.UPDATE_SUCCESSFUL);
        finish();
    }
    public void checkUpdate() {
        Long idGiaoDich = getIntent().getLongExtra("idGiaoDich", -1);
        if (idGiaoDich != -1) {
            viewModel.get_giaoDich(EditSpendingActivity.this, idGiaoDich);
            viewModel.gd().observe(this, new Observer<GiaoDich>() {
                @Override
                public void onChanged(GiaoDich gd) {
                    giaoDich = gd;
                    binding.tvDay.setText(gd.getNgayGiaoDich() + "/" + gd.getThangGiaoDich() + "/" +
                            gd.getNamGiaoDich());
                    binding.edtNote.getEditText().setText(gd.getGhiChu());
                    binding.edtSpendingMoney.getEditText().setText(gd.getTien().toString());
                    calendar.set(gd.getNamGiaoDich(), gd.getThangGiaoDich() - 1, gd.
                            getNgayGiaoDich());
                    if (gd.getThuChi())
                        onClickTienChi();
                    else
                        onClickTienThu();
                }
            });
        }
    }
}