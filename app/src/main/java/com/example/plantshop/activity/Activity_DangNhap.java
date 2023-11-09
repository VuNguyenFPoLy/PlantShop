package com.example.plantshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantshop.R;
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.model.Account;

import java.util.ArrayList;

public class Activity_DangNhap extends AppCompatActivity {

    private EditText edt_LEmai, edt_Lpass;
    private Button btn_Login;
    private TextView tv_Forgotpass, tv_SignUp;
    private DAO dao;
    private ArrayList<Account> listAccount;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        makeStatusBarTransparent(getWindow(), Activity_DangNhap.this);


        edt_LEmai = findViewById(R.id.edt_LEmai);
        edt_Lpass = findViewById(R.id.edt_Lpass);
        btn_Login = findViewById(R.id.btn_Login);
        tv_Forgotpass = findViewById(R.id.tv_Forgotpass);
        tv_SignUp = findViewById(R.id.tv_SignUp);


        dao = new DAO();
        listAccount = new ArrayList<>();
        listAccount = dao.getListAccount();

        tv_SignUp.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_DangNhap.this, Activity_DangKy.class);
            startActivity(intent);
            finish();
        });

        tv_Forgotpass.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_DangNhap.this, Activity_ForgotPass.class);
            startActivity(intent);
            finish();
        });

        btn_Login.setOnClickListener(v -> {

            String email = edt_LEmai.getText().toString();
            String pass = edt_Lpass.getText().toString();
            String regex_email = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,4}){1,2}$";


            if (email.isEmpty()) {
                edt_LEmai.setError("Vui lòng nhập email");
                edt_LEmai.requestFocus();
            } else if (pass.isEmpty()) {
                edt_Lpass.setError("Vui lòng nhập mật khẩu");
                edt_Lpass.requestFocus();
            } else if (!email.matches(regex_email)) {
                edt_LEmai.setError("Email không đúng");
                edt_LEmai.requestFocus();
            } else {

                boolean checkUser = false;
                boolean checkPass = false;

                for (Account ac : listAccount
                ) {
                    if (email.equals(ac.getUserName())) {
                        checkUser = true;
                        if (pass.equals(ac.getPassWord())) {
                            checkPass = true;
                            id = ac.getIdAcount();
                            break;
                        } else {
                            edt_Lpass.setError("Mật khẩu không đúng");
                            edt_Lpass.requestFocus();
                        }
                    }
                }

                if (!checkUser) {
                    edt_LEmai.setError("Sai email hoặc chưa đăng ký");
                    edt_LEmai.requestFocus();
                } else if (!checkPass) {
                    edt_Lpass.setError("Mật khẩu không đúng");
                    edt_Lpass.requestFocus();
                } else {
                    Intent intent = new Intent(Activity_DangNhap.this, MainActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public static void makeStatusBarTransparent(Window window, Activity activity) {

            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |

                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Thêm flag này để biểu tượng trở thành màu đen
            );
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.white)); // Đặt màu nền thành màu trắng

    }

    }
