package fpl.datn.damau_ph21672.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.model.PhieuMuon;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "QuanLyThuVien", null, 7);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tLoaiSach = "create table LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(tLoaiSach);
        db.execSQL("insert into LOAISACH values(1, 'thiếu nhi'), (2, 'tình cảm'), (3, 'hành động')");


        String tSach = "create table SACH(masach integer primary key autoincrement, tensach text, tacgia text, giaban integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(tSach);
        db.execSQL("insert into SACH values (1, 'Kể chuyện cổ tích', 'Nguyễn Nhật Ánh', 15000, 1), (2, 'Trạng Quỳnh', 'Kim Đồng', 10000, 1), (3, 'Doraemon', 'Kim Đồng', 20000, 1)");


        String tNguoiDung = "create table NGUOIDUNG(mand integer primary key autoincrement, tennd text, sdt text, diachi text, tendangnhap text, matkhau text, role integer)";
        db.execSQL(tNguoiDung);
        db.execSQL("insert into NGUOIDUNG values(1, 'Nguyễn Văn Anh', '0987294859', 'Việt Nam', 'vananh01', '123456', 1), (2, 'Trịnh Hòa Bình', '039183959', 'Việt Nam', 'hoabinh01', '123456', 2), (3, 'Lê Văn Hùng', '029372846', 'Việt Nam', 'hunglv01', '123456', 3)");

        /*
         * role:
         * 1- người dùng
         * 2- thủ thư
         * 3- admin
         * */
        String tPhieuMuon = "create table PHIEUMUON(mapm integer primary key autoincrement, ngaymuon text, trangthai integer, mand integer references NGUOIDUNG(mand))";
        db.execSQL(tPhieuMuon);
        db.execSQL("insert into PHIEUMUON values(1, '20/09/2024', 1, 1), (2, '21/09/2024',0,1)");

        String tCTPM = "create table CTPM(mactpm integer primary key autoincrement, mapm integer references PHIEUMUON(mapm), masach integer references SACH(masach), soluong integer)";
        db.execSQL(tCTPM);
        db.execSQL("insert into CTPM values(1, 1, 1, 2), (2, 2, 2, 6), (3, 2, 3, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists LOAISACH");
            db.execSQL("drop table if exists SACH");
            db.execSQL("drop table if exists NGUOIDUNG");
            db.execSQL("drop table if exists PHIEUMUON");
            db.execSQL("drop table if exists CTPM");
            onCreate(db);
        }
    }

    public ArrayList<String> getTop5MostBorrowedBooks() {
        ArrayList<String> topBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SACH.tensach, SUM(CTPM.soluong) AS tong_muon " +
                        "FROM SACH JOIN CTPM ON SACH.masach = CTPM.masach " +
                        "GROUP BY SACH.tensach " +
                        "ORDER BY tong_muon ASC " +
                        "LIMIT 5", null);

        if (cursor.moveToFirst()) {
            do {
                String tensach = cursor.getString(0);
                int tongMuon = cursor.getInt(1);
                topBooks.add(tensach + " - " + tongMuon + " lượt mượn");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topBooks;
    }
    public ArrayList<PhieuMuon> getLichSuPhieuMuon(int maND) {
        ArrayList<PhieuMuon> phieuMuonList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM PHIEUMUON WHERE mand = ?", new String[]{String.valueOf(maND)});

        if (cursor.moveToFirst()) {
            do {
                int maPM = cursor.getInt(0);
                String ngayMuon = cursor.getString(1);
                int trangThai = cursor.getInt(2);
                String tenND = "";  // Bạn có thể bổ sung tên người dùng từ bảng NGUOIDUNG
                PhieuMuon phieuMuon = new PhieuMuon(maPM, ngayMuon, trangThai, tenND, maND);
                phieuMuonList.add(phieuMuon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return phieuMuonList;
    }

}
