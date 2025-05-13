package com.example.qlct_n3.base;

import static com.example.qlct_n3.ViewExtention.convertDrawableToBase64;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.qlct_n3.Dao.Daoo;
import com.example.qlct_n3.Model.DanhMuc;
import com.example.qlct_n3.Model.GiaoDich;
import com.example.qlct_n3.Model.HoaDon;
import com.example.qlct_n3.Model.NguoiDung;
import com.example.qlct_n3.R;

import java.util.concurrent.CompletableFuture;

import kotlinx.coroutines.GlobalScope;

@Database(entities = {DanhMuc.class, GiaoDich.class, HoaDon.class, NguoiDung.class}, version = 1, exportSchema = false)
public abstract class DataBaseManager extends RoomDatabase {
    public abstract Daoo getItemDAO();

    private static final String DATABASE_NAME = "database.db";
    private static volatile DataBaseManager instance;

    public static DataBaseManager getInstance(Context context) {
        if (instance == null) {
            synchronized (DataBaseManager.class) {
                if (instance == null) {
                    instance = buildDatabase(context);
                }
            }
        }
        return instance;
    }

    private static DataBaseManager buildDatabase(Context context) {
        return Room.databaseBuilder(context, DataBaseManager.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {

                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                            prepopulateDatabase(instance.getItemDAO(), context);
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    private static void prepopulateDatabase(Daoo dao, Context context) {
        dao.themDanhMuc(new DanhMuc(1, convertDrawableToBase64(context, R.drawable.icon_eat_and_drink), "Ăn uống", true));
        dao.themDanhMuc(new DanhMuc(2, convertDrawableToBase64(context, R.drawable.icon_daily_spending), "Chi tiêu hàng ngày", true));
        dao.themDanhMuc(new DanhMuc(3, convertDrawableToBase64(context, R.drawable.icon_cloths), "Quần áo", true));
        dao.themDanhMuc(new DanhMuc(4, convertDrawableToBase64(context, R.drawable.icon_cosmetics), "Mỹ phẩm", true));
        dao.themDanhMuc(new DanhMuc(5, convertDrawableToBase64(context, R.drawable.icon_communication_fee), "Phí giao lưu", true));
        dao.themDanhMuc(new DanhMuc(6, convertDrawableToBase64(context, R.drawable.icon_medical), "Y tế", true));
        dao.themDanhMuc(new DanhMuc(7, convertDrawableToBase64(context, R.drawable.icon_education), "Giáo dục", true));
        dao.themDanhMuc(new DanhMuc(8, convertDrawableToBase64(context, R.drawable.icon_electricity_bill), "Tiền điện", true));
        dao.themDanhMuc(new DanhMuc(9, convertDrawableToBase64(context, R.drawable.icon_go), "Đi lại", true));
        dao.themDanhMuc(new DanhMuc(10, convertDrawableToBase64(context, R.drawable.icon_bill_contact), "Phí liên lạc", true));
        dao.themDanhMuc(new DanhMuc(11, convertDrawableToBase64(context, R.drawable.icon_bill_home), "Tiền nhà", true));
        dao.themDanhMuc(new DanhMuc(21, convertDrawableToBase64(context, R.drawable.icon_salary), "Tiền lương", false));
        dao.themDanhMuc(new DanhMuc(22, convertDrawableToBase64(context, R.drawable.icon_allowance), "Tiền phụ cấp", false));
        dao.themDanhMuc(new DanhMuc(23, convertDrawableToBase64(context, R.drawable.icon_bonus), "Tiền thưởng", false));
        dao.themDanhMuc(new DanhMuc(24, convertDrawableToBase64(context, R.drawable.icon_investment_money), "Đầu tư", false));
        dao.themDanhMuc(new DanhMuc(25, convertDrawableToBase64(context, R.drawable.icon_supplementary_income), "Thu nhập phụ", false));
    }
    public static void deleteAllTable(){
        instance.clearAllTables();
    }
}


