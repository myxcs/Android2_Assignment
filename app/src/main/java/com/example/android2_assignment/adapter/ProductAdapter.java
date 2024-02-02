package com.example.android2_assignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2_assignment.R;
import com.example.android2_assignment.dao.SanPhamDAO;
import com.example.android2_assignment.model.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;
    private SanPhamDAO sanPhamDAO;

    public ProductAdapter(Context context, ArrayList<Product> list, SanPhamDAO sanPhamDAO) {
        this.context = context;
        this.list = list;
        this.sanPhamDAO = sanPhamDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.tvName.setText(list.get(position).getTensp());

         NumberFormat format = new DecimalFormat("#,###");
         double price = list.get(position).getGiaban();
         String priceFormat = format.format(price);
         holder.tvPrice.setText(priceFormat+ " VND");

         holder.tvQuantity.setText("Số lượng: " +list.get(position).getSoluong());

         //xu ly update delete
         holder.tvEdit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //xu ly update
                 showDialogUpdate(list.get(holder.getAdapterPosition()));
             }
         });
         holder.tvDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //xu ly delete

             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity, tvEdit, tvDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_product_name);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_product_quantity);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }
    private void showDialogUpdate(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //dua du lieu cu
        EditText edName = view.findViewById(R.id.ed_ten);
        EditText edPrice = view.findViewById(R.id.ed_gia);
        EditText edQuantity = view.findViewById(R.id.ed_soluong);
        Button btnUpdate = view.findViewById(R.id.btn_update);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        //dua du lieu can sua vao form

        edName.setText(product.getTensp());
        edPrice.setText(String.valueOf(product.getGiaban()));
        edQuantity.setText(String.valueOf(product.getSoluong()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masp = product.getMasp();
                String name = edName.getText().toString();
                String price = edPrice.getText().toString();
                String quantity = edQuantity.getText().toString();

                if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Product productChinhSua = new Product(masp, name, Integer.parseInt(price), Integer.parseInt(quantity));
                    boolean check = sanPhamDAO.chinhSuaSP(productChinhSua);
                    if (check) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                        //cap nhat lai list
                        list.clear();
                        list = sanPhamDAO.getDS();
                        notifyDataSetChanged();

                        alertDialog.dismiss();

                    }
                    else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                alertDialog.dismiss();
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
