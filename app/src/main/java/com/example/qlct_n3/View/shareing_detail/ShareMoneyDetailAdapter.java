package com.example.qlct_n3.View.shareing_detail;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.R;
import com.example.qlct_n3.databinding.ItemMoneySharingDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class ShareMoneyDetailAdapter extends RecyclerView.Adapter<ShareMoneyDetailAdapter.ViewHolder> {
    private List<NguoiDung> listAdapter = new ArrayList<>();
    private Context context;
    ClickListener clickListener;

    public ShareMoneyDetailAdapter(Context context, ClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMoneySharingDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (listAdapter.get(position).getTrangThai())
            holder.binding.getRoot().setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
        else
            holder.binding.getRoot().setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
        holder.binding.tvMoney.setText(listAdapter.get(position).getKhoanChi().toString() + "đ");
        holder.binding.tvName.setText(listAdapter.get(position).getTen());
        holder.binding.getRoot().setOnLongClickListener(view -> {
            showPopupMenu(holder.itemView,position);
            return false;
        });
        holder.binding.getRoot().setOnClickListener(view ->{
            clickListener.onClickMember(listAdapter.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listAdapter.size();
    }

    public void setAdapter(List<NguoiDung> list) {
        listAdapter.clear();
        listAdapter.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMoneySharingDetailsBinding binding;

        public ViewHolder(ItemMoneySharingDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_edit_tt, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.done:
                        clickListener.onClickDone(listAdapter.get(position));
                        return true;
                    case R.id.pay:
                        clickListener.onClickPay(listAdapter.get(position));
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    interface ClickListener {
        void onClickDone(NguoiDung nguoiDung);
        void onClickPay(NguoiDung nguoiDung);

        void onClickMember(NguoiDung nguoiDung);
    }

}
