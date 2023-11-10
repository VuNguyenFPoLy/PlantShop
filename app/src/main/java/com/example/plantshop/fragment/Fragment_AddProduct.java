package com.example.plantshop.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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

public class Fragment_AddProduct extends Fragment {

    private ImageView img_Back, img_addProduct;
    private EditText edt_NameProduct, edt_Price, edt_Size, edt_Brand, edt_Quantity, edt_Describe;
    private TextView lb_Notify, tv_TypeProduct, tv_TypeOfProduct;
    private Button btn_Cancel, btn_Submit;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_add_product, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        img_addProduct = view.findViewById(R.id.img_addProduct);
        edt_NameProduct = view.findViewById(R.id.edt_NameProduct);
        edt_Price = view.findViewById(R.id.edt_Price);
        edt_Size = view.findViewById(R.id.edt_Size);
        edt_Brand = view.findViewById(R.id.edt_Brand);
        edt_Quantity = view.findViewById(R.id.edt_Quantity);
        edt_Describe = view.findViewById(R.id.edt_Describe);
        lb_Notify = view.findViewById(R.id.lb_Notify);
        btn_Cancel = view.findViewById(R.id.btn_Cancel);
        btn_Submit = view.findViewById(R.id.btn_Submit);
        tv_TypeProduct = view.findViewById(R.id.tv_TypeProduct);
        tv_TypeOfProduct = view.findViewById(R.id.tv_TypeOfProduct);


        // trở về
        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Product()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        // trở về
        btn_Cancel.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Product()).commit();
            fragmentTransaction.addToBackStack(null);
        });

        // chọn kích cỡ
        edt_Size.setOnClickListener(v -> {
            String[] size = {"Nhỏ", "Trung Bình", "Lớn"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Chọn kích cỡ");
            builder.setItems(size, (dialog, which) -> { // Thiết lập Item lên thông báo
                edt_Size.setText(size[which]);
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        tv_TypeProduct.setOnClickListener(v -> {
            String[] type = {"Cây trồng", "Chậu cây", "Dụng cụ"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Chọn loại sản phẩm");
            builder.setItems(type, (dialog, which) -> { // Thiết lập Item lên thông báo
                tv_TypeProduct.setText(type[which]);
                tv_TypeOfProduct.setText("Chọn thể loại sản phẩm");
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        tv_TypeOfProduct.setOnClickListener(v -> {
            String[] typeOfPlant = {"Ưa bóng", "Ưa mát", "Ưa sáng", "Ưa tối"};
            String[] typeOfPots = {"Gốm", "Xứ", "Xi măng", "Nhựa", "Đất nung"};
            String[] typeOfTool = {"Găng tay", "Cuốc", "Xẻng", "Cào đất", "Bay", "Cưa", "Đinh ba"};

            String typeProduct = tv_TypeProduct.getText().toString();
            if(typeProduct.equals("Chọn loại sản phẩm")){
                Toast.makeText(getContext(), "Vui lòng chọn loại sản phẩm trước", Toast.LENGTH_SHORT).show();
            }else {

                String[] typeOf;
                if(typeProduct.equals("Cây trồng")){
                    typeOf = typeOfPlant;
                } else if (typeProduct.equals("Chậu cây")) {
                    typeOf = typeOfPots;
                }else {
                    typeOf = typeOfTool;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn loại sản phẩm");
                builder.setItems(typeOf, (dialog, which) -> { // Thiết lập Item lên thông báo
                    tv_TypeOfProduct.setText(typeOf[which]);
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });




        img_addProduct.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        });

        btn_Submit.setOnClickListener(v -> {

            String name = edt_NameProduct.getText().toString();
            String price = edt_Price.getText().toString();
            String size = edt_Size.getText().toString();
            String brand = edt_Brand.getText().toString();
            String quantity = edt_Quantity.getText().toString();
            String describe = edt_Describe.getText().toString();

        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            img_addProduct.setImageURI(uri);
        }
    }
}
