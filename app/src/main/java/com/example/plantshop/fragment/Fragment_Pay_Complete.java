package com.example.plantshop.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.plantshop.model.Guest;
import com.example.plantshop.model.Notification;
import com.example.plantshop.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Fragment_Pay_Complete extends Fragment {

    private ImageView img_Back;
    private TextView tv_get_FullName, tv_get_Email, tv_get_Address, tv_get_PhoneNumber, tv_GHN_ExpectedDate,
            tv_COD_ExpectedDate, tv_PaymentBy, tv_Cancel_Order, tv_SumPrice, tv_BackHome;
    private LinearLayout layout_GHNShip, layout_CODShip;
    private RecyclerView rc_NT_Product;
    private Button btn_GoHandBook;
    private ArrayList<Product> listPayment;
    private Guest guest;
    private boolean check_COD, check_GHN, check_Pay;
    private double sumPrice;
    private DatabaseReference databaseRef_NT, databaseRef_CartOfNT;
    private int id = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_complete, container, false);

        img_Back = view.findViewById(R.id.img_Back);

        tv_get_FullName = view.findViewById(R.id.tv_get_FullName);
        tv_get_Email = view.findViewById(R.id.tv_get_Email);
        tv_get_Address = view.findViewById(R.id.tv_get_Address);
        tv_get_PhoneNumber = view.findViewById(R.id.tv_get_PhoneNumber);
        tv_GHN_ExpectedDate = view.findViewById(R.id.tv_GHN_ExpectedDate);
        tv_COD_ExpectedDate = view.findViewById(R.id.tv_COD_ExpectedDate);
        tv_PaymentBy = view.findViewById(R.id.tv_PaymentBy);
        tv_Cancel_Order = view.findViewById(R.id.tv_Cancel_Order);
        tv_SumPrice = view.findViewById(R.id.tv_SumPrice);
        tv_BackHome = view.findViewById(R.id.tv_BackHome);

        layout_GHNShip = view.findViewById(R.id.layout_GHNShip);
        layout_CODShip = view.findViewById(R.id.layout_CODShip);
        rc_NT_Product = view.findViewById(R.id.rc_NT_Product);

        btn_GoHandBook = view.findViewById(R.id.btn_GoHandBook);

        tv_Cancel_Order.setPaintFlags(tv_Cancel_Order.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_BackHome.setPaintFlags(tv_BackHome.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
        check_Pay = getData.getBoolean("check_Pay");
        sumPrice = getData.getDouble("sumPrice");

        if (check_COD) {
            layout_CODShip.setVisibility(View.VISIBLE);
            layout_GHNShip.setVisibility(View.GONE);
        } else if (check_GHN) {
            layout_GHNShip.setVisibility(View.VISIBLE);
            layout_CODShip.setVisibility(View.GONE);
        }

        if (check_Pay) {
            tv_PaymentBy.setText("Thanh toán bằng thẻ tín dụng");
        } else {
            tv_PaymentBy.setText("Thanh toán khi nhận hàng");
        }

        tv_SumPrice.setText(String.format("%.3f", (sumPrice / 1000)));


        listPayment = Fragment_Cart.listPD_InCart;

        if (listPayment == null) {
            listPayment = MainActivity.listPurchased;
        }


        rc_NT_Product.setHasFixedSize(true);
        rc_NT_Product.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rc_NT_Product.setAdapter(new PaymentAdapter(getContext(), listPayment));

        tv_BackHome.setOnClickListener(v -> {
            MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Home);
        });

        img_Back.setOnClickListener(v -> {
            MainActivity.bottom_Navigation.setSelectedItemId(R.id.bt_Home);
        });

        if (Fragment_Cart.listPD_InCart != null) {

            databaseRef_NT = FirebaseDatabase.getInstance().getReference("Notification").child(String.valueOf(MainActivity.getID));
            databaseRef_CartOfNT = FirebaseDatabase.getInstance().getReference("Purchased Product").child(String.valueOf(MainActivity.getID));

            databaseRef_NT.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<Notification> listNT = new ArrayList<>(); // Lấy danh sách thông báo về và thiết lập id tự động cho thông báo
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()
                    ) {
                        Notification nt = dataSnapshot.getValue(Notification.class);
                        if (nt != null) {
                            listNT.add(nt);
                        }
                    }


                    if (listNT.size() > 0) {
                        id = listNT.get(listNT.size() - 1).getIdNT() + 1;
                    }

                    Date currentDate = new Date(); // lấy ngày tháng hiện tại cho thông báo
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);

                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    String dayOfWeekString = getDayOfWeekString(dayOfWeek);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);
                    String date = dayOfWeekString + ", " + day + "/" + month + "/" + year;

                    Notification nt = new Notification();
                    nt.setIdNT(id);
                    nt.setIdGuest(MainActivity.getID);
                    nt.setCheck_COD(check_COD);
                    nt.setCheck_GHN(check_GHN);
                    nt.setCheck_Pay(check_Pay);
                    nt.setSumPrice(sumPrice);
                    nt.setDateOder(date);
                    nt.setStatus(true);


                    databaseRef_NT.child(String.valueOf(id)).setValue(nt); // up thông tin thông báo

                    for (Product product : listPayment // thêm các sản phẩm vào thông báo
                    ) {
                        databaseRef_CartOfNT.child(String.valueOf(id)).child(product.getLoaiSanPham()).child(String.valueOf(product.getIdSanPham())).setValue(product);
                    }

                    FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(MainActivity.getID)).removeValue(); // xoá sản phẩm trong giỏ hàng


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        return view;
    }

    private String getDayOfWeekString(int dayOfWeek) {
        String[] daysOfWeek = new String[]{"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};
        return daysOfWeek[dayOfWeek - 1];
    }
}
