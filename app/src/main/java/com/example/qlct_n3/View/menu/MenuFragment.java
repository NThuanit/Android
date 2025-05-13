package com.example.qlct_n3.View.menu;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qlct_n3.R;
import com.example.qlct_n3.View.history_sharing.HistoryShareMoneyActivity;
import com.example.qlct_n3.View.searchmoney.ShareMoneyActivity;
import com.example.qlct_n3.base.DataBaseManager;
import com.example.qlct_n3.databinding.FragmentMenuBinding;

import java.util.Calendar;

public class MenuFragment extends Fragment {
    private FragmentMenuBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMenuBinding.inflate(getLayoutInflater());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onClick();
        return binding.getRoot();
    }

    private void onClick() {
        binding.lnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
        binding.lnShareMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMoney();
            }
        });
        binding.lnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                history();
            }
        });
    }

    private void history() {
        startActivity(new Intent(requireContext(), HistoryShareMoneyActivity.class));
    }

    private void shareMoney() {
        startActivity(new Intent(requireContext(), ShareMoneyActivity.class));
    }

    private void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Câu hỏi")
                .setMessage(
                        "Bạn có chắc chắn muốn xóa tất cả dữ liệu? Thao tác này không thể\n" +
                                "hoàn tác lại."
                )
                .setPositiveButton("Yes", (dialog, which) -> {
                    try {
                        DataBaseManager.deleteAllTable();

                    } catch (Exception e) {
                        e.getMessage();

                    }

                    dialog.dismiss();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}