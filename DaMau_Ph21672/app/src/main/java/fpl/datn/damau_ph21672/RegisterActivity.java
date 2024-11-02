package fpl.datn.damau_ph21672;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpl.datn.damau_ph21672.dao.NguoiDungDAO;
import fpl.datn.damau_ph21672.model.NguoiDung;

public class RegisterActivity extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        EditText edtRepass = findViewById(R.id.edtRePass);
        EditText edtName = findViewById(R.id.edtName);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtAddress = findViewById(R.id.edtAddress);
        Button btnReg = findViewById(R.id.btnReg);
        Button btnBack = findViewById(R.id.btnBack);

        nguoiDungDAO = new NguoiDungDAO(this);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtPass.getText().toString();
                String repass = edtRepass.getText().toString();
                if (!pass.equals(repass)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                ;
                }else{
                    String user = edtUser.getText().toString();
                    String name = edtName.getText().toString();
                    String phone = edtPhone.getText().toString();
                    String address = edtAddress.getText().toString();

                    NguoiDung nguoiDung = new NguoiDung(name, phone, address, user, pass);
                    boolean check = nguoiDungDAO.dangKyTaiKhoan(nguoiDung);
                    if (check) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công ~", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}