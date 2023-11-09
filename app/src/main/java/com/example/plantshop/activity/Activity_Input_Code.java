package com.example.plantshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plantshop.R;
import com.example.plantshop.config.ConfigNotification;

import java.util.Date;

public class Activity_Input_Code extends AppCompatActivity {

    private EditText edtNumOne, edtNumTwo, edtNumThree, edtNumFour;
    private Button btn_Continue;
    private TextView tv_BackLogin, tv_SignUp, tv_ResendCode;
    private String otp1, otp2, otp3, otp4, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_code);
        Activity_DangNhap.makeStatusBarTransparent(getWindow(), Activity_Input_Code.this);


        edtNumOne = findViewById(R.id.edtNumOne);
        edtNumTwo = findViewById(R.id.edtNumTwo);
        edtNumThree = findViewById(R.id.edtNumThree);
        edtNumFour = findViewById(R.id.edtNumFour);
        btn_Continue = findViewById(R.id.btn_Continue);
        tv_BackLogin = findViewById(R.id.tv_BackLogin);
        tv_SignUp = findViewById(R.id.tv_SignUp);
        tv_ResendCode = findViewById(R.id.tv_ResendCode);

        Intent getEmail = getIntent();
        email = getEmail.getStringExtra("email");

        Long rdNumOne = Math.round(Math.random() * 9);
        Long rdNumTwo = Math.round(Math.random() * 9);
        Long rdNumThree = Math.round(Math.random() * 9);
        Long rdNumFour = Math.round(Math.random() * 9);

        otp1 = String.valueOf(rdNumOne);
        otp2 = String.valueOf(rdNumTwo);
        otp3 = String.valueOf(rdNumThree);
        otp4 = String.valueOf(rdNumFour);

        sendNotification(otp1, otp2, otp3, otp4);
        Toast.makeText(this, "Hãy kiểm tra OTP ở trong thông báo!", Toast.LENGTH_SHORT).show();

        edtNumOne.addTextChangedListener(new OtpTextWatcher(edtNumOne, edtNumTwo));
        edtNumTwo.addTextChangedListener(new OtpTextWatcher(edtNumTwo, edtNumThree));
        edtNumThree.addTextChangedListener(new OtpTextWatcher(edtNumThree, edtNumFour));
        edtNumFour.addTextChangedListener(new OtpTextWatcher(edtNumFour, null));

        edtNumFour.addTextChangedListener(new OtpPreTextWatcher(edtNumFour, edtNumThree));
        edtNumThree.addTextChangedListener(new OtpPreTextWatcher(edtNumThree, edtNumTwo));
        edtNumTwo.addTextChangedListener(new OtpPreTextWatcher(edtNumTwo, edtNumOne));
        edtNumOne.addTextChangedListener(new OtpPreTextWatcher(edtNumOne, null));


        tv_BackLogin.setOnClickListener(v -> {
            Intent i_backToLogin = new Intent(Activity_Input_Code.this, Activity_DangNhap.class);
            startActivity(i_backToLogin);
            finish();
        });

        tv_SignUp.setOnClickListener(v -> {
            Intent goToSignUp = new Intent(Activity_Input_Code.this, Activity_DangKy.class);
            startActivity(goToSignUp);
            finish();
        });

        tv_ResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_ResendCode.setTextColor(Color.GREEN);

                Long rdNumOne = Math.round(Math.random() * 9);
                Long rdNumTwo = Math.round(Math.random() * 9);
                Long rdNumThree = Math.round(Math.random() * 9);
                Long rdNumFour = Math.round(Math.random() * 9);

                otp1 = String.valueOf(rdNumOne);
                otp2 = String.valueOf(rdNumTwo);
                otp3 = String.valueOf(rdNumThree);
                otp4 = String.valueOf(rdNumFour);

                sendNotification(otp1, otp2, otp3, otp4);
            }
        });

        btn_Continue.setOnClickListener(v -> {

            String inPutOtp1 = edtNumOne.getText().toString();
            String inPutOtp2 = edtNumTwo.getText().toString();
            String inPutOtp3 = edtNumThree.getText().toString();
            String inPutOtp4 = edtNumFour.getText().toString();

            Boolean check1 = inPutOtp1.equals(otp1);
            Boolean check2 = inPutOtp2.equals(otp2);
            Boolean check3 = inPutOtp3.equals(otp3);
            Boolean check4 = inPutOtp4.equals(otp4);

            if (inPutOtp1.isEmpty()) {
                edtNumOne.setError("Trống");
                edtNumOne.requestFocus();
            } else if (inPutOtp2.isEmpty()) {
                edtNumTwo.setError("Trống");
                edtNumTwo.requestFocus();
            } else if (inPutOtp3.isEmpty()) {
                edtNumThree.setError("Trống");
                edtNumThree.requestFocus();
            } else if (inPutOtp4.isEmpty()) {
                edtNumFour.setError("Trống");
                edtNumFour.requestFocus();
            } else {

                if (check1 && check2 && check3 && check4) {
                    Intent i_gotoCreateNewPass = new Intent(Activity_Input_Code.this, Activity_NewPass.class);
                    i_gotoCreateNewPass.putExtra("email", email);
                    startActivity(i_gotoCreateNewPass);
                    finish();
                } else {
                    Toast.makeText(this, "Mã OPT không đúng", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });




    }



    public void sendNotification(String otp1, String otp2, String otp3, String otp4) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ConfigNotification.CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Mã OPT CỦA BẠN")
                .setContentText("Mã OPT của bạn là: " + otp1 + otp2 + otp3 + otp4 +
                        " .Vui lòng không chia sẻ mã này cho người khác")
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            managerCompat.notify((int) new Date().getTime(), builder.build());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 7979);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7979) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendNotification(otp1, otp2, otp3, otp4);
                }
            }
        }
    }


    private class OtpTextWatcher implements TextWatcher {

        private EditText currentEditText;
        private EditText nextEditText;

        public OtpTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String otp = editable.toString().trim();

            if (otp.length() == 1) {
                // Nếu đã nhập một số, di chuyển tới ô EditText tiếp theo (nếu có)
                if (nextEditText != null) {
                    nextEditText.requestFocus();
                }
            }
        }
    }

    private class OtpPreTextWatcher implements TextWatcher {

        private EditText currentEditText;
        private EditText previousEditText;

        public OtpPreTextWatcher(EditText currentEditText, EditText previousEditText) {
            this.currentEditText = currentEditText;
            this.previousEditText = previousEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String otp = editable.toString().trim();

            if (otp.length() == 0) {
                // Nếu đã nhập một số, di chuyển tới ô EditText tiếp theo (nếu có)
                if (previousEditText != null) {
                    previousEditText.requestFocus();
                }
            }
        }
    }

}