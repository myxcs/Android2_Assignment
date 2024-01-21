package com.example.android2_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android2_assignment.dao.NguoiDungDAO;

public class RegisterActivity extends AppCompatActivity {
    EditText edFullname, edUsername, edPassword, edRePassword;
    Button btRegister;
    TextView tvLogin;

    private NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //ánh xạ
        edFullname = findViewById(R.id.ed_fullname_register);
        edUsername = findViewById(R.id.ed_username_register);
        edPassword = findViewById(R.id.ed_password_register);
        edRePassword = findViewById(R.id.ed_repassword_register);
        btRegister = findViewById(R.id.bt_register);
        tvLogin = findViewById(R.id.tv_login);

        nguoiDungDAO = new NguoiDungDAO(this);

        //xử li nút đăng ký
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = edFullname.getText().toString();
                String userName = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String rePassword = edRePassword.getText().toString();

                if (fullName.isEmpty() || userName.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập dầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(RegisterActivity.this, "Password không trùng nhau, vui lòng nhập lại password", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = nguoiDungDAO.Register(userName, password, fullName);
                    if (check) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        //xử li nút đăng nhập
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}