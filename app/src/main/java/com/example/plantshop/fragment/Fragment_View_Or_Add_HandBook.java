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

import com.example.plantshop.R;
import com.example.plantshop.activity.MainActivity;
import com.example.plantshop.firebase.DAO_Stage;
import com.example.plantshop.firebase.DAO_Step;
import com.example.plantshop.model.Product;
import com.example.plantshop.model.Stage;
import com.example.plantshop.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_View_Or_Add_HandBook extends Fragment {

    private ImageView img_Back, img_Product;
    private TextView tv_NameProduct, tv_Type_Product, tv_TypeOf_Product, addOrViewStep, addOrViewStage;
    private int getId;
    private ArrayList<Product> listPlant = Fragment_HandBook.listPlant;
    protected ArrayList<Step> listStep;
    protected ArrayList<Stage> listStage;
    private Product product;
    private GridLayout gridStep, gridStage;
    private boolean checkLayout_Step, checkLayout_Stage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_or_add_handbook, container, false);

        img_Back = view.findViewById(R.id.img_Back);
        img_Product = view.findViewById(R.id.img_Product);
        tv_NameProduct = view.findViewById(R.id.tv_NameProduct);
        tv_Type_Product = view.findViewById(R.id.tv_Type_Product);
        tv_TypeOf_Product = view.findViewById(R.id.tv_TypeOf_Product);
        addOrViewStep = view.findViewById(R.id.addOrViewStep);
        addOrViewStage = view.findViewById(R.id.addOrViewStage);

        gridStep = view.findViewById(R.id.gridStep);
        gridStage = view.findViewById(R.id.gridStage);

        Bundle bundle = getArguments();
        getId = bundle.getInt("idPlant");

        for (Product pd : listPlant
        ) {
            if (pd.getIdSanPham() == getId) {
                product = pd;

                Picasso.get().load(pd.getUrl_Img()).into(img_Product);
                tv_NameProduct.setText(pd.getTenSanPham());
                tv_Type_Product.setText(pd.getLoaiSanPham());
                tv_TypeOf_Product.setText(pd.getTheLoaiSanPham());
                break;
            }
        }

        img_Back.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fr_Layout, new Fragment_HandBook()).commit();
        });

        // lấy danh sách các bước và giai đoạn
        getListStep();
        getListStage();

        if (MainActivity.getID > 0) {
            gridStep.setVisibility(View.GONE);
            gridStage.setVisibility(View.GONE);
        }

        DAO_Step daoStep = new DAO_Step(product.getIdSanPham());
        DAO_Stage daoStage = new DAO_Stage(product.getIdSanPham());

        addOrViewStep.setOnClickListener(v -> {

            if (MainActivity.getID == 0) {

                int idStep = daoStep.getNewId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View layoutAdd = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_hanbook_help, null);
                builder.setView(layoutAdd);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView tv_Title = layoutAdd.findViewById(R.id.tv_Title);
                EditText edt_Question = layoutAdd.findViewById(R.id.edt_Question);
                EditText edt_Content = layoutAdd.findViewById(R.id.edt_Content);
                Button btn_Cancel = layoutAdd.findViewById(R.id.btn_Cancel);
                Button btn_Add = layoutAdd.findViewById(R.id.btn_Add);

                tv_Title.setText("Bước " + (idStep + 1));

                btn_Add.setOnClickListener(v1 -> {

                    String question = edt_Question.getText().toString();
                    String content = edt_Content.getText().toString();

                    if (question.isEmpty()) {
                        edt_Question.setError("Trống");
                        edt_Question.requestFocus();
                    } else if (content.isEmpty()) {
                        edt_Content.setError("Trống");
                        edt_Content.requestFocus();
                    } else {

                        Step step = new Step();
                        step.setIdStep(idStep);
                        step.setStep(idStep + 1);
                        step.setStepName(question);
                        step.setStepContent(content);
                        daoStep.pushStep(step);

                        if (daoStep.pushStep(step)) {

                            getListStep();
                            edt_Question.setText("");
                            edt_Content.setText("");
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Đã thêm bước mới", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_Cancel.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
            } else {

                if (checkLayout_Step) {

                    gridStep.setVisibility(View.GONE);
                    checkLayout_Step = false;

                } else {

                    gridStep.setVisibility(View.VISIBLE);
                    checkLayout_Step = true;
                }
            }

        });

        addOrViewStage.setOnClickListener(v -> {

            if (MainActivity.getID == 0) {

                int idStage = daoStage.getNewId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View layoutAdd = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_hanbook_help, null);
                builder.setView(layoutAdd);
                AlertDialog dialog = builder.create();
                dialog.show();

                TextView tv_Title = layoutAdd.findViewById(R.id.tv_Title);
                EditText edt_Question = layoutAdd.findViewById(R.id.edt_Question);
                EditText edt_Content = layoutAdd.findViewById(R.id.edt_Content);
                Button btn_Cancel = layoutAdd.findViewById(R.id.btn_Cancel);
                Button btn_Add = layoutAdd.findViewById(R.id.btn_Add);

                tv_Title.setText("Giai đoạn " + (idStage + 1));

                btn_Add.setOnClickListener(v1 -> {

                    String question = edt_Question.getText().toString();
                    String content = edt_Content.getText().toString();

                    if (question.isEmpty()) {
                        edt_Question.setError("Trống");
                        edt_Question.requestFocus();
                    } else if (content.isEmpty()) {
                        edt_Content.setError("Trống");
                        edt_Content.requestFocus();
                    } else {

                        Stage stage = new Stage();
                        stage.setIdStage(idStage);
                        stage.setStage(idStage + 1);
                        stage.setStageName(question);
                        stage.setStageContent(content);

                        daoStage.pushStage(stage);

                        if (daoStage.pushStage(stage)) {

                            getListStage();
                            edt_Question.setText("");
                            edt_Content.setText("");
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Đã thêm bước mới", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btn_Cancel.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
            } else {

                if (checkLayout_Stage) {

                    gridStage.setVisibility(View.GONE);
                    checkLayout_Stage = false;

                } else {

                    gridStage.setVisibility(View.VISIBLE);
                    checkLayout_Stage = true;
                }
            }

        });

        return view;
    }

    public void getListStep() {

        listStep = new ArrayList<>();
        DAO_Step daoStep = new DAO_Step(product.getIdSanPham());
        listStep = daoStep.getListStep();

        ArrayList<Boolean> isContentVisibleList = new ArrayList<>();
        ArrayList<View> listViewStep = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (listStep.size() > 0) {
                    handler.removeCallbacks(this);

                    gridStep.removeAllViews();
                    gridStep.setRowCount(listStep.size());

                    for (Step step : listStep
                    ) {

                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_step, null);

                        TextView tv_Step = view.findViewById(R.id.tv_Step);
                        TextView tv_Content = view.findViewById(R.id.tv_Content);
                        ImageView img_icon = view.findViewById(R.id.img_icon);
                        RelativeLayout ly_Step = view.findViewById(R.id.ly_Step);

                        tv_Step.setText("Bước " + step.getStep() + ": " + step.getStepName());
                        tv_Content.setText(step.getStepContent());

                        // Thêm một biến boolean để theo dõi trạng thái hiện tại của nội dung
                        isContentVisibleList.add(false);

                        ly_Step.setOnClickListener(v -> {
                            if (isContentVisibleList.get(listStep.indexOf(step))) {
                                img_icon.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                                tv_Content.setVisibility(View.GONE);
                            } else {
                                img_icon.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
                                tv_Content.setVisibility(View.VISIBLE);
                            }

                            // Đảo ngược trạng thái boolean khi ly_Step được click
                            isContentVisibleList.set(listStep.indexOf(step), !isContentVisibleList.get(listStep.indexOf(step)));

                        });

                        Dialog bottomDialog = new Dialog(getContext());
                        bottomDialog.setContentView(R.layout.layout_sheet_bottom);

                        Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
                        TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
                        TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);
                        TextView tv_Message = bottomDialog.findViewById(R.id.tv_Message);  // bottom dialog


                        view.setOnClickListener(v -> { //  sửa các bước
                            if (MainActivity.getID == 0) {


                                tv_Title.setText("Chỉnh sửa");
                                tv_Message.setText("Bạn có muốn chỉnh sửa lại nội dung?");
                                btn_Accept.setText("Chỉnh sửa");

                                btn_Accept.setOnClickListener(v1 -> {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    View layoutAdd = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_hanbook_help, null);
                                    builder.setView(layoutAdd);
                                    AlertDialog dialog = builder.create();

                                    dialog.show();

                                    TextView tv_TitleStep = layoutAdd.findViewById(R.id.tv_Title);
                                    EditText edt_Question = layoutAdd.findViewById(R.id.edt_Question);
                                    EditText edt_Content = layoutAdd.findViewById(R.id.edt_Content);
                                    Button btn_Cancel = layoutAdd.findViewById(R.id.btn_Cancel);
                                    Button btn_Add = layoutAdd.findViewById(R.id.btn_Add);

                                    tv_TitleStep.setText("Bước " + step.getStep());
                                    edt_Question.setText(step.getStepName());
                                    edt_Content.setText(step.getStepContent());

                                    btn_Add.setOnClickListener(view1 -> {

                                        String question = edt_Question.getText().toString();
                                        String content = edt_Content.getText().toString();

                                        if (question.isEmpty()) {
                                            edt_Question.setError("Trống");
                                            edt_Question.requestFocus();
                                        } else if (content.isEmpty()) {
                                            edt_Content.setError("Trống");
                                            edt_Content.requestFocus();
                                        } else {

                                            step.setStepName(question);
                                            step.setStepContent(content);
                                            daoStep.pushStep(step);

                                            if (daoStep.pushStep(step)) {

                                                getListStep();
                                                edt_Question.setText("");
                                                edt_Content.setText("");
                                                bottomDialog.dismiss();
                                                dialog.dismiss();
                                                Toast.makeText(getContext(), "Đã thêm bước mới", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    btn_Cancel.setOnClickListener(view1 -> {
                                        bottomDialog.dismiss();
                                        dialog.dismiss();
                                    });

                                });


                                tv_Cancel.setOnClickListener(v1 -> { // huỷ xoá
                                    bottomDialog.dismiss();
                                });

                                bottomDialog.show();
                                bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
                                bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ

                            }
                        });

                        view.setOnLongClickListener(v -> { // giữ để xoá

                            tv_Title.setText("Xoá bước?");
                            tv_Message.setText("Thao tác này sẽ không thể khôi phục lại");

                            btn_Accept.setOnClickListener(v1 -> {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Xác nhận xoá");
                                builder.setMessage("Hãy xác nhận nếu bạn thật sự muốn xoá.");

                                builder.setPositiveButton("Xác nhận", ((dialog, which) -> {
                                    daoStep.deleteStep(step.getIdStep());

                                    if (daoStep.deleteStep(step.getIdStep())) {
                                        getListStep();
                                        dialog.dismiss();
                                        bottomDialog.dismiss();
                                        Toast.makeText(getContext(), "Đã xoá bước " + step.getStep(), Toast.LENGTH_SHORT).show();
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


                            return true;
                        });

                        listViewStep.add(view);

                    }

                    for (View view : listViewStep
                    ) {
                        gridStep.addView(view);
                    }


                } else {
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.postDelayed(runnable, 500);
    }

    public void getListStage() {

        listStage = new ArrayList<>();
        DAO_Stage daoStage = new DAO_Stage(product.getIdSanPham());
        listStage = daoStage.getListStage();

        ArrayList<Boolean> isContentVisibleList = new ArrayList<>();
        ArrayList<View> listViewStage = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (listStage.size() > 0) {
                    handler.removeCallbacks(this);

                    gridStage.removeAllViews();
                    gridStage.setRowCount(listStep.size());

                    for (Stage stage : listStage
                    ) {

                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_step, null);

                        TextView tv_Stage = view.findViewById(R.id.tv_Step);
                        TextView tv_Content = view.findViewById(R.id.tv_Content);
                        ImageView img_icon = view.findViewById(R.id.img_icon);
                        RelativeLayout ly_Step = view.findViewById(R.id.ly_Step);

                        tv_Stage.setText(stage.getStage() + ". " + stage.getStageName());
                        tv_Content.setText(stage.getStageContent());

                        // Thêm một biến boolean để theo dõi trạng thái hiện tại của nội dung
                        isContentVisibleList.add(false);

                        ly_Step.setOnClickListener(v -> {
                            if (isContentVisibleList.get(listStage.indexOf(stage))) {
                                img_icon.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                                tv_Content.setVisibility(View.GONE);
                            } else {
                                img_icon.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
                                tv_Content.setVisibility(View.VISIBLE);
                            }

                            // Đảo ngược trạng thái boolean khi ly_Step được click
                            isContentVisibleList.set(listStage.indexOf(stage), !isContentVisibleList.get(listStage.indexOf(stage)));

                        });

                        Dialog bottomDialog = new Dialog(getContext());
                        bottomDialog.setContentView(R.layout.layout_sheet_bottom);

                        Button btn_Accept = bottomDialog.findViewById(R.id.btn_Accept);
                        TextView tv_Cancel = bottomDialog.findViewById(R.id.tv_Cancel);
                        TextView tv_Title = bottomDialog.findViewById(R.id.tv_Title);
                        TextView tv_Message = bottomDialog.findViewById(R.id.tv_Message);  // bottom dialog

                        view.setOnClickListener(v -> { //  sửa các bước
                            if (MainActivity.getID == 0) {


                                tv_Title.setText("Chỉnh sửa");
                                tv_Message.setText("Bạn có muốn chỉnh sửa lại nội dung?");
                                btn_Accept.setText("Chỉnh sửa");

                                btn_Accept.setOnClickListener(v1 -> {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    View layoutAdd = LayoutInflater.from(getContext()).inflate(R.layout.layout_add_hanbook_help, null);
                                    builder.setView(layoutAdd);
                                    AlertDialog dialog = builder.create();

                                    dialog.show();

                                    TextView tv_TitleStep = layoutAdd.findViewById(R.id.tv_Title);
                                    EditText edt_Question = layoutAdd.findViewById(R.id.edt_Question);
                                    EditText edt_Content = layoutAdd.findViewById(R.id.edt_Content);
                                    Button btn_Cancel = layoutAdd.findViewById(R.id.btn_Cancel);
                                    Button btn_Add = layoutAdd.findViewById(R.id.btn_Add);

                                    tv_TitleStep.setText("Giai đoạn  " + stage.getStage());
                                    edt_Question.setText(stage.getStageName());
                                    edt_Content.setText(stage.getStageContent());

                                    btn_Add.setOnClickListener(view1 -> {

                                        String question = edt_Question.getText().toString();
                                        String content = edt_Content.getText().toString();

                                        if (question.isEmpty()) {
                                            edt_Question.setError("Trống");
                                            edt_Question.requestFocus();
                                        } else if (content.isEmpty()) {
                                            edt_Content.setError("Trống");
                                            edt_Content.requestFocus();
                                        } else {

                                            stage.setStageName(question);
                                            stage.setStageContent(content);
                                            daoStage.pushStage(stage);

                                            if (daoStage.pushStage(stage)) {

                                                getListStage();
                                                edt_Question.setText("");
                                                edt_Content.setText("");
                                                bottomDialog.dismiss();
                                                dialog.dismiss();
                                                Toast.makeText(getContext(), "Đã thêm bước mới", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    btn_Cancel.setOnClickListener(view1 -> {
                                        bottomDialog.dismiss();
                                        dialog.dismiss();
                                    });

                                });


                                tv_Cancel.setOnClickListener(v1 -> { // huỷ xoá
                                    bottomDialog.dismiss();
                                });

                                bottomDialog.show();
                                bottomDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //Thiết lập kích thước cửa sổ
                                bottomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                bottomDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                                bottomDialog.getWindow().setGravity(Gravity.BOTTOM); //Vị trí cửa sổ

                            }
                        });

                        view.setOnLongClickListener(v -> { // giữ để xoá

                            tv_Title.setText("Xoá bước?");
                            tv_Message.setText("Thao tác này sẽ không thể khôi phục lại");

                            btn_Accept.setOnClickListener(v1 -> {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Xác nhận xoá");
                                builder.setMessage("Hãy xác nhận nếu bạn thật sự muốn xoá.");

                                builder.setPositiveButton("Xác nhận", ((dialog, which) -> {
                                    daoStage.deleteStage(stage.getIdStage());

                                    if (daoStage.deleteStage(stage.getIdStage())) {
                                        getListStage();
                                        dialog.dismiss();
                                        bottomDialog.dismiss();
                                        Toast.makeText(getContext(), "Đã xoá giai đoạn " + stage.getStage(), Toast.LENGTH_SHORT).show();
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


                            return true;
                        });

                        listViewStage.add(view);

                    }

                    for (View view : listViewStage
                    ) {
                        gridStage.addView(view);
                    }


                } else {
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.postDelayed(runnable, 500);
    }


}
