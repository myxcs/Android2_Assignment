package com.example.android2_assignment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android2_assignment.database.DbHelper;

public class NguoiDungDAO {
    private DbHelper dbHelper;

    public NguoiDungDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    //login
    public boolean CheckLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE TENDANGNHAP = ? AND MATKHAU = ?", new String[]{username, password});
        if(cursor.getCount() > 0)
            {
                return true;
            }
        return false;
    }
    public boolean Register(String username, String password, String hoten){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        //vứt dữ liệu vào ContentValues
        ContentValues values = new ContentValues();
        values.put("TENDANGNHAP", username);
        values.put("MATKHAU", password);
        values.put("HOTEN", hoten);

        //check dữ liệu oke chưa, sqLiteDatabase.insert là 1 id chứa bằng long, nếu lỗi sẽ return về -1
        long check = sqLiteDatabase.insert("NGUOIDUNG", null, values);
        if (check == -1){
            return false;
        }
        return true;
    }
}
