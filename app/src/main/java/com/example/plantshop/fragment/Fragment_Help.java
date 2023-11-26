package com.example.plantshop.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO_Help;
import com.example.plantshop.model.Help;

import java.util.ArrayList;


public class Fragment_Help extends Fragment {
    private ImageView img_Back;
    private GridLayout grid_help;
    private TextView tv_FloadAddHelp;
    public static ArrayList<Help> listHelp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        grid_help = view.findViewById(R.id.grid_help);
        tv_FloadAddHelp = view.findViewById(R.id.tv_FloadAddHelp);

        if(MainActivity.getID > 0 ){
            tv_FloadAddHelp.setVisibility(View.GONE);
        }


        getListHelp();


        tv_FloadAddHelp.setOnClickListener(v -> {
            DAO_Help daoHelp = new DAO_Help();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View layoutAdd = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_hanbook_help, null);
            builder.setView(layoutAdd);
            AlertDialog dialog = builder.create();
            dialog.show();

            TextView tv_Title = layoutAdd.findViewById(R.id.tv_Title);
            TextView lb_Step = layoutAdd.findViewById(R.id.lb_Step);
            TextView lb_Content = layoutAdd.findViewById(R.id.lb_Content);
            EditText edt_Question = layoutAdd.findViewById(R.id.edt_Question);
            EditText edt_Content = layoutAdd.findViewById(R.id.edt_Content);
            Button btn_Cancel = layoutAdd.findViewById(R.id.btn_Cancel);
            Button btn_Add = layoutAdd.findViewById(R.id.btn_Add);

            tv_Title.setText("Thêm trợ giúp");
            lb_Step.setText("Câu hỏi");
            lb_Content.setText("Trả lời");

            edt_Question.setHint("Nhập câu hỏi");
            edt_Content.setHint("Nhập câu trả lời");


            btn_Add.setOnClickListener(v1 -> {


                String questtion = edt_Question.getText().toString();
                String answer = edt_Content.getText().toString();

                if (questtion.isEmpty()) {
                    edt_Question.setError("Trống");
                    edt_Question.requestFocus();
                } else if (answer.isEmpty()) {
                    edt_Content.setError("Trống");
                    edt_Content.requestFocus();
                } else {

                    Help help = new Help();
                    help.setIdHelp(daoHelp.getIdHelp());
                    help.setTv_Question(questtion);
                    help.setTv_Content(answer);

                    daoHelp.pushHelp(help);
                    if (daoHelp.pushHelp(help)) {

                        getListHelp();
                        edt_Question.setText("");
                        edt_Content.setText("");
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Đã thêm trợ giúp", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                    }
                }

            });

            btn_Cancel.setOnClickListener(v1 -> {
                dialog.dismiss();
            });


        });

        img_Back.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_Layout, new Fragment_Profile()).commit();
        });

        return view;
    }

    public Fragment_Help() {
    }

    public void getListHelp() {

        DAO_Help daoHelp = new DAO_Help();
        listHelp = new ArrayList<>();
        listHelp = daoHelp.getListHelp();

        ArrayList<Boolean> isContentVisibleList = new ArrayList<>();
        ArrayList<View> listViewHelp = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (listHelp.size() > 0) {

                    handler.removeCallbacks(this);

                    grid_help.removeAllViews();
                    grid_help.setRowCount(listHelp.size());

                    for (Help help : listHelp
                    ) {

                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_help, null);

                        TextView tv_Question = view.findViewById(R.id.tv_Question);
                        TextView tv_Content = view.findViewById(R.id.tv_Content);
                        ImageView img_Show = view.findViewById(R.id.img_Show);
                        RelativeLayout ly_Question = view.findViewById(R.id.ly_Question);

                        tv_Question.setText(help.getTv_Question());
                        tv_Content.setText(help.getTv_Content());

                        isContentVisibleList.add(false);

                        if(MainActivity.getID > 0){
                            tv_Content.setVisibility(View.GONE);
                        }
                        ly_Question.setOnClickListener(v -> {

                            if (isContentVisibleList.get(listHelp.indexOf(help))) {
                                img_Show.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                                tv_Content.setVisibility(View.GONE);
                            } else {
                                img_Show.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
                                tv_Content.setVisibility(View.VISIBLE);
                            }

                            // Đảo ngược trạng thái boolean khi ly_Step được click
                            isContentVisibleList.set(listHelp.indexOf(help), !isContentVisibleList.get(listHelp.indexOf(help)));

                        });

                        Dialog bottomDialog = new Dialog(getContext());
                        bottomDialog.setContentView(R.layout.layout_sheet_bottom);

                        Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
                        TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
                        TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);
                        TextView tv_Message = bottomDialog.findViewById(R.id.tv_Message);

                        view.setOnClickListener(v -> {

                            if(MainActivity.getID == 0){

                                tv_Title.setText("Chỉnh sửa");
                                tv_Message.setText("Bạn có muốn chỉnh sửa lại nội dung?");
                                btn_Accept.setText("Chỉnh sửa");

                                btn_Accept.setOnClickListener(v1 -> {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    View layoutAdd = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_hanbook_help, null);
                                    builder.setView(layoutAdd);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                    TextView tv_TitleHelp = layoutAdd.findViewById(R.id.tv_Title);
                                    TextView lb_Step = layoutAdd.findViewById(R.id.lb_Step);
                                    TextView lb_Content = layoutAdd.findViewById(R.id.lb_Content);
                                    EditText edt_Question = layoutAdd.findViewById(R.id.edt_Question);
                                    EditText edt_Content = layoutAdd.findViewById(R.id.edt_Content);
                                    Button btn_Cancel = layoutAdd.findViewById(R.id.btn_Cancel);
                                    Button btn_Add = layoutAdd.findViewById(R.id.btn_Add);

                                    tv_TitleHelp.setText("Sửa trợ giúp");
                                    lb_Step.setText("Câu hỏi");
                                    lb_Content.setText("Trả lời");

                                    edt_Question.setText(help.getTv_Question());
                                    edt_Content.setText(help.getTv_Content());

                                    btn_Add.setOnClickListener(v2 -> {


                                        String questtion = edt_Question.getText().toString();
                                        String answer = edt_Content.getText().toString();

                                        if (questtion.isEmpty()) {
                                            edt_Question.setError("Trống");
                                            edt_Question.requestFocus();
                                        } else if (answer.isEmpty()) {
                                            edt_Content.setError("Trống");
                                            edt_Content.requestFocus();
                                        } else {


                                            help.setTv_Question(questtion);
                                            help.setTv_Content(answer);

                                            daoHelp.pushHelp(help);
                                            if (daoHelp.pushHelp(help)) {

                                                getListHelp();
                                                edt_Question.setText("");
                                                edt_Content.setText("");
                                                dialog.dismiss();
                                                bottomDialog.dismiss();
                                                Toast.makeText(getContext(), "Đã thêm trợ giúp", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });


                                    btn_Cancel.setOnClickListener(v2 -> {

                                        dialog.dismiss();
                                        bottomDialog.dismiss();
                                    });

                                });

                                tv_Cancel.setOnClickListener(v1 -> { // huỷ xoá
                                    bottomDialog.dismiss();
                                });

                                bottomDialog.show();
                                bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
                                bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                            }

                        });

                        view.setOnLongClickListener(v -> {

                            if(MainActivity.getID == 0){

                                tv_Title.setText("Xoá trợ giúp?");
                                tv_Message.setText("Thao tác này sẽ không thể khôi phục lại");

                                btn_Accept.setOnClickListener(v1 -> {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Xác nhận xoá");
                                    builder.setMessage("Hãy xác nhận nếu bạn thật sự muốn xoá.");

                                    builder.setPositiveButton("Xác nhận", ((dialog, which) -> {

                                        daoHelp.deleteHelp(help.getIdHelp());

                                        if (daoHelp.deleteHelp(help.getIdHelp())) {

                                            getListHelp();
                                            dialog.dismiss();
                                            bottomDialog.dismiss();
                                            Toast.makeText(getContext(), "Đã xoá trợ giúp ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                                        }

                                    }));

                                    builder.setNegativeButton("Huỷ bỏ", (dialog, which) -> {
                                        dialog.dismiss();
                                        bottomDialog.dismiss();
                                    });

                                    AlertDialog dialog = builder.create();
                                    dialog.show();

                                });

                                tv_Cancel.setOnClickListener(v1 -> {
                                    bottomDialog.dismiss();
                                });

                                bottomDialog.show();
                                bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
                                bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ


                            }

                            return true;
                        });

                        listViewHelp.add(view);
                    }

                    for (View view : listViewHelp
                    ) {
                        grid_help.addView(view);
                    }
                } else {
                    handler.postDelayed(this, 500);
                }
            }
        };

        handler.postDelayed(runnable, 500);

    }
}
