package fpl.datn.damau_ph21672.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fpl.datn.damau_ph21672.database.DbHelper;
import fpl.datn.damau_ph21672.model.NguoiDung;

public class NguoiDungDAO {
    private DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public NguoiDungDAO(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);

    }

    //kiểm tra thông tin đăng nhập
    //nếu có giá trị thì ==> true
    public boolean KiemTraDangNhap(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from NGUOIDUNG where tendangnhap = ? and matkhau = ?", new String[]{username, password});
//        if (cursor.getCount() > 0)
//            return true;
//        else
//            return false;

        //lưu vai trò của người đăng nhập
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("role", cursor.getInt(6));
            editor.apply();
        }

        return cursor.getCount() > 0;
    }

    public boolean dangKyTaiKhoan(NguoiDung nguoiDung){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tennd", nguoiDung.getTennd());
        values.put("sdt", nguoiDung.getSdt());
        values.put("diachi", nguoiDung.getDiachi());
        values.put("tendangnhap", nguoiDung.getTendangnhap());
        values.put("matkhau", nguoiDung.getMatkhau());
        values.put("role", 1);
        long check = sqLiteDatabase.insert("NGUOIDUNG", null, values );
        return check != -1;
    }
}
