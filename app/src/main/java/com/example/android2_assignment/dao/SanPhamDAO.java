package com.example.android2_assignment.dao;

import android.content.ContentValues;
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

    //them sp
    public boolean themSPmoi(Product product){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //them du lieu vao content value
        contentValues.put("TENSP",product.getTensp());
        contentValues.put("GIABAN",product.getGiaban());
        contentValues.put("SOLUONG",product.getSoluong());

        long check = sqLiteDatabase.insert("SANPHAM",null,contentValues);
        return check != -1;
//         if(check == -1)
//            return false;
//        else
//            return true;
    }

    //chinh sua sp
    public boolean chinhSuaSP(Product product){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TENSP",product.getTensp());
        contentValues.put("GIABAN",product.getGiaban());
        contentValues.put("SOLUONG",product.getSoluong());

        int check = sqLiteDatabase.update("SANPHAM",contentValues,"MASP=?",new String[]{String.valueOf(product.getMasp())});
        if (check <= 0)
            return false;
        else
            return true;
    }
    public boolean xoaSP(int masp){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("SANPHAM","MASP=?",new String[]{String.valueOf(masp)});
        if (check <= 0)
            return false;
        else
            return true;
    }
}
