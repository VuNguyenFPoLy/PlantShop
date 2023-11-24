package com.example.plantshop.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantshop.R;
import com.example.plantshop.activity.Activity_DangNhap;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO;
import com.example.plantshop.model.Guest;
import com.squareup.picasso.Picasso;

public class Fragment_Profile extends Fragment {

    private ImageView img_Avatar;
    private TextView tv_FullName, tv_EmailUser, tv_EditInformation, tv_HandbookPlant, tv_History, tv_Help, tv_ChangePass, tv_LogOut;
    private final int PICK_IMAGE_REQUEST = 2;
    private Uri uri;
    private Fragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        img_Avatar = view.findViewById(R.id.img_Avatar);
        tv_FullName = view.findViewById(R.id.tv_FullName);
        tv_EmailUser = view.findViewById(R.id.tv_EmailUser);
        tv_EditInformation = view.findViewById(R.id.tv_EditInformation);
        tv_HandbookPlant = view.findViewById(R.id.tv_HandbookPlant);
        tv_History = view.findViewById(R.id.tv_History);
        tv_Help = view.findViewById(R.id.tv_Help);
        tv_ChangePass = view.findViewById(R.id.tv_ChangePass);
        tv_LogOut = view.findViewById(R.id.tv_LogOut);

        if (MainActivity.guest != null) {
            Guest guest = MainActivity.guest;

            if (guest.getFullName() != null) {
                tv_FullName.setText(guest.getFullName());
            } else {
                tv_FullName.setText("Guest");
            }
            tv_EmailUser.setText(guest.getEmail());
            if (guest.getUrl_img() != null) {
                Picasso.get().load(guest.getUrl_img()).into(img_Avatar);
            }
        }

        DAO dao = new DAO();

        img_Avatar.setOnClickListener(v -> { // cập nhật ảnh đại diện
            Intent getIMG = new Intent();
            getIMG.setType("image/*");
            getIMG.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(getIMG, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        tv_EditInformation.setOnClickListener(v -> { // chuyển tới frg cập nhật thông tin

            fragment = new Fragment_EditInformation();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();

        });

        tv_HandbookPlant.setOnClickListener(v -> {
            fragment = new Fragment_HandBook();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });

        tv_ChangePass.setOnClickListener(v -> {
            fragment = new Fragment_ChangePassWord();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, fragment).commit();
        });


        tv_LogOut.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Activity_DangNhap.class);
            startActivity(intent);
        });
        return view;
    }

    public void setImg_Avatar(Uri uri) {
        DAO.setIMG(uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            img_Avatar.setImageURI(uri);
            setImg_Avatar(uri);
        }
    }
}
