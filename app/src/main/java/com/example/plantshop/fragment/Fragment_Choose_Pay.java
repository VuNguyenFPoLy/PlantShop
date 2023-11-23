package com.example.plantshop.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.model.Guest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fragment_Choose_Pay extends Fragment {

    private EditText edt_FullName, edt_Email, edt_Address, edt_PhoneNumber;
    private TextView tv_tamtinh, tv_FeeShip, tv_SumPrice;
    private ImageView img_GHN, img_COD, img_PayCash, img_PayCreditCard, img_Back;
    private Button btn_Continue;
    private LinearLayout ly_PayCash, ly_PayCreditCard, ly_GHN, ly_CODShip;
    private double getPrice, sumPrice;
    private Guest guest;
    private DatabaseReference databaseRef_Guest;
    private boolean check_GHN, check_COD, check_PayCash, check_Pay_CreditCard, check_Pay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_pay, container, false);

        edt_FullName = view.findViewById(R.id.edt_FullName);
        edt_Email = view.findViewById(R.id.edt_Email);
        edt_Address = view.findViewById(R.id.edt_Address);
        edt_PhoneNumber = view.findViewById(R.id.edt_PhoneNumber);

        ly_GHN = view.findViewById(R.id.ly_GHN);
        ly_CODShip = view.findViewById(R.id.ly_CODShip);
        ly_PayCash = view.findViewById(R.id.ly_PayCash);
        ly_PayCreditCard = view.findViewById(R.id.ly_PayCreditCard);

        tv_tamtinh = view.findViewById(R.id.tv_tamtinh);
        tv_FeeShip = view.findViewById(R.id.tv_FeeShip);
        tv_SumPrice = view.findViewById(R.id.tv_SumPrice);

        img_Back = view.findViewById(R.id.img_Back);
        img_GHN = view.findViewById(R.id.img_GHN);
        img_COD = view.findViewById(R.id.img_COD);
        img_PayCash = view.findViewById(R.id.img_PayCash);
        img_PayCreditCard = view.findViewById(R.id.img_PayCreditCard);

        btn_Continue = view.findViewById(R.id.btn_Continue);

        Bundle bundle = getArguments();

        if (bundle != null) {
            getPrice = bundle.getDouble("price");

            tv_tamtinh.setText(String.format("%.3f", getPrice/1000));
            tv_SumPrice.setText(String.format("%.3f", getPrice/1000));
        }

        if (MainActivity.guest != null) { // Lấy thông tin người dùng
            guest = MainActivity.guest;

            if (guest.getFullName() != null) {
                edt_FullName.setText(guest.getFullName());
            }

            if (guest.getEmail() != null) {
                edt_Email.setText(guest.getEmail());
            }

            if (guest.getAddress() != null) {
                edt_Address.setText(guest.getAddress());
            }

            if (guest.getPhoneNumber() != null) {
                edt_PhoneNumber.setText(guest.getPhoneNumber());
            }
        }

        if (MainActivity.getID > 0) {
            databaseRef_Guest = FirebaseDatabase.getInstance().getReference("Guest").child(String.valueOf(MainActivity.getID));
        }

        img_Back.setOnClickListener(v -> {
            Fragment fragment = new Fragment_Cart();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });

        ly_GHN.setOnClickListener(v -> {
            img_GHN.setVisibility(View.VISIBLE);
            img_COD.setVisibility(View.GONE);

            check_GHN = true;
            check_COD = false;

            double fee_GHN = 30000;
            sumPrice = getPrice + fee_GHN;
            tv_FeeShip.setText(String.format("%.3f", fee_GHN));
            tv_SumPrice.setText(String.format("%.3f", sumPrice/1000));
        });

        ly_CODShip.setOnClickListener(v -> {
            img_GHN.setVisibility(View.GONE);
            img_COD.setVisibility(View.VISIBLE);

            check_GHN = false;
            check_COD = true;

            double fee_COD = 200000;
            sumPrice = getPrice + fee_COD;
            tv_FeeShip.setText(String.format("%.3f", fee_COD));
            tv_SumPrice.setText(String.format("%.3f", sumPrice/1000));
        });

        ly_PayCash.setOnClickListener(v -> {
            img_PayCash.setVisibility(View.VISIBLE);
            img_PayCreditCard.setVisibility(View.GONE);

            check_PayCash = true;
            check_Pay_CreditCard = false;
        });

        ly_PayCreditCard.setOnClickListener(v -> {
            img_PayCash.setVisibility(View.GONE);
            img_PayCreditCard.setVisibility(View.VISIBLE);

            check_PayCash = false;
            check_Pay_CreditCard = true;
        });


        btn_Continue.setOnClickListener(v -> { // tiếp tục mua hàng

            String name = edt_FullName.getText().toString();
            String email = edt_Email.getText().toString();
            String address = edt_Address.getText().toString();
            String phone = edt_PhoneNumber.getText().toString();

            if (name.isEmpty()) {
                edt_FullName.setError("Trống");
                edt_FullName.requestFocus();
            } else if (email.isEmpty()) {
                edt_Email.setError("Trống");
                edt_Email.requestFocus();
            } else if (address.isEmpty()) {
                edt_Address.setError("Trống");
                edt_Address.requestFocus();
            } else if (phone.isEmpty()) {
                edt_PhoneNumber.setError("Trống");
                edt_PhoneNumber.requestFocus();
            } else if (phone.length() < 9 || phone.length() > 10) {
                edt_PhoneNumber.setError("Số điện thoại không đúng");
                edt_PhoneNumber.requestFocus();
            } else {

                if (check_COD || check_GHN) {

                    if (check_PayCash || check_Pay_CreditCard) {

                        guest.setEmail(email);
                        guest.setFullName(name);
                        guest.setAddress(address);
                        guest.setPhoneNumber(phone);

                        databaseRef_Guest.setValue(guest);

                        if (check_PayCash) { // thanh toán bằng tiền mặt

                            Dialog bottomDialog = new Dialog(getContext());
                            bottomDialog.setContentView(R.layout.layout_sheet_bottom);

                            Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
                            TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
                            TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);
                            TextView tv_Message = bottomDialog.findViewById(R.id.tv_Message);
                            tv_Message.setVisibility(View.GONE);

                            tv_Title.setText("Xác nhận thanh toán?");

                            btn_Accept.setOnClickListener(v1 -> {

                                Fragment fragment = new Fragment_Pay_Complete();

                                Bundle bundle1 = new Bundle();

                                bundle1.putBoolean("check_COD", check_COD);
                                bundle1.putBoolean("check_GHN", check_GHN);
                                bundle1.putBoolean("check_Pay", check_Pay); //true là thẻ tín dụng, false là tiền mặt
                                bundle1.putDouble("sumPrice", sumPrice);

                                fragment.setArguments(bundle1);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();

                                bottomDialog.dismiss();
                            });

                            tv_Cancel.setOnClickListener(v1 -> {
                                bottomDialog.dismiss();
                            });

                            bottomDialog.show();
                            bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
                            bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ

                        } else {

                            Fragment fragment = new Fragment_Info_CreditCard();

                            Bundle putData = new Bundle();

                            putData.putBoolean("check_COD", check_COD);
                            putData.putBoolean("check_GHN", check_GHN);
                            putData.putDouble("tamTinh", getPrice);
                            putData.putString("sum", tv_SumPrice.getText().toString());
                            fragment.setArguments(putData);

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
                        }

                    } else {
                        Toast.makeText(getContext(), "Chưa chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Chưa chọn phương thức vận chuyển!", Toast.LENGTH_SHORT).show();
                }


            }


        });

        return view;
    }
}
