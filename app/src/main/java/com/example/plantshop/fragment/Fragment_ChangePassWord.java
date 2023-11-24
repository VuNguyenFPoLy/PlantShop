package com.example.plantshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.model.Account;

import java.util.ArrayList;

public class Fragment_ChangePassWord extends Fragment {
    private EditText edt_passPre, edt_change, edt_change_pass;
    private ImageView img_Back;
    private Button btn_Submit;
    private DAO dao;
    private Account account;
    private ArrayList<Account> listAccount;

    public static int getID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);

        edt_passPre = view.findViewById(R.id.edt_passPre);
        edt_change = view.findViewById(R.id.edt_change);
        edt_change_pass = view.findViewById(R.id.edt_change_pass);
        btn_Submit = view.findViewById(R.id.btn_Submit);
        img_Back = view.findViewById(R.id.img_Back);


        dao = new DAO();
        listAccount = new ArrayList<>();
        listAccount = dao.getListAccount();


        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();
        });

        btn_Submit.setOnClickListener(v -> {

            if (listAccount != null) { // lấy account

                for (int i = 0; i < listAccount.size(); i++) {

                    if(MainActivity.getID == listAccount.get(i).getIdAcount()) {
                        account = listAccount.get(i);
                        break;
                    }

                }

            }

            String passPre = edt_passPre.getText().toString();
            String newPass = edt_change.getText().toString();
            String confirmNewPass = edt_change_pass.getText().toString();

            boolean checkPass = confirmNewPass.equals(newPass);

            if (dao.checkpass(passPre)) {

                if (passPre.isEmpty()) {
                    edt_passPre.setError("Trống");
                    edt_passPre.requestFocus();
                } else if (newPass.isEmpty()) {
                    edt_change.setError("Trống");
                    edt_change.requestFocus();
                } else if (confirmNewPass.isEmpty()) {
                    edt_change_pass.setError("Trống");
                    edt_change_pass.requestFocus();
                } else if (!checkPass) {
                    edt_change_pass.setError("Mật khẩu không trùng khớp nhau");
                    edt_change_pass.requestFocus();
                } else {

                    account.setPassWord(newPass);

                    dao.updateAccount(account);

                    if (dao.updateAccount(account)) {
                        Toast.makeText(getActivity(), "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                        MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Profile);

                    } else {
                        Toast.makeText(getActivity(), "Lỗi! kiểm tra mạng của bạn", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {
                edt_passPre.setError("Mật khẩu hiện tại không đúng");
                edt_passPre.requestFocus();
            }


        });
        return view;
    }
}
