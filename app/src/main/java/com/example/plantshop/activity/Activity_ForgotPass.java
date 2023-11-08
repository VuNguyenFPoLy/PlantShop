package com.example.plantshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plantshop.R;
import com.example.plantshop.firebase.DAO;

public class Activity_ForgotPass extends AppCompatActivity {

    private EditText edt_FEmai;
    private Button btn_Continue;
    private TextView tv_BackLogin, tv_SignUp;
    private DAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass);

        edt_FEmai = findViewById(R.id.edt_FEmai);
        btn_Continue = findViewById(R.id.btn_Continue);
        tv_BackLogin = findViewById(R.id.tv_BackLogin);
        tv_SignUp = findViewById(R.id.tv_SignUp);

        dao = new DAO();

        tv_BackLogin.setOnClickListener(v -> {
            finish();
        });

        tv_SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ForgotPass.this, Activity_DangKy.class);
            startActivity(intent);
            finish();
        });

        btn_Continue.setOnClickListener(v -> {
            String email = edt_FEmai.getText().toString();
            String regex_email = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,4}){1,2}$";

            if(email.isEmpty()){
                edt_FEmai.setError("Vui lòng nhập email");
                edt_FEmai.requestFocus();
            }if(!email.matches(regex_email)){
                edt_FEmai.setError("Email không đúng");
                edt_FEmai.requestFocus();
            }else{
                 dao.checkUser(email);
                if(dao.checkUser(email)){
                    Intent intent = new Intent(Activity_ForgotPass.this, Activity_Input_Code.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }else {
                    edt_FEmai.setError("Sai email hoặc chưa được đăng ký");
                    edt_FEmai.requestFocus();
                }
            }
        });
    }
}