package com.example.plantshop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantshop.R;
import com.example.plantshop.firebase.DAO;

public class Activity_DangKy extends AppCompatActivity {

    private EditText edt_SEmail, edt_Spass, edt_SConfirmPass;
    private Button btn_Submit;
    private TextView tv_back;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        Activity_DangNhap.makeStatusBarTransparent(getWindow(), Activity_DangKy.this);


        edt_SEmail = findViewById(R.id.edt_SEmai);
        edt_Spass = findViewById(R.id.edt_Spass);
        edt_SConfirmPass = findViewById(R.id.edt_SConfirmPass);
        btn_Submit = findViewById(R.id.btn_Submit);
        tv_back = findViewById(R.id.tv_back);
        
        dao = new DAO();

        tv_back.setOnClickListener(v -> {
            finish();
        });

        btn_Submit.setOnClickListener(v -> {
           String email = edt_SEmail.getText().toString();
           String pass = edt_Spass.getText().toString();
           String confirmPass = edt_SConfirmPass.getText().toString();
            String regex_email = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,4}){1,2}$";

           if(email.isEmpty()){
               edt_SEmail.setError("Vui lòng nhập email");
               edt_SEmail.requestFocus();
           }else if (pass.isEmpty()){
               edt_Spass.setError("Vui lòng nhập mật khẩu");
               edt_Spass.requestFocus();
           }else if (confirmPass.isEmpty()){
               edt_SConfirmPass.setError("Vui lòng nhập lại mật khẩu");
               edt_SConfirmPass.requestFocus();
           }else if (!pass.equals(confirmPass)){
               edt_SConfirmPass.setError("Mật khẩu không trùng khớp");
               edt_SConfirmPass.requestFocus();
           } else if (!email.matches(regex_email)) {
               edt_SEmail.setError("Email không đúng");
               edt_SEmail.requestFocus();
           }else {

                dao.setUser(email, pass);
                if(dao.setUser(email, pass)){
                    Intent intent = new Intent(Activity_DangKy.this, Activity_DangNhap.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                }else {
                    edt_SEmail.setError("Email đã được đăng ký");
                    edt_SEmail.requestFocus();
                }
                
           }


        });

    }
}