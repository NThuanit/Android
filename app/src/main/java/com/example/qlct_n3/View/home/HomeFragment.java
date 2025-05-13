package com.example.qlct_n3.View.home;

import static com.example.qlct_n3.ViewExtention.showToast;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.Constants;
import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.Model.SpendingInCalendar;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.calendar.CalendarFragment;
import com.example.qlct_n3.View.calendar.CalendarViewModel;
import com.example.qlct_n3.View.edit_directory.EditDirectoryActivity;
import com.example.qlct_n3.View.edit_spending.EditSpendingActivity;
import com.example.qlct_n3.View.home.HomeViewModel;
import com.example.qlct_n3.base.DataBaseManager;
import com.example.qlct_n3.databinding.FragmentHomeBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private boolean trangThai;
    Calendar calendar;
    private DirectoryAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel._getDanhMucThu(requireContext());
        viewModel._getDanhMucChi(requireContext());
        calendar = Calendar.getInstance();
        trangThai = true;
        adapter = new DirectoryAdapter(
                new DirectoryAdapter.ClickListener() {
                    @Override
                    public void onClickEditDirectory() {
                        startActivity(new Intent(requireContext(), EditDirectoryActivity.class));
                    }
                }
        );
        getDanhMucChi();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadView();
        return binding.getRoot();

    }

    private void loadView() {
        binding.tvDay.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
        binding.recyclerview.setAdapter(adapter);
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtSpendingMoney.getEditText().getText().toString().isEmpty()) {
                    showToast(requireContext(), Constants.PLS_ENTER_THE_AMOUNT);
                } else {
                    updateSpending();
                }
            }
        });
        binding.imvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edtSpendingMoney.getEditText().getText().toString().isEmpty()) {
                    showToast(requireContext(), Constants.PLS_ENTER_THE_AMOUNT);
                } else {
                    updateSpending();
                }
            }
        });
        binding.btnTienChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai = true;
                onClickTienChi();
            }
        });
        binding.btnTienThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trangThai = false;
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

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onClickday() {
        DatePickerDialog datePicker = new DatePickerDialog(
                requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int yearPicker, int monthPicker, int dayOfMonthPicker) {
                calendar.set(Calendar.YEAR, yearPicker);
                calendar.set(Calendar.MONTH, monthPicker );
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthPicker);
                String selectedDate = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                        (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR); // Month is zero-based
                binding.tvDay.setText(selectedDate);
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }


    private void onClickTienThu() {
        getDanhMucThu();
        binding.btnTienThu.setBackgroundResource(R.color.pink);
        binding.btnTienThu.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        binding.btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu);
        binding.btnTienChi.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink));
        binding.tvSpendingMoneyOrRevenue.setText(Constants.TIEN_THU);
        binding.btnAccept.setText(Constants.BTN_TIEN_THU);
    }

    private void onClickTienChi() {
        getDanhMucChi();
        binding.btnTienChi.setBackgroundResource(R.color.pink);
        binding.btnTienChi.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        binding.btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu);
        binding.btnTienThu.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink));
        binding.tvSpendingMoneyOrRevenue.setText(Constants.TIEN_CHI);
        binding.btnAccept.setText(Constants.BTN_TIEN_CHI);
    }

    private void getDanhMucChi() {
        viewModel._getDanhMucChi(requireContext());
        viewModel.danhMucChi().observe(this, new Observer<List<DanhMuc>>() {
            @Override
            public void onChanged(List<DanhMuc> danhMucs) {
                adapter.setAdapter(danhMucs);
            }
        });
    }

    private void getDanhMucThu() {
        viewModel._getDanhMucThu(requireContext());
        viewModel.danhMucThu().observe(this, new Observer<List<DanhMuc>>() {
            @Override
            public void onChanged(List<DanhMuc> danhMucs) {
                adapter.setAdapter(danhMucs);
            }
        });
    }

    private void updateSpending() {
        GiaoDich giaoDich = new GiaoDich(
                0,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                Long.parseLong(binding.edtSpendingMoney.getEditText().getText().toString()),
                binding.edtNote.getEditText().getText().toString(),
                trangThai,
                adapter.getDanhMuc().getId()
        );
        viewModel.set_giaoDich(requireContext(), giaoDich);
        showToast(requireContext(), Constants.ADD_SUCCESSFUL);
        binding.edtNote.getEditText().setText("");
        binding.edtSpendingMoney.getEditText().setText("");
    }

}