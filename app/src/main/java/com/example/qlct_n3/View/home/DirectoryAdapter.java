package com.example.qlct_n3.View.home;

import static com.example.qlct_n3.ViewExtention.decodeBase64ToBitmap;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.R;
import com.example.qlct_n3.databinding.ItemDirectoryBinding;

import java.util.ArrayList;
import java.util.List;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> {
    private static final String TAG = "DirectoryAdapter";
    private List<DanhMuc> listDirectory = new ArrayList<>();

    private int index = 0;
    private String directory = "Ăn uống";
    private ClickListener clickListener;
    public DirectoryAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemDirectoryBinding binding;

        public ViewHolder(ItemDirectoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public DirectoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDirectoryBinding binding = ItemDirectoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DirectoryAdapter.ViewHolder holder, int position) {
        holder.binding.imvDirectory.setImageBitmap(decodeBase64ToBitmap(listDirectory.get(position).getIcon()));
        holder.binding.tvNameDiretory.setText(listDirectory.get(position).getTenDanhMuc());
        holder.binding.getRoot().setOnClickListener(view -> {
            int i = position;
            index = i;
            directory = holder.binding.tvNameDiretory.getText().toString();
            if (position != listDirectory.size() - 1) {
                notifyDataSetChanged();
            } else {
                clickListener.onClickEditDirectory();
            }
        });
        if (position == index) {
            holder.binding.getRoot().setBackgroundResource(R.drawable.border_btn_tienthu);
        } else {
            holder.binding.getRoot().setBackgroundResource(R.drawable.border_directory);
        }
    }

    @Override
    public int getItemCount() {
        return listDirectory.size();
    }

    public void setAdapter(List<DanhMuc> list) {
        listDirectory.clear();
        listDirectory.addAll(list);
        DanhMuc danhMuc = new DanhMuc();
        danhMuc.setTenDanhMuc("Chỉnh sửa");
        listDirectory.add(danhMuc);
        notifyDataSetChanged();
    }

    public DanhMuc getDanhMuc() {
        return listDirectory.get(index);
    }
    interface ClickListener {
        void onClickEditDirectory();
    }

}


