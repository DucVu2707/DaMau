package fpl.datn.damau_ph21672.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.database.DbHelper;
import fpl.datn.damau_ph21672.model.LoaiSach;

public class LoaiSachDAO {

    private DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }


    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LOAISACH", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        return list;

    }

    public boolean themLoaiSach(String tenLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenLoai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
//        if (check == -1) {
//            return false;
//        }else return true;
        return check != -1;
    }

    public boolean suaLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenloai", loaiSach.getTenloai());

        int check = sqLiteDatabase.update("LOAISACH", values, "maloai = ?", new String[]{String.valueOf(loaiSach.getMaloai())});
        return check != 0;

    }

    /*
     * -1: ko xóa đk do bị lỗi blabla
     *  0: ko xóa đk do ràng buộc khóa ngoại
     *  1: xóa thàng công
     * */

    public int xoaLoaiSach(int maloai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        //kiểm tra sự tồn tại của những cuốn sách trong bảng sách với thể loại đang thực hiện xóa

        Cursor cursor = sqLiteDatabase.rawQuery("select * from SACH where maloai = ?", new String[]{String.valueOf(maloai)});
        if (cursor.getCount() > 0) {
            return 0;//không được xóa vì ràng buộc fr key
        } else {
            int check = sqLiteDatabase.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(maloai)});
            if (check == 0) {
                return -1;//không xóa vì không thấy ...
            } else {
                return 1;
            }
        }
    }

}
