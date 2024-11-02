package fpl.datn.damau_ph21672;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LinearLayout lineCate = findViewById(R.id.lineLoaiSach);
        LinearLayout lineBook = findViewById(R.id.lineSach);
        LinearLayout linePm = findViewById(R.id.linePM);
        LinearLayout lineThonke = findViewById(R.id.lineThongke);
        LinearLayout lineHistory = findViewById(R.id.lineLichSu);
        //lấy role đã lưu trong sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);
        switch (role) {
            case 1://người dùng
                lineBook.setVisibility(View.GONE);
                lineCate.setVisibility(View.GONE);
                lineThonke.setVisibility(View.GONE);
                linePm.setVisibility(View.GONE);
                break;
            case 2://thủ thư
                lineBook.setVisibility(View.GONE);
                lineCate.setVisibility(View.GONE);
                lineThonke.setVisibility(View.GONE);
                lineHistory.setVisibility(View.GONE);
                break;
            case 3://admin
                lineHistory.setVisibility(View.GONE);
                break;
            default:
                lineBook.setVisibility(View.GONE);
                lineCate.setVisibility(View.GONE);
                lineThonke.setVisibility(View.GONE);
                lineHistory.setVisibility(View.GONE);
                linePm.setVisibility(View.GONE);

                break;
        }

        lineCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
            }
        });

        lineBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BookActivity.class));
            }
        });
        linePm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhieuMuonActivity.class));
            }
        });
        lineThonke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThongKeActivity.class));
            }
        });
        lineHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }

}