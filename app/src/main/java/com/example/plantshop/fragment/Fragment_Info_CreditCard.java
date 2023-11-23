package com.example.plantshop.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.adapter.PaymentAdapter;
import com.example.plantshop.model.CreditCard;
import com.example.plantshop.model.Guest;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class Fragment_Info_CreditCard extends Fragment {

    private ImageView img_Back, img_GHN, img_COD;
    private EditText edt_IdCrediCard, edt_NameInCard, edt_ReleaseDate, edt_ProtectID;
    private TextView tv_get_FullName, tv_get_Email, tv_get_Address, tv_get_PhoneNumber, tv_GHN, tv_CODShip,
            tv_tamtinh, tv_FeeShip, tv_SumPrice;
    private Button btn_Continue;
    private LinearLayout layout_CODShip, layout_GHNShip;
    private Guest guest;
    private boolean check_COD, check_GHN;
    private double getPrice;
    private RecyclerView rc_Pay_Product;
    private ArrayList<Product> listPayment = Fragment_Cart.listPD_InCart;
    private PaymentAdapter paymentAdapter;
    private boolean check_Pay = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_creditcard, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        img_GHN = view.findViewById(R.id.img_GHN);
        img_COD = view.findViewById(R.id.img_COD);

        edt_IdCrediCard = view.findViewById(R.id.edt_IdCrediCard);
        edt_NameInCard = view.findViewById(R.id.edt_NameInCard);
        edt_ReleaseDate = view.findViewById(R.id.edt_ReleaseDate);
        edt_ProtectID = view.findViewById(R.id.edt_ProtectID);

        tv_get_FullName = view.findViewById(R.id.tv_get_FullName);
        tv_get_Email = view.findViewById(R.id.tv_get_Email);
        tv_get_Address = view.findViewById(R.id.tv_get_Address);
        tv_get_PhoneNumber = view.findViewById(R.id.tv_get_PhoneNumber);

        tv_GHN = view.findViewById(R.id.ly_GHN);
        tv_CODShip = view.findViewById(R.id.ly_CODShip);
        tv_tamtinh = view.findViewById(R.id.tv_tamtinh);
        tv_FeeShip = view.findViewById(R.id.tv_FeeShip);
        tv_SumPrice = view.findViewById(R.id.tv_SumPrice);

        rc_Pay_Product = view.findViewById(R.id.rc_Pay_Product);

        layout_CODShip = view.findViewById(R.id.layout_CODShip);
        layout_GHNShip = view.findViewById(R.id.layout_GHNShip);

        btn_Continue = view.findViewById(R.id.btn_Continue);

        DatabaseReference databaseRef_CreditCard = FirebaseDatabase.getInstance().getReference("CreditCard").child(String.valueOf(MainActivity.getID));

        databaseRef_CreditCard.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CreditCard creditCard = snapshot.getValue(CreditCard.class);
                if (creditCard != null) {
                    edt_IdCrediCard.setText(creditCard.getCardNumber());
                    edt_NameInCard.setText(creditCard.getCardName());
                    edt_ReleaseDate.setText(creditCard.getReleaseDate());
                    edt_ProtectID.setText(creditCard.getProtectCard());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (MainActivity.guest != null) { // Lấy thông tin người dùng
            guest = MainActivity.guest;

            if (guest.getFullName() != null) {
                tv_get_FullName.setText("Họ và tên: " + guest.getFullName());
            }

            if (guest.getEmail() != null) {
                tv_get_Email.setText("Email: " + guest.getEmail());
            }

            if (guest.getAddress() != null) {
                tv_get_Address.setText("Địa chỉ: " + guest.getAddress());
            }

            if (guest.getPhoneNumber() != null) {
                tv_get_PhoneNumber.setText("Số điện thoại: " + guest.getPhoneNumber());
            }
        }

        Bundle getData = getArguments();
        check_COD = getData.getBoolean("check_COD");
        check_GHN = getData.getBoolean("check_GHN");
        getPrice = getData.getDouble("tamTinh");

        tv_SumPrice.setText(getData.getString("sum"));
        tv_tamtinh.setText(String.format("%.3f", getPrice));

        if (check_COD) {
            layout_CODShip.setVisibility(View.VISIBLE);
            layout_GHNShip.setVisibility(View.GONE);

            tv_FeeShip.setText("20.000");
        } else {
            layout_GHNShip.setVisibility(View.VISIBLE);
            layout_CODShip.setVisibility(View.GONE);

            tv_FeeShip.setText("30.000");
        }

        img_Back.setOnClickListener(v -> {

            Fragment fragment = new Fragment_Choose_Pay();
            Bundle bundle = new Bundle();
            bundle.putDouble("price", getPrice);
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });

        paymentAdapter = new PaymentAdapter(getContext(), listPayment);

        rc_Pay_Product.setHasFixedSize(true);
        rc_Pay_Product.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rc_Pay_Product.setAdapter(paymentAdapter);

        edt_IdCrediCard.addTextChangedListener(new TextWatcher() {
            private boolean selfChange = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // No action needed when text is changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the change was made by the TextWatcher itself
                if (selfChange) {
                    selfChange = false;
                    return;
                }

                // Remove any previous formatting
                String originalString = editable.toString().replaceAll("\\s", "");

                // Check if the length is greater than 0
                if (originalString.length() > 0) {
                    StringBuilder formattedString = new StringBuilder();

                    // Format the string by adding space after every 4 characters
                    for (int i = 0; i < originalString.length(); i++) {
                        formattedString.append(originalString.charAt(i));
                        if ((i + 1) % 4 == 0 && (i + 1) < originalString.length()) {
                            formattedString.append(" ");
                        }
                    }

                    // Set the formatted string back to the EditText
                    selfChange = true;
                    edt_IdCrediCard.setText(formattedString.toString());
                    edt_IdCrediCard.setSelection(formattedString.length());
                }
            }
        });

        btn_Continue.setOnClickListener(v -> {

            String idCard = edt_IdCrediCard.getText().toString();
            String nameCard = edt_NameInCard.getText().toString();
            String releaseDate = edt_ReleaseDate.getText().toString();
            String protectID = edt_ProtectID.getText().toString();

            if (idCard.isEmpty()) {
                edt_IdCrediCard.setError("Trống");
                edt_IdCrediCard.requestFocus();
            } else if (idCard.length() < 19) {
                edt_IdCrediCard.setError("Mã số thẻ không đúng");
                edt_IdCrediCard.requestFocus();
            } else if (nameCard.isEmpty()) {
                edt_NameInCard.setError("Trống");
                edt_NameInCard.requestFocus();
            } else if (releaseDate.isEmpty()) {
                edt_ReleaseDate.setError("Trống");
                edt_ReleaseDate.requestFocus();
            } else if (protectID.isEmpty()) {
                edt_ProtectID.setError("Trống");
                edt_ProtectID.requestFocus();
            } else {


                Dialog bottomDialog = new Dialog(getContext());  // sheet bottom dialog
                bottomDialog.setContentView(R.layout.layout_sheet_bottom);

                Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
                TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
                TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);
                TextView tv_Message = bottomDialog.findViewById(R.id.tv_Message);
                tv_Message.setVisibility(View.GONE);

                tv_Title.setText("Xác nhận thanh toán?");

                btn_Accept.setOnClickListener(v1 -> {

                    CreditCard creditCard = new CreditCard();
                    creditCard.setIdCard(MainActivity.getID);
                    creditCard.setCardNumber(idCard);
                    creditCard.setCardName(nameCard);
                    creditCard.setReleaseDate(releaseDate);
                    creditCard.setProtectCard(protectID);

                    databaseRef_CreditCard.setValue(creditCard);
                    Fragment fragment = new Fragment_Pay_Complete();

                    Bundle bundle = new Bundle();

                    double sumPrice = Double.valueOf(tv_SumPrice.getText().toString());
                    bundle.putBoolean("check_COD", check_COD);
                    bundle.putBoolean("check_GHN", check_GHN);
                    bundle.putBoolean("check_Pay", check_Pay); //true là thẻ tín dụng, false là tiền mặt
                    bundle.putDouble("sumPrice", sumPrice);

                    fragment.setArguments(bundle);
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
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);


            }

        });

        return view;
    }
}
