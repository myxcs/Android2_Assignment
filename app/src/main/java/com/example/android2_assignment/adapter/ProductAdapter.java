package com.example.android2_assignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android2_assignment.R;
import com.example.android2_assignment.model.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list;

    public ProductAdapter(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
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
}
