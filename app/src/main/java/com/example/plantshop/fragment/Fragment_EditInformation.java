package com.example.plantshop.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Guest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_EditInformation extends Fragment {

    private ImageView img_Back_Profile;
    private EditText edt_editName, edt_EditEmail, edt_EditAddress, edt_EditPhone;
    private Button btn_Submit;
    private DatabaseReference databaseRef_Guest;
    private Guest guest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editinformation, container, false);

        img_Back_Profile = view.findViewById(R.id.img_Back_Profile);
        edt_editName = view.findViewById(R.id.edt_editName);
        edt_EditEmail = view.findViewById(R.id.edt_EditEmail);
        edt_EditAddress = view.findViewById(R.id.edt_EditAddress);
        edt_EditPhone = view.findViewById(R.id.etd_EditPhone);
        btn_Submit = view.findViewById(R.id.btn_Submit);

        btn_Submit.setEnabled(false);

        if (MainActivity.guest != null) { // Lấy thông tin người dùng
            guest = MainActivity.guest;

            if(guest.getFullName() != null){
                edt_editName.setText(guest.getFullName());
            }

            if(guest.getEmail() != null){
                edt_EditEmail.setText(guest.getEmail());
            }

            if(guest.getAddress() != null){
                edt_EditAddress.setText(guest.getAddress());
            }

            if(guest.getPhoneNumber() != null){
                edt_EditPhone.setText(guest.getPhoneNumber());
            }
        }

        if (MainActivity.getID > 0) {
            databaseRef_Guest = FirebaseDatabase.getInstance().getReference("Guest").child(String.valueOf(MainActivity.getID));
        }else {
            edt_EditEmail.setClickable(false);
            edt_EditEmail.setFocusable(false);
            edt_EditAddress.setClickable(false);
            edt_EditAddress.setFocusable(false);
            edt_editName.setClickable(false);
            edt_editName.setFocusable(false);
            edt_EditPhone.setClickable(false);
            edt_EditPhone.setFocusable(false);
        }

        img_Back_Profile.setOnClickListener(v -> {
            MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Profile);
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String name = edt_editName.getText().toString();
                String email = edt_EditEmail.getText().toString();
                String address = edt_EditAddress.getText().toString();
                String phone = edt_EditPhone.getText().toString();

                boolean check = !name.isEmpty() && !email.isEmpty() && !address.isEmpty() && !phone.isEmpty();
                if (check) {
                    btn_Submit.setEnabled(check);
                    btn_Submit.setBackgroundColor(getResources().getColor(R.color.green));
                }
            }
        };

        edt_editName.addTextChangedListener(textWatcher);
        edt_EditEmail.addTextChangedListener(textWatcher);
        edt_EditAddress.addTextChangedListener(textWatcher);
        edt_EditPhone.addTextChangedListener(textWatcher);

        btn_Submit.setOnClickListener(v -> {

            String name = edt_editName.getText().toString();
            String email = edt_EditEmail.getText().toString();
            String address = edt_EditAddress.getText().toString();
            String phone = edt_EditPhone.getText().toString();

            if (name.isEmpty()) {
                edt_editName.setError("Trống");
                edt_editName.requestFocus();
            } else if (email.isEmpty()) {
                edt_EditEmail.setError("Trống");
                edt_EditEmail.requestFocus();
            } else if (address.isEmpty()) {
                edt_EditAddress.setError("Trống");
                edt_EditAddress.requestFocus();
            } else if (phone.isEmpty()) {
                edt_EditPhone.setError("Trống");
                edt_EditPhone.requestFocus();
            } else if (phone.length() < 9 || phone.length() > 10) {
                edt_EditPhone.setError("Số điện thoại không đúng");
                edt_EditPhone.requestFocus();
            } else {

                guest.setEmail(email);
                guest.setFullName(name);
                guest.setAddress(address);
                guest.setPhoneNumber(phone);

                databaseRef_Guest.setValue(guest).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
