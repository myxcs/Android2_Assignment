package com.example.android2_assignment.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android2_assignment.database.DbHelper;
import com.example.android2_assignment.model.Product;

import java.util.ArrayList;

public class SanPhamDAO {
    private DbHelper dbHelper;
    public SanPhamDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    //lấy danh sách sản phẩm
    public ArrayList<Product> getDS(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM sanpham",null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
               list.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }
}
