package com.example.qlct_n3.View.history_sharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.Constants;
import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.shareing_detail.MoneySharingDetailsActivity;
import com.example.qlct_n3.View.shareing_detail.ShareMoneyDetailAdapter;
import com.example.qlct_n3.databinding.ActivityHistoryShareMoneyBinding;

import java.util.List;

public class HistoryShareMoneyActivity extends AppCompatActivity {
    private HistoryShareMoneyViewModel viewModel;
    private ActivityHistoryShareMoneyBinding binding;
    private HistoryShareMoneyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryShareMoneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(HistoryShareMoneyViewModel.class);
        adapter = new HistoryShareMoneyAdapter(new HistoryShareMoneyAdapter.ClickListener() {
            @Override
            public void onClickHoaDon(Long id) {
                clickHoaDon(id);
            }

            @Override
            public void onClickDelete(HoaDon hoaDon) {
                clickDelete(hoaDon);
            }
        }, this);
        binding.recyclerview.setAdapter(adapter);
        getData();

        binding.imbtnBack.setOnClickListener(view -> {
            finish();
        });
    }

    private void clickDelete(HoaDon hoaDon) {
        viewModel.deteteNguoiDungs(this,hoaDon);
        viewModel.nguoiDung().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewModel.deteteHoaDon(HistoryShareMoneyActivity.this,hoaDon);
            }
        });
        getData();
    }

    public void clickHoaDon(Long id) {
        Intent intent = new Intent(this, MoneySharingDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void getData() {
        viewModel.getAllHoaDon(this);
        viewModel.hoaDons().observe(this, new Observer<List<HoaDon>>() {
            @Override
            public void onChanged(List<HoaDon> hoaDons) {
                adapter.setAdapter(hoaDons);
            }
        });
    }
}