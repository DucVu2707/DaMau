package fpl.datn.damau_ph21672;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.adapter.ThongKeAdapter;
import fpl.datn.damau_ph21672.database.DbHelper;

public class ThongKeActivity extends AppCompatActivity {
    private RecyclerView recyThongKe;
    private ThongKeAdapter thongKeAdapter;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyThongKe = findViewById(R.id.recyThongKe);
        recyThongKe.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DbHelper(this);
        ArrayList<String> topBooks = dbHelper.getTop5MostBorrowedBooks(); // Sử dụng ArrayList

        thongKeAdapter = new ThongKeAdapter(topBooks);
        recyThongKe.setAdapter(thongKeAdapter);
    }
}