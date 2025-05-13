package com.example.qlct_n3.View.shareing_detail;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.Constants;
import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.R;
import com.example.qlct_n3.databinding.ActivityMoneySharingDetailsBinding;

import java.util.ArrayList;
import java.util.List;

public class MoneySharingDetailsActivity extends AppCompatActivity {
    private static final String TAG = "MoneySharingDetailsActi";
    private Long idHoaDon;
    ActivityMoneySharingDetailsBinding binding;
    MoneySharingDetailsViewModel viewModel;
    ShareMoneyDetailAdapter adapter;
    List<NguoiDung> listNguoiDung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMoneySharingDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new ShareMoneyDetailAdapter(this, new ShareMoneyDetailAdapter.ClickListener() {
            @Override
            public void onClickDone(NguoiDung nguoiDung) {
                Done(nguoiDung);
            }

            public void onClickPay(NguoiDung nguoiDung) {
                Pay(nguoiDung);
            }

            @Override
            public void onClickMember(NguoiDung nguoiDung) {
                showMember(nguoiDung);
            }
        });

        viewModel = new ViewModelProvider(this).get(MoneySharingDetailsViewModel.class);
        listNguoiDung = new ArrayList<>();
        idHoaDon = getIntent().getLongExtra("id", -1);
        binding.recyclerview.setAdapter(adapter);
        onClick();
        getData();
    }

    // Hàm hiển thị thông tin 1 thành viên
    private void showMember(NguoiDung nguoiDung) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Member"); // Tiêu đề của AlertDialog
        builder.setMessage("Tên : " + nguoiDung.getTen() + "\n" + "Khoản chi : " +
                nguoiDung.getKhoanChi() + "\n" + "Số điện thoại : " +
                ""+nguoiDung.getSdt());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    // Hàm lấy thông tin dữ liệu theo id
    private void getData() {
        if (idHoaDon != -1) {
            viewModel.getAllMember(idHoaDon, this);
            viewModel.getHoaDon(idHoaDon, this);
            viewModel.nguoiDungs().observe(this, new Observer<List<NguoiDung>>() {
                @Override
                public void onChanged(List<NguoiDung> list) {
                    adapter.setAdapter(list);
                }
            });
            viewModel.hoaDon().observe(this, new Observer<HoaDon>() {
                @Override
                public void onChanged(HoaDon hoaDon) {
                    binding.tvQuantity.setText(hoaDon.getSoLuong() + "");
                    binding.tvTotalMoney.setText(hoaDon.getTongTien() + "");
                    binding.tvDescribe.setText(hoaDon.getMota() + "");
                    binding.tvDetails.setText(hoaDon.getNgay() + "");
                }
            });
        }
    }

    private void onClick() {
        binding.imbtnBack.setOnClickListener(view -> {
            finish();
        });
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });
    }


    // hàm set trạng thái đã thanh toán cho các thành veien
    private void Done(NguoiDung nguoiDung) {
        nguoiDung.setTrangThai(true);
        nguoiDung.setKhoanChi(0L);
        viewModel.editMember(nguoiDung, this);
        getData();
    }

    private void Pay(NguoiDung nguoiDung) {
        if(nguoiDung.getTrangThai() == false){
            // Số điện thoại đích
            String phoneNumber = nguoiDung.getSdt().toString();

            viewModel.getHoaDon(nguoiDung.getIdHoaDon(), getApplicationContext());
            viewModel.hoaDon().observe(this, new Observer<HoaDon>() {
                @Override
                public void onChanged(HoaDon hoaDon) {
                    // Nội dung tin nhắn
                    String message = "Bạn đang thiếu " + (0-nguoiDung.getKhoanChi()) + "đ vào ngày " + hoaDon.getNgay() + ".Vui lòng thanh toán.";

                    Uri smsUri = Uri.parse("smsto:" + phoneNumber);

                    // Tạo Intent với hành động ACTION_SENDTO và dữ liệu là Uri của số điện thoại
                    Intent intent = new Intent(Intent.ACTION_SENDTO, smsUri);

                    // Đặt nội dung tin nhắn (tùy chọn)
                    intent.putExtra("sms_body", message);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Mở ứng dụng nhắn tin
                        startActivity(intent);
                    } else {
                        Constants.showToast("Bạn chuwa cấp quyền sử dụng tin nhắn", getApplicationContext());
                    }
                }
            });
        }else {
            Constants.showToast("Người này đã thanh toán", this);
        }

    }
}