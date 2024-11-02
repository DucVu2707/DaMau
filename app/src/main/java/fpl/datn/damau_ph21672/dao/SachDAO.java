package fpl.datn.damau_ph21672.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.database.DbHelper;
import fpl.datn.damau_ph21672.model.Sach;

public class SachDAO {
    private DbHelper dbHelper;

    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //lấy danh sách các cuốn sách
    public ArrayList<Sach> getDsSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        //Select * from sach

        Cursor cursor = sqLiteDatabase.rawQuery("select s.masach, s.tensach, s.tacgia, s.giaban, s.maloai, l.tenloai from SACH s, LOAISACH l where s.maloai = l.maloai", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));

            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themSach(Sach sach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", sach.getTensach());
        contentValues.put("tacgia", sach.getTacgia());
        contentValues.put("giaban", sach.getGiaban());
        contentValues.put("maloai", sach.getMaloai()); // Mã loại sách (liên kết với bảng LOAISACH)

        long result = sqLiteDatabase.insert("SACH", null, contentValues);
        sqLiteDatabase.close();

        return result != -1; // Trả về true nếu thêm thành công, false nếu thêm thất bại
    }

    public boolean suaSach(Sach sach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTensach());
        values.put("tacgia", sach.getTacgia());
        values.put("giaban", sach.getGiaban());
        values.put("maloai", sach.getMaloai());

        // Cập nhật thông tin sách dựa trên masach
        int result = sqLiteDatabase.update("SACH", values, "masach = ?", new String[]{String.valueOf(sach.getMasach())});
        sqLiteDatabase.close();

        return result != 0; // Trả về true nếu cập nhật thành công, false nếu thất bại
    }
    public int xoaSach(int masach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        // Kiểm tra sự tồn tại của sách trong bảng CTPM
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CTPM WHERE masach = ?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() > 0) {
            return 0; // Không được xóa vì sách đang được liên kết trong CTPM (khóa ngoại)
        } else {
            // Xóa sách khỏi bảng SACH
            int check = sqLiteDatabase.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
            if (check == 0) {
                return -1; // Không xóa được vì sách không tồn tại
            } else {
                return 1; // Xóa thành công
            }
        }
    }

}
