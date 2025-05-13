package com.example.qlct_n3.View.add_directory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct_n3.R;
import com.example.qlct_n3.databinding.ItemAddDirectoryBinding;

import java.util.ArrayList;
import java.util.List;

public class AddDirectoryAdapter extends RecyclerView.Adapter<AddDirectoryAdapter.ViewHolder> {
    private final List<Integer> listDirectory = new ArrayList<>(List.of(R.drawable.icon_eat_and_drink,
            R.drawable.icon_daily_spending,
            R.drawable.icon_cloths,
            R.drawable.icon_cosmetics,
            R.drawable.icon_communication_fee,
            R.drawable.icon_medical,
            R.drawable.icon_education,
            R.drawable.icon_electricity_bill,
            R.drawable.icon_go,
            R.drawable.icon_bill_contact,
            R.drawable.icon_bill_home,
            R.drawable.icon_salary,
            R.drawable.icon_allowance,
            R.drawable.icon_bonus,
            R.drawable.icon_investment_money,
            R.drawable.icon_supplementary_income,
            R.drawable.icon_coc,
            R.drawable.icon_cuon,
            R.drawable.icon_gau,
            R.drawable.icon_heart,
            R.drawable.icon_note,
            R.drawable.icon_setting,
            R.drawable.icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8));
    private int item = 0;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemAddDirectoryBinding binding;

        public ViewHolder(ItemAddDirectoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public AddDirectoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemAddDirectoryBinding binding = ItemAddDirectoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(AddDirectoryAdapter.ViewHolder holder, int position) {
        holder.binding.imvDirectory.setImageResource(listDirectory.get(position));
        holder.binding.getRoot().setOnClickListener(view -> {
            int i = position;
            item = i;
            notifyDataSetChanged();
        });
        if (position == item) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.border_btn_tienthu);
        } else {
            holder.binding.getRoot().setBackgroundResource(R.drawable.border_directory);
        }
    }

    @Override
    public int getItemCount() {
        return listDirectory.size();
    }

    public int getIcon() {
        return listDirectory.get(item);
    }
}


