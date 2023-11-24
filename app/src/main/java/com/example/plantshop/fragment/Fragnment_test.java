package com.example.plantshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantshop.R;
import com.example.plantshop.activity.Activity_DangNhap;
import com.example.plantshop.activity.Activity_ForgotPass;
import com.example.plantshop.activity.Activity_Input_Code;
import com.example.plantshop.activity.Activity_NewPass;
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.model.Account;

import java.util.ArrayList;

public class Fragnment_test extends Fragment {
    EditText edt_passPre, edt_change, edt_change_pass;
    ImageView img_Back;
    private Button btn_Login;
    private DAO dao;
    private Account account;
    private ArrayList<Account> listAccount;

    public static int getID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragnment_change, container, false);

        edt_passPre = view.findViewById(R.id.edt_passPre);
        edt_change= view.findViewById(R.id.edt_change);
        edt_change_pass= view.findViewById(R.id.edt_change_pass);
        btn_Login = view.findViewById(R.id.btn_Login);
        img_Back = view.findViewById(R.id.img_Back);
        Intent getEmail = getActivity().getIntent();
        String email = getEmail.getStringExtra("email");

         account = new Account();
         dao = new DAO();
         listAccount = new ArrayList<>();
         listAccount = dao.getListAccount();
         if(listAccount != null && !listAccount.isEmpty()){
             for (int i = 0; i < listAccount.size(); i++) {
                 if (email.equals(listAccount.get(i).getUserName())) {
                     getID = listAccount.get(i).getIdAcount();
                     break;
                 }
             }
         }

        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();
        });

        btn_Login.setOnClickListener(v -> {
            String passPre = edt_passPre.getText().toString();
            String newPass = edt_change.getText().toString();
            String confirmNewPass = edt_change_pass.getText().toString();

            if(dao.checkpass(passPre)){
                if(passPre.isEmpty()){
                    edt_passPre.setError("Vui lòng nhập mật khẩu hiện tại");
                    edt_passPre.requestFocus();
                }
                else if (newPass.isEmpty()) {
                    edt_change.setError("Vui lòng nhập mật khẩu mới");
                    edt_change.requestFocus();
                } else if (confirmNewPass.isEmpty()) {
                    edt_change_pass.setError("Vui lòng nhập lại mật khẩu mới");
                    edt_change_pass.requestFocus();
                } else if (!newPass.equals(confirmNewPass)) {
                    edt_change_pass.setError("Mật khẩu không trùng khớp nhau");
                    edt_change_pass.requestFocus();
                } else {
//                    Toast.makeText(getActivity(), "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();


                for (Account ac : listAccount
                ) {
                    if(email.equals(ac.getUserName())){
                        account = ac;
                        String checkemail = account.getUserName();
                        break;
                    }
                }
                account.setPassWord(edt_change.getText().toString());
                dao.updateAccount(account);

                if(dao.updateAccount(account)){
                    Toast.makeText(getActivity(), "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();

                }else {
                    Toast.makeText(getActivity(), "Lỗi! kiểm tra mạng của bạn", Toast.LENGTH_SHORT).show();
                }
                }

            }else {
                edt_passPre.setError("Mật khẩu hiện tại khong đúng");
                edt_passPre.requestFocus();
            }


        });
        return view;
    }
}
