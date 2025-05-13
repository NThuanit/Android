package com.example.qlct_n3.View.edit_directory;

import static com.example.qlct_n3.ViewExtention.decodeBase64ToBitmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.home.DirectoryAdapter;
import com.example.qlct_n3.databinding.ItemEditDirectoryBinding;

import java.util.ArrayList;
import java.util.List;

public class EditDirectoryAdapter extends RecyclerView.Adapter<EditDirectoryAdapter.ViewHolder> {
    private List<DanhMuc> listAdapter = new ArrayList<>();
    private ClickListener clickListener;
    private Context context;
    private int item = 0;

    public EditDirectoryAdapter(ClickListener clickListener, Context context) {
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public EditDirectoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEditDirectoryBinding binding = ItemEditDirectoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EditDirectoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EditDirectoryAdapter.ViewHolder holder, int position) {
        holder.binding.tvNameDiretory.setText(listAdapter.get(position).getTenDanhMuc());
        holder.binding.imvIcon.setImageBitmap(decodeBase64ToBitmap(listAdapter.get(position).getIcon()));
        holder.binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                item = position;
                showPopupMenu(holder.itemView);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAdapter.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemEditDirectoryBinding binding;

        public ViewHolder(ItemEditDirectoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setAdapter(List<DanhMuc> list) {
        listAdapter.clear();
        listAdapter.addAll(list);
        notifyDataSetChanged();
    }

    interface ClickListener {
        void onClickEdit();
        void onClickDelete();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_directory, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        clickListener.onClickDelete();
                        return true;
                    case R.id.edit:
                        clickListener.onClickEdit();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public DanhMuc getDanhMuc() {
        return listAdapter.get(item);
    }
}
