package fpl.datn.damau_ph21672;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;

import fpl.datn.damau_ph21672.adapter.LoaiSachAdapter;
import fpl.datn.damau_ph21672.dao.LoaiSachDAO;
import fpl.datn.damau_ph21672.model.LoaiSach;

public class CategoryActivity extends AppCompatActivity {
    private LoaiSachDAO loaiSachDAO;
    RecyclerView recycCate;
    private ArrayList<LoaiSach> list;
    private RelativeLayout main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //thiết kế giao diện chính + giao diện item
        main = findViewById(R.id.main);
        recycCate = findViewById(R.id.recycCategory);
        FloatingActionButton floatAdd = findViewById(R.id.floatAdd);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hiển thị dialog
                showDialogThem();
            }
        });
        //data
        loaiSachDAO = new LoaiSachDAO(this);


        //adapter
        loadData();


    }
    private void loadData(){
        list = loaiSachDAO.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycCate.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(this, list, loaiSachDAO);
        recycCate.setAdapter(loaiSachAdapter);
    }
    public void showDialogThem() {
        AlertDialog.Builder buidler = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cate, null);
        buidler.setView(view);

        AlertDialog alertDialog = buidler.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        //ánh xạ 
        EditText edtTenLoai = view.findViewById(R.id.edtTenLoai);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();
                if (tenLoai.equals("")) {
                    Toast.makeText(CategoryActivity.this, "Nhập thiếu thông tin~", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean check = loaiSachDAO.themLoaiSach(tenLoai);
                if (check) {
//                    Toast.makeText(CategoryActivity.this, "Thêm thành công loại sách~", Toast.LENGTH_SHORT).show();
                    Snackbar.make(main, "Thêm thành công~", Snackbar.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(CategoryActivity.this, "Thêm thật bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

}