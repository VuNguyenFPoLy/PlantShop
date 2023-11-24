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
import com.example.plantshop.model.Account;

import java.util.ArrayList;

public class Activity_NewPass extends AppCompatActivity {

    private EditText edt_NewPass, edt_ConfirmNewPass;
    private Button btn_Login;
    private TextView tv_Back, tv_SignUp;
    private ArrayList<Account> listAccount;
    private DAO dao;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        Activity_DangNhap.makeStatusBarTransparent(getWindow(), Activity_NewPass.this);


        edt_NewPass = findViewById(R.id.edt_NewPass);
        edt_ConfirmNewPass = findViewById(R.id.edt_ConfirmNewPass);
        btn_Login = findViewById(R.id.btn_Submit);
        tv_Back = findViewById(R.id.tv_Back);
        tv_SignUp = findViewById(R.id.tv_SignUp);


        Intent getEmail = getIntent();
        String email = getEmail.getStringExtra("email");

        account = new Account();
        dao = new DAO();
        listAccount = new ArrayList<>();
        listAccount = dao.getListAccount();

        Intent i_backToLogin = new Intent(Activity_NewPass.this, Activity_DangNhap.class);

        tv_Back.setOnClickListener(v -> {
            startActivity(i_backToLogin);
            finish();
        });

        tv_SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_NewPass.this, Activity_DangKy.class);
            startActivity(intent);
            finish();
        });

        btn_Login.setOnClickListener(v -> {
            String newPass = edt_NewPass.getText().toString();
            String confirmNewPass = edt_ConfirmNewPass.getText().toString();

            if(newPass.isEmpty()){
                edt_NewPass.setError("Vui lòng nhập mật khẩu mới");
                edt_NewPass.requestFocus();
            } else if (confirmNewPass.isEmpty()) {
                edt_ConfirmNewPass.setError("Vui lòng nhập lại mật khẩu mới");
                edt_ConfirmNewPass.requestFocus();
            } else if (!newPass.equals(confirmNewPass)) {
                edt_ConfirmNewPass.setError("Mật khẩu không trùng khớp nhau");
                edt_ConfirmNewPass.requestFocus();
            } else {

                for (Account ac : listAccount
                ) {
                    if(email.equals(ac.getUserName())){
                        account = ac;
                        String checkemail = account.getUserName();
                        break;
                    }
                }

                account.setPassWord(newPass);
                dao.updateAccount(account);
                if(dao.updateAccount(account)){

                    startActivity(i_backToLogin);
                    finish();
                    Toast.makeText(this, "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Lỗi! kiểm tra mạng của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}