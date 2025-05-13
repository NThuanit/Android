package com.example;

import android.content.Context;
import android.widget.Toast;

public class Constants {
    public static final String ENTER_USER_NAME = "Email trống";
    public static final String TIEN_THU = "Tiền thu";
    public static final String TIEN_CHI = "Tiền chi";
    public static final String BTN_TIEN_CHI = "Nhập khoản tiền chi";
    public static final String BTN_TIEN_THU = "Nhập khoản tiền thu";
    public static final String ADD_SUCCESSFUL = "Thêm thành công";
    public static final String UPDATE_SUCCESSFUL = "Cập nhật thành công";
    public static final String PLS_ENTER_THE_AMOUNT = "Vui lòng nhâp số tiền";

    public static void showToast(String string, Context context) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}


