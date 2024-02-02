package com.example.android2_assignment.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2_assignment.R;
import com.example.android2_assignment.adapter.ProductAdapter;
import com.example.android2_assignment.dao.SanPhamDAO;
import com.example.android2_assignment.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private RecyclerView recProduct;
    private FloatingActionButton floatAdd;
    private EditText edtTensp, edtGiaban, edtSoluong;
    private Button btnAdd, btnCancel;
    private SanPhamDAO sanPhamDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);


        recProduct = view.findViewById(R.id.rec_product);
        floatAdd = view.findViewById(R.id.float_add);
        sanPhamDAO = new SanPhamDAO(getContext());
        loadData();

        //xứ lý
        //thêm
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v,"Thêm sản phẩm", Snackbar.LENGTH_SHORT).show();
                showDialogAdd();
            }
        });

        //xử lý recyclerView

        return view;
    }
    private void loadData(){
        ArrayList<Product> list = sanPhamDAO.getDS();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recProduct.setLayoutManager(layoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(), list);
        recProduct.setAdapter(adapter);
    }
    private void showDialogAdd(){
        //như tên
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //xu ly nut
        btnAdd = view.findViewById(R.id.btn_add);
        btnCancel = view.findViewById(R.id.btn_cancel);
        edtTensp = view.findViewById(R.id.ed_ten);
        edtGiaban = view.findViewById(R.id.ed_gia);
        edtSoluong = view.findViewById(R.id.ed_soluong);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edtTensp.getText().toString();
                String giaban = edtGiaban.getText().toString();
                String soluong = edtSoluong.getText().toString();
                if (tensp.isEmpty() || giaban.isEmpty() || soluong.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
                }else {
                    Product product = new Product(tensp, Integer.parseInt(giaban), Integer.parseInt(soluong));
                    boolean check = sanPhamDAO.themSPmoi(product);
                    if (check){
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();

                    }
                    else {
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
