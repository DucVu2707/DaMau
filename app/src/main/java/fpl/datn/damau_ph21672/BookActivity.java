package fpl.datn.damau_ph21672;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.material.snackbar.Snackbar;

import fpl.datn.damau_ph21672.adapter.SachAdapter;
import fpl.datn.damau_ph21672.dao.SachDAO;
import fpl.datn.damau_ph21672.model.Sach;

public class BookActivity extends AppCompatActivity {
    private RecyclerView recyclerViewBook;
    private SachDAO sachDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerViewBook = findViewById(R.id.recycBook);
        FloatingActionButton floatAdd = findViewById(R.id.floadAddBook);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThem();
            }
        });
        sachDAO = new SachDAO(this);

        loadData();
    }

    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewBook.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(this, sachDAO.getDsSach());
        recyclerViewBook.setAdapter(adapter);
    }
    public void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_book, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        // Ánh xạ các view
        EditText edtTenSach = view.findViewById(R.id.edtTenSach);


        EditText edtTacGia = view.findViewById(R.id.edtTacGia);
        EditText edtGiaBan = view.findViewById(R.id.edtGiaBan);
        EditText edtMaLoai = view.findViewById(R.id.edtMaLoai);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        // Xử lý sự kiện nút Lưu
        btnLuu.setOnClickListener(v -> {
            String tenSach = edtTenSach.getText().toString();
            String tacGia = edtTacGia.getText().toString();
            int giaBan = Integer.parseInt(edtGiaBan.getText().toString());
            int maLoai = Integer.parseInt(edtMaLoai.getText().toString());

            // Tạo đối tượng Sach mới
            Sach sach = new Sach( tenSach, tacGia, giaBan, maLoai, "");

            // Gọi phương thức thêm sách trong SachDAO
            boolean check = sachDAO.themSach(sach);
            if (check) {
                Snackbar.make(findViewById(R.id.main), "Thêm sách thành công!", Snackbar.LENGTH_SHORT).show();
                loadData(); // Tải lại danh sách sau khi thêm sách thành công
                dialog.dismiss();
            } else {
                Toast.makeText(BookActivity.this, "Thêm sách thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút Hủy
        btnHuy.setOnClickListener(v -> dialog.dismiss());
    }

}