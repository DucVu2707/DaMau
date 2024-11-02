package fpl.datn.damau_ph21672;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.adapter.PhieuMuonAdapter;
import fpl.datn.damau_ph21672.dao.PhieuMuonDAO;
import fpl.datn.damau_ph21672.model.PhieuMuon;

public class PhieuMuonActivity extends AppCompatActivity {
    private PhieuMuonAdapter phieuMuonAdapter;
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<PhieuMuon> listPhieuMuon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phieu_muon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RecyclerView recyclerViewPm = findViewById(R.id.recycPM);
        FloatingActionButton floatAddPm = findViewById(R.id.floadAddPM);
        phieuMuonDAO = new PhieuMuonDAO(this);
        listPhieuMuon = phieuMuonDAO.getDsPhieuMuon();
        phieuMuonAdapter = new PhieuMuonAdapter(this, listPhieuMuon, phieuMuonDAO);
        recyclerViewPm.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPm.setAdapter(phieuMuonAdapter);


        floatAddPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPhieuMuonDialog();
            }
        });
    }

    private void showAddPhieuMuonDialog() {
        // Sử dụng AlertDialog để hiển thị form thêm mới phiếu mượn
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("Thêm Phiếu Mượn Mới");

        // Inflate layout form thêm mới
        final View dialogView = inflater.inflate(R.layout.dialog_add_phieu_muon, null);
        builder.setView(dialogView);

        // Lấy các view từ form
        EditText etNgayMuon = dialogView.findViewById(R.id.etNgayMuon);
        EditText etMaNguoiDung = dialogView.findViewById(R.id.etMaNguoiDung);
        Switch switchTrangThai = dialogView.findViewById(R.id.switchTrangThai);

        // Xử lý nút Lưu
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String ngayMuon = etNgayMuon.getText().toString();
            int maND = Integer.parseInt(etMaNguoiDung.getText().toString());
            int trangThai = switchTrangThai.isChecked() ? 1 : 0;

            // Lấy tên người dùng từ mã người dùng
            String tenND = phieuMuonDAO.getTenNguoiDungById(maND);

            if (tenND.isEmpty()) {
                Toast.makeText(PhieuMuonActivity.this, "Mã người dùng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo phiếu mượn mới với mã và tên người dùng
            PhieuMuon phieuMuon = new PhieuMuon(0, ngayMuon, trangThai, tenND, maND);

            // Thêm phiếu mượn vào database
            boolean result = phieuMuonDAO.themPhieuMuon(phieuMuon);

            if (result) {
                Toast.makeText(PhieuMuonActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                // Cập nhật lại danh sách phiếu mượn
                listPhieuMuon.add(phieuMuon);
                phieuMuonAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(PhieuMuonActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút hủy
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        // Hiển thị dialog
        builder.create().show();
    }
    public void showEditPhieuMuonDialog(PhieuMuon phieuMuon) {
        // Sử dụng AlertDialog để hiển thị form sửa trạng thái phiếu mượn
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setTitle("Sửa trạng thái Phiếu Mượn");

        // Inflate layout form sửa trạng thái
        final View dialogView = inflater.inflate(R.layout.dialog_edit_trangthai_phieu_muon, null);
        builder.setView(dialogView);

        // Lấy các view từ form
        Switch switchTrangThai = dialogView.findViewById(R.id.switchTrangThai);
        // Set giá trị ban đầu cho switch
        switchTrangThai.setChecked(phieuMuon.getTrangThai() == 1);

        // Xử lý nút Lưu
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            int trangThai = switchTrangThai.isChecked() ? 1 : 0;

            // Cập nhật trạng thái của phiếu mượn
            phieuMuon.setTrangThai(trangThai);
            boolean result = phieuMuonDAO.suaPhieuMuon(phieuMuon);

            if (result) {
                Toast.makeText(PhieuMuonActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                phieuMuonAdapter.notifyDataSetChanged(); // Làm mới danh sách
            } else {
                Toast.makeText(PhieuMuonActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút hủy
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        // Hiển thị dialog
        builder.create().show();
    }

}