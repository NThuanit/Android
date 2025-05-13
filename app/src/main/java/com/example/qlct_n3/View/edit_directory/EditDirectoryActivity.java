package com.example.qlct_n3.View.edit_directory;

import android.app.AlertDialog;
import android.content.Intent;
import android.icu.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.add_directory.AddDirectoryActivity;
import com.example.qlct_n3.databinding.ActivityEditDirectoryBinding;

import kotlinx.coroutines.Dispatchers;

import java.util.ArrayList;
import java.util.List;

public class EditDirectoryActivity extends AppCompatActivity {
    private static final String TAG = "EditDirectoryActivity";
    private ActivityEditDirectoryBinding binding;
    private EditDirectoryViewModel viewModel;

    private EditDirectoryAdapter adapter;
    private List<DanhMuc> listDirectory;
    private boolean thuCHi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDirectoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getView();
        onClickTienChi();
        getDataCHi();
        binding.recyclerview.setAdapter(adapter);
        binding.btnTienChi.setOnClickListener(v -> {
            thuCHi = true;
            onClickTienChi();
            getDataCHi();
        });

        binding.btnTienThu.setOnClickListener(v -> {
            thuCHi = false;
            onClickTienThu();
            getDataThu();
        });

        binding.imbtnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddDirectoryActivity.class);
            intent.putExtra("thuChi", thuCHi);
            startActivity(intent);
        });

        binding.imbtnBack.setOnClickListener(v -> finish());
    }
    private void getView(){
        viewModel = new ViewModelProvider(this).get(EditDirectoryViewModel.class);
        listDirectory = new ArrayList<>();
        thuCHi = true;
        adapter = new EditDirectoryAdapter(new EditDirectoryAdapter.ClickListener() {
            @Override
            public void onClickEdit() {
                edit();
            }

            @Override
            public void onClickDelete() {
                delete();
            }
        }, this);
    }
    private void onClickTienThu() {
        binding.btnTienThu.setBackgroundResource(R.color.pink);
        binding.btnTienThu.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        binding.btnTienChi.setBackgroundResource(R.drawable.border_btn_tienthu);
        binding.btnTienChi.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pink));
    }

    private void onClickTienChi() {
        binding.btnTienChi.setBackgroundResource(R.color.pink);
        binding.btnTienChi.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        binding.btnTienThu.setBackgroundResource(R.drawable.border_btn_tienthu);
        binding.btnTienThu.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pink));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (thuCHi) {
            getDataCHi();
        } else {
            getDataThu();
        }
    }

    private void getDataThu() {
        viewModel.getDanhMucThu(this);
        viewModel.danhMucThu().observe(this, new Observer<List<DanhMuc>>() {
            @Override
            public void onChanged(List<DanhMuc> danhMucs) {
                adapter.setAdapter(danhMucs);
            }
        });
    }

    private void getDataCHi() {
        viewModel.getDanhMucChi(this);
        viewModel.danhMucChi().observe(this, new Observer<List<DanhMuc>>() {
            @Override
            public void onChanged(List<DanhMuc> danhMucs) {
               adapter.setAdapter(danhMucs);
                Log.e(TAG, "onChanged: " + danhMucs.toString() );
            }
        });
    }

    private void delete() {
        showYesNoDialog();
    }

    private void edit() {
        Intent intent = new Intent(this, AddDirectoryActivity.class);
        intent.putExtra("name", adapter.getDanhMuc().getTenDanhMuc());
        intent.putExtra("id", adapter.getDanhMuc().getId());
        startActivity(intent);
    }
    private void showYesNoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Câu hỏi")
                .setMessage("Bạn có chắc chắn muốn danh mục này? Thao tác này không thể\n" +
                        "hoàn tác lại.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    viewModel.deleteDanhMuc(this,adapter.getDanhMuc());
                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


