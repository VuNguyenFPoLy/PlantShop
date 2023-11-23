package com.example.plantshop.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.firebase.DAO_Product;
import com.example.plantshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_AddProduct extends Fragment {

    private ImageView img_Back, img_addProduct;
    private EditText edt_NameProduct, edt_Price, edt_Size, edt_Brand, edt_Quantity, edt_Describe;
    private TextView lb_Notify, tv_TypeProduct, tv_TypeOfProduct;
    private Button btn_Cancel, btn_Submit;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;
    private DAO_Product dao_product;
    private Fragment fragment;
    private ArrayList<Product> listProduct;
    private String getURl, checkEditPD, getFrom, key;

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

        listProduct = Fragment_Product.listProduct;

        dao_product = new DAO_Product();
        Bundle bundle = new Bundle();

        Bundle getData = getArguments();
        key = getData.getString("key");

        if (getData != null) {
            checkEditPD = getData.getString("edit");
        }


        if (getData.getString("from") != null) {
            getFrom = getData.getString("from");
        }


        if (getFrom.equals("home") || getFrom.equals("search")) {
            listProduct.clear();
            if (Fragment_Product.key.equals("Xem thêm cây trồng")) {
                listProduct = Fragment_Home.listPlant;
            } else if (Fragment_Product.key.equals("Xem thêm chậu cây")) {

                listProduct = Fragment_Home.listPots;
            } else {
                listProduct = Fragment_Home.listTools;
            }
        }

        if (checkEditPD != null) {

            for (Product pd : listProduct
            ) {
                if (Fragment_Edit_Or_Delete.id == pd.getIdSanPham()) {

                    Picasso.get().load(pd.getUrl_Img()).into(img_addProduct);
                    edt_NameProduct.setText(pd.getTenSanPham());
                    tv_TypeProduct.setText(pd.getLoaiSanPham());
                    tv_TypeOfProduct.setText(pd.getTheLoaiSanPham());
                    edt_Price.setText(String.valueOf(pd.getGiaTien()));
                    edt_Size.setText(pd.getKichCo());
                    edt_Brand.setText(pd.getXuatXu());
                    edt_Quantity.setText(String.valueOf(pd.getSoLuong()));
                    edt_Describe.setText(pd.getMoTa());
                    lb_Notify.setText("Nhấn để đổi ảnh");
                    getURl = pd.getUrl_Img();
                }
            }
        }


        // trở về
        img_Back.setOnClickListener(v -> {
            fragment = new Fragment_Product();
            bundle.putString("key", getData.getString("key"));
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();

        });

        // trở về
        btn_Cancel.setOnClickListener(v -> {
            fragment = new Fragment_Product();
            bundle.putString("key", getData.getString("key"));
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
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

        if (Fragment_Product.key.equals("Xem thêm cây trồng")) {
            tv_TypeProduct.setText("Cây trồng");
        } else if (Fragment_Product.key.equals("Xem thêm chậu cây")) {
            tv_TypeProduct.setText("Chậu cây");
        } else {
            tv_TypeProduct.setText("Dụng cụ");
        }

        tv_TypeOfProduct.setOnClickListener(v -> {
            String[] typeOfPlant = {"Ưa bóng", "Ưa mát", "Ưa sáng", "Ưa tối"};
            String[] typeOfPots = {"Gốm", "Xứ", "Xi măng", "Nhựa", "Đất nung"};
            String[] typeOfTool = {"Găng tay", "Cuốc", "Xẻng", "Cào đất", "Bay", "Cưa", "Đinh ba", "Bình tưới"};

            String typeProduct = tv_TypeProduct.getText().toString();
            if (typeProduct.equals("Chọn loại sản phẩm")) {
                Toast.makeText(getContext(), "Vui lòng chọn loại sản phẩm trước", Toast.LENGTH_SHORT).show();
            } else {

                String[] typeOf;
                if (typeProduct.equals("Cây trồng")) {
                    typeOf = typeOfPlant;
                } else if (typeProduct.equals("Chậu cây")) {
                    typeOf = typeOfPots;
                } else {
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
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            lb_Notify.setVisibility(View.GONE);
        });

        btn_Submit.setOnClickListener(v -> {

            String name = edt_NameProduct.getText().toString();
            String price = edt_Price.getText().toString();
            String typeProduct = tv_TypeProduct.getText().toString();
            String typeOfProduct = tv_TypeOfProduct.getText().toString();
            String size = edt_Size.getText().toString();
            String brand = edt_Brand.getText().toString();
            String quantity = edt_Quantity.getText().toString();
            String describe = edt_Describe.getText().toString();

            if (name.isEmpty()) {
                edt_NameProduct.setError("Trống");
                edt_NameProduct.requestFocus();
            } else if (price.isEmpty()) {
                edt_Price.setError("Trống");
                edt_Price.requestFocus();
            } else if (typeProduct.equals("Chọn loại sản phẩm")) {
                Toast.makeText(getContext(), "Vui lòng chọn loại sản phẩm", Toast.LENGTH_SHORT).show();
            } else if (typeOfProduct.equals("Chọn thể loại sản phẩm")) {
                Toast.makeText(getContext(), "Vui lòng chọn thể loại sản phẩm", Toast.LENGTH_SHORT).show();
            } else if (size.isEmpty()) {
                edt_Size.setError("Trống");
                edt_Size.requestFocus();
            } else if (brand.isEmpty()) {
                edt_Brand.setError("Trống");
                edt_Brand.requestFocus();
            } else if (quantity.isEmpty()) {
                edt_Quantity.setError("Trống");
                edt_Quantity.requestFocus();
            } else if (describe.isEmpty()) {
                edt_Describe.setError("Trống");
                edt_Describe.requestFocus();
            } else {

                Product product = new Product();
                if (checkEditPD != null) {
                    product.setIdSanPham(Fragment_Edit_Or_Delete.id);
                } else {
                    product.setIdSanPham(-1);
                }

                product.setTenSanPham(name);
                product.setLoaiSanPham(typeProduct);
                product.setTheLoaiSanPham(typeOfProduct);
                product.setGiaTien(Double.parseDouble(price));
                product.setKichCo(size);
                product.setXuatXu(brand);
                product.setSoLuong(Integer.parseInt(quantity));
                product.setMoTa(describe);

                if (uri != null) {
                    dao_product.pushProduct(product, uri);
                } else {
                    product.setUrl_Img(getURl);
                    dao_product.updateProduct(product);
                }


                if (dao_product.pushProduct(product, uri) || dao_product.updateProduct(product)) {
                    setNullEdt();
                    Toast.makeText(getContext(), "Đã thêm sản phẩm mới", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new Fragment_Product();
                    bundle.putString("key", Fragment_Product.key);
                    fragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fr_Layout, fragment).commit();
                    fragmentTransaction.addToBackStack(null);
                }
            }

        });


        return view;
    }

    public void setNullEdt() {
        edt_NameProduct.setText(null);
        edt_Price.setText(null);
        edt_Size.setText(null);
        edt_Brand.setText(null);
        edt_Quantity.setText(null);
        edt_Describe.setText(null);
        tv_TypeProduct.setText("Chọn loại sản phẩm");
        tv_TypeOfProduct.setText("Chọn thể loại sản phẩm");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            img_addProduct.setImageURI(uri);
        }
    }
}
