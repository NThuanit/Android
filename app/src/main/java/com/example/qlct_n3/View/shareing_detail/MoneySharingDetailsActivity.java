package com.example.qlct_n3.View.shareing_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;

    private Long idHoaDon;
    ActivityMoneySharingDetailsBinding binding;
    MoneySharingDetailsViewModel viewModel;
    ShareMoneyDetailAdapter adapter;
    List<NguoiDung> listNguoiDung;
    private NguoiDung currentNguoiDung; // Để lưu người dùng hiện tại khi yêu cầu quyền

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

    // hàm set trạng thái đã thanh toán cho các thành viên
    private void Done(NguoiDung nguoiDung) {
        nguoiDung.setTrangThai(true);
        nguoiDung.setKhoanChi(0L);
        viewModel.editMember(nguoiDung, this);
        getData();
    }

    private void Pay(NguoiDung nguoiDung) {
        // Lưu người dùng hiện tại để sử dụng sau khi yêu cầu quyền
        currentNguoiDung = nguoiDung;

        if(nguoiDung.getTrangThai() == false) {
            // Kiểm tra quyền SMS
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                // Yêu cầu quyền
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
                return;
            }

            // Số điện thoại đích
            String phoneNumber = nguoiDung.getSdt();

            // Kiểm tra số điện thoại
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                Constants.showToast("Số điện thoại không hợp lệ", this);
                return;
            }

            viewModel.getHoaDon(nguoiDung.getIdHoaDon(), getApplicationContext());
            viewModel.hoaDon().observe(this, new Observer<HoaDon>() {
                @Override
                public void onChanged(HoaDon hoaDon) {
                    // Nội dung tin nhắn
                    String message = "Bạn đang thiếu " + (0-nguoiDung.getKhoanChi()) + "đ vào ngày " + hoaDon.getNgay() + ".Vui lòng thanh toán.";

                    // Gửi SMS trực tiếp bằng SmsManager
                    sendSMSDirectly(phoneNumber, message);
                }
            });
        } else {
            Constants.showToast("Người này đã thanh toán", this);
        }
    }

    // Phương pháp sử dụng SmsManager để gửi SMS trực tiếp
    private void sendSMSDirectly(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();

            // Nếu tin nhắn quá dài, chia thành nhiều phần
            if (message.length() > 160) {
                ArrayList<String> parts = smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            } else {
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            }

            Constants.showToast("Tin nhắn đã gửi thành công", getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, "Error sending SMS: " + e.getMessage());
            e.printStackTrace();
            Constants.showToast("Không thể gửi SMS: " + e.getMessage(), getApplicationContext());
        }
    }

    // Xử lý kết quả yêu cầu quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, thực hiện lại chức năng
                if (currentNguoiDung != null) {
                    Pay(currentNguoiDung);
                }
            } else {
                // Quyền bị từ chối
                Constants.showToast("Bạn cần cấp quyền SMS để sử dụng chức năng này", this);

                // Hiển thị dialog giải thích tại sao cần quyền
                showPermissionExplanationDialog();
            }
        }
    }

    // Hiển thị dialog giải thích tại sao cần quyền SMS
    private void showPermissionExplanationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cần quyền SMS")
                .setMessage("Ứng dụng cần quyền SMS để gửi tin nhắn đòi nợ. Vui lòng cấp quyền trong cài đặt.")
                .setPositiveButton("Đi đến Cài đặt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Mở cài đặt ứng dụng
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}