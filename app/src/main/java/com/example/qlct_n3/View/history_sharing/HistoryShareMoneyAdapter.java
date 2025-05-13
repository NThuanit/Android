package com.example.qlct_n3.View.history_sharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.R;
import com.example.qlct_n3.databinding.ItemHistoryBinding;
import com.example.qlct_n3.databinding.ItemMoneySharingDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class HistoryShareMoneyAdapter extends RecyclerView.Adapter<HistoryShareMoneyAdapter.ViewHolder> {
    private List<HoaDon> listAdapter;
    private ClickListener clickListener;
    private Context context;

    public HistoryShareMoneyAdapter(ClickListener clickListener, Context context) {
        this.clickListener = clickListener;
        this.listAdapter = new ArrayList<>();
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemHistoryBinding binding;

        public ViewHolder(ItemHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public HistoryShareMoneyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryShareMoneyAdapter.ViewHolder holder, int position) {
        holder.binding.tvNameDescribe.setText(listAdapter.get(position).getMota() + " (" + listAdapter.get(position).getNgay() + ")");
        holder.binding.getRoot().setOnClickListener(view -> {
            clickListener.onClickHoaDon(listAdapter.get(position).getId());
        });
        holder.binding.getRoot().setOnLongClickListener(view -> {
            showPopupMenu(holder.itemView,listAdapter.get(position));
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return listAdapter.size();
    }

    public void setAdapter(List<HoaDon> list) {
        listAdapter.clear();
        listAdapter.addAll(list);
        notifyDataSetChanged();
    }

    interface ClickListener {
        void onClickHoaDon(Long id);
        void onClickDelete(HoaDon hoaDon);
    }

    private void showPopupMenu(View view,HoaDon hoaDon) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_giaodich, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        clickListener.onClickDelete(hoaDon);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
}
