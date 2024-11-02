package fpl.datn.damau_ph21672.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.PhieuMuonActivity;
import fpl.datn.damau_ph21672.database.DbHelper;
import fpl.datn.damau_ph21672.model.PhieuMuon;

public class PhieuMuonDAO {
    private DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }
    public ArrayList<PhieuMuon> getDsPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // SELECT với JOIN để lấy tên người dùng thay vì mã người dùng
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.mapm, pm.ngaymuon, pm.trangthai, pm.mand, nd.tennd FROM PHIEUMUON pm JOIN NGUOIDUNG nd ON pm.mand = nd.mand", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int mapm = cursor.getInt(0);
                String ngayMuon = cursor.getString(1);
                int trangThai = cursor.getInt(2);
                String tenND = cursor.getString(4);  // Lấy tên người dùng từ bảng NGUOIDUNG

                // Tạo đối tượng PhieuMuon với tên người dùng thay vì mã người dùng
                list.add(new PhieuMuon(mapm, ngayMuon, trangThai, tenND));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public boolean themPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngaymuon", phieuMuon.getNgayMuon());
        contentValues.put("trangthai", phieuMuon.getTrangThai());
        contentValues.put("mand", phieuMuon.getMaND());

        long result = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        sqLiteDatabase.close();

        return result != -1; // Trả về true nếu thêm thành công, false nếu thêm thất bại
    }
    public String getTenNguoiDungById(int maND) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String tenND = "";
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT tennd FROM NGUOIDUNG WHERE mand = ?", new String[]{String.valueOf(maND)});

        if (cursor.moveToFirst()) {
            tenND = cursor.getString(0);
        }

        cursor.close();
        return tenND;
    }
    public boolean suaPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangthai", phieuMuon.getTrangThai());

        // Cập nhật trạng thái dựa trên mapm
        int result = sqLiteDatabase.update("PHIEUMUON", values, "mapm = ?", new String[]{String.valueOf(phieuMuon.getMaPM())});
        sqLiteDatabase.close();

        return result != 0; // Trả về true nếu cập nhật thành công, false nếu thất bại
    }
    public boolean xoaPhieuMuon(int maPM) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        // Thực hiện xóa phiếu mượn khỏi bảng PHIEUMUON
        int result = sqLiteDatabase.delete("PHIEUMUON", "mapm = ?", new String[]{String.valueOf(maPM)});

        sqLiteDatabase.close();
        return result != 0; // Trả về true nếu xóa thành công, false nếu thất bại
    }

}
