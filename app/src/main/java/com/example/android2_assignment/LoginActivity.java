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

public class LoginActivity extends AppCompatActivity {

    private NguoiDungDAO nguoiDungDAO;

    EditText edUsername, edPassword;
    Button btLogin;
    TextView tvForgotPassword, tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ánh xạ
        edPassword = findViewById(R.id.ed_password);
        edUsername = findViewById(R.id.ed_username);
        btLogin = findViewById(R.id.bt_login);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvSignUp = findViewById(R.id.tv_sign_up);

        nguoiDungDAO = new NguoiDungDAO(this);

        //xử lí nút đăng nhập
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();

                if (nguoiDungDAO.CheckLogin(username, password)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //xử lí nút đăng kí
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Work in progress", Toast.LENGTH_SHORT).show();
            }
        });
    }
}