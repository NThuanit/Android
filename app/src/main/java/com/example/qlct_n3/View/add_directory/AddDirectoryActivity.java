package com.example.qlct_n3.View.add_directory;

import static com.example.qlct_n3.ViewExtention.convertDrawableToBase64;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.databinding.ActivityAddDirectoryBinding;

public class AddDirectoryActivity extends AppCompatActivity {
    private AddDirectoryViewModel viewModel;
    private ActivityAddDirectoryBinding binding;
    private AddDirectoryAdapter adapter;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddDirectoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AddDirectoryViewModel.class);
        adapter = new AddDirectoryAdapter();
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            binding.edtName.getEditText().setText(getIntent().getStringExtra("name"));
        }
        binding.imbtnBack.setOnClickListener(view -> finish());
        binding.btnSave.setOnClickListener(view -> {
            onClickSave();
        });
        binding.recyclerview.setAdapter(adapter);
    }

    private void onClickSave() {
        if (!binding.edtName.getEditText().getText().toString().isEmpty()) {
            try {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setTenDanhMuc(binding.edtName.getEditText().getText().toString());
                danhMuc.setIcon(convertDrawableToBase64(
                        getApplicationContext(),
                        adapter.getIcon()
                ));
                danhMuc.setThuChi(getIntent().getBooleanExtra("thuChi", true));
                if (id != -1) {
                    DanhMuc dm = new DanhMuc();
                    danhMuc.setId(id);
                    viewModel.deleteDanhMuc(this,dm);
                }
                addDanhMuc(danhMuc);
                finish();
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            Toast.makeText(
                    getApplicationContext(), "Vui lòng chọn tên danh mục và ảnh!", Toast.LENGTH_SHORT
            ).show();
            binding.edtName.getEditText().requestFocus();
        }
    }

    private void addDanhMuc(DanhMuc danhMuc) {
        viewModel.addDanhMuc(this, danhMuc);
        viewModel.blDanhMuc().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Toast.makeText(
                        getApplicationContext(), "Đã lưu!", Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}


