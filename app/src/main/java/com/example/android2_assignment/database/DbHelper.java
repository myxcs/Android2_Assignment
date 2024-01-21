package com.example.android2_assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context){
        super(context, "AND102", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //tao bang nguoi dung
        String NguoiDung = "CREATE TABLE NGUOIDUNG (TENDANGNHAP TEXT PRIMARY KEY, " +
                "MATKHAU TEXT, " +
                "HOTEN TEXT)";
        sqLiteDatabase.execSQL(NguoiDung);

        //tao bang san pham
        String SanPham = "CREATE TABLE SANPHAM (MASP INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TENSP TEXT, " +
                "GIABAN INTEGER, " +
                "SOLUONG INTEGER)";
        sqLiteDatabase.execSQL(SanPham);

        //add data
        String addNguoiDung = "INSERT INTO NGUOIDUNG VALUES " +
                "('ABC', '123', 'Nguyen Van A')," +
                "('DEF', '456', 'Nguyen Van B'), " +
                "('GHI', '789', 'Nguyen Van C')";
        sqLiteDatabase.execSQL(addNguoiDung);

        String addSanPham = "INSERT INTO SANPHAM VALUES " +
                "(1, 'May tinh', 1000000, 10), " +
                "(2, 'Laptop', 2000000, 20), " +
                "(3, 'Dien thoai', 3000000, 30)";
        sqLiteDatabase.execSQL(addSanPham);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //update, xoa v
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SANPHAM");
            onCreate(sqLiteDatabase);
        }
    }
}
