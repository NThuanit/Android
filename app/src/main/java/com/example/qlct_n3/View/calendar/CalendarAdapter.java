package com.example.qlct_n3.View.calendar;

import static com.example.qlct_n3.ViewExtention.decodeBase64ToBitmap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.Model.SpendingInCalendar;
import com.example.qlct_n3.R;
import com.example.qlct_n3.View.home.DirectoryAdapter;
import com.example.qlct_n3.View.home.HomeViewModel;
import com.example.qlct_n3.base.DataBaseManager;
import com.example.qlct_n3.databinding.ItemSpendingBinding;
import com.example.qlct_n3.databinding.ItemTimeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;


public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "CalendarAdapter";
    private final int typeTime = 0;
    private final int typeSpending = 1;
    private String time = "";
    private int index;
    private final List<SpendingInCalendar> listSpending;
    private Context context;
    private ClickListener clickListener;
    private MutableLiveData<DanhMuc> _danhMucThu = new MutableLiveData<>();

    public LiveData<DanhMuc> danhMucThu() {
        return _danhMucThu;
    }

    public CalendarAdapter(ClickListener onClickDelete, Context context) {
        this.clickListener = onClickDelete;
        this.context = context;
        this.listSpending = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        final int positionTime = 0;
        if (position == positionTime) {
            return typeTime;
        } else {
            return typeSpending;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTimeBinding bindingTime = ItemTimeBinding.inflate(layoutInflater, parent, false);
        ItemSpendingBinding bindingSpending = ItemSpendingBinding.inflate(layoutInflater, parent, false);
        if (viewType == typeTime) {
            return new TimeViewHolder(bindingTime);
        } else {
            return new SpendingViewHolder(bindingSpending);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int i = position;
        if (holder instanceof TimeViewHolder) {
            TimeViewHolder timeViewHolder = (TimeViewHolder) holder;
            timeViewHolder.binding.getRoot().setText(time + "");
        }
        if (holder instanceof SpendingViewHolder) {
            SpendingViewHolder spendingViewHolder = (SpendingViewHolder) holder;
            SpendingInCalendar spending = listSpending.get(position - 1);
            spendingViewHolder.binding.tvNameDiretory.setText(spending.getTenDanhMuc() + "");
            spendingViewHolder.binding.imvAvtSpending.setImageBitmap(decodeBase64ToBitmap(spending.getIcon()));
            spendingViewHolder.binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupMenu(spendingViewHolder.itemView);
                    index = i - 1;
                    return true;
                }
            });
            spendingViewHolder.binding.getRoot().setOnClickListener(view -> {
                clickListener.onClickSpending(listSpending.get(position - 1));
            });
            if (spending.getThuChi()) {
                spendingViewHolder.binding.tvSpendingMoney.setText("-" + spending.getTien());
            } else {
                spendingViewHolder.binding.tvSpendingMoney.setText("+" + spending.getTien());
            }
            spendingViewHolder.binding.tvNote.setText(listSpending.get(position - 1).getGhiChu());
        }
    }

    @Override
    public int getItemCount() {
        return listSpending.size() + 1;
    }

    public void setAdapter(String Time, List<SpendingInCalendar> list) {
        time = Time;
        listSpending.clear();
        listSpending.addAll(list);
        notifyDataSetChanged();
    }

    public int getIndex() {
        return index;
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_giaodich, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        clickListener.onClickDelete();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimeBinding binding;

        public TimeViewHolder(ItemTimeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class SpendingViewHolder extends RecyclerView.ViewHolder {
        private final ItemSpendingBinding binding;

        public SpendingViewHolder(ItemSpendingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    interface ClickListener {
        void onClickDelete();

        void onClickSpending(SpendingInCalendar spendingInCalendar);
    }
}


