package com.example.android2_assignment.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private SanPhamDAO sanPhamDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        //ánh xạ
        recProduct = view.findViewById(R.id.rec_product);
        floatAdd = view.findViewById(R.id.float_add);
        sanPhamDAO = new SanPhamDAO(getContext());
        ArrayList<Product> list = sanPhamDAO.getDS();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recProduct.setLayoutManager(layoutManager);
        ProductAdapter adapter = new ProductAdapter(getContext(), list);
        recProduct.setAdapter(adapter);

        //xứ lý
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Thêm sản phẩm", Snackbar.LENGTH_SHORT).show();
            }
        });

        //xử lý recyclerView

        return view;
    }
}
