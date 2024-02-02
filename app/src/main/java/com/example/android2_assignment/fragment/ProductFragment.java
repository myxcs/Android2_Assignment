package com.example.android2_assignment.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.android2_assignment.R;
import com.example.android2_assignment.adapter.ProductAdapter;
import com.example.android2_assignment.dao.SanPhamDAO;
import com.example.android2_assignment.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFragment extends Fragment {
    private RecyclerView recProduct;
    private FloatingActionButton floatAdd;
    private EditText edtTensp, edtGiaban, edtSoluong;
    private Button btnAdd, btnCancel;
    private SanPhamDAO sanPhamDAO;
    private ImageView ivHinhSP;
    private TextView tvTrangThai;

    private String filePath = "";
    private String linkHinh = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);


        recProduct = view.findViewById(R.id.rec_product);
        floatAdd = view.findViewById(R.id.float_add);

        configCloudinary();
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
        ProductAdapter adapter = new ProductAdapter(getContext(), list, sanPhamDAO);
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
        ivHinhSP = view.findViewById(R.id.img_sanpham);
        tvTrangThai = view.findViewById(R.id.tv_trangthai);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edtTensp.getText().toString();
                String giaban = edtGiaban.getText().toString();
                String soluong = edtSoluong.getText().toString();
                if (tensp.isEmpty() || giaban.isEmpty() || soluong.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ", Toast.LENGTH_SHORT).show();
                }else {
                    Product product = new Product(tensp, Integer.parseInt(giaban), Integer.parseInt(soluong), linkHinh);
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

        //xử lý hình
        ivHinhSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });
    }

    //lấy hình ảnh
    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //get the image's file location
            filePath = getRealPathFromUri(result.getData().getData(), getActivity());

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //set picked image to the mProfile
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                    ivHinhSP.setImageBitmap(bitmap);
                    uploadToCloudinary(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private String getRealPathFromUri(Uri imageUri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if (cursor == null) {
            return imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    HashMap<String, String> config = new HashMap<>();

    private void configCloudinary() {
        config.put("cloud_name", "du6cuhb3q");
        config.put("api_key", "135214143533253");
        config.put("api_secret", "bdfyutsB1_B03FAr1jd7W82tIh0");
        MediaManager.init(getActivity(), config);
    }

    private void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                tvTrangThai.setText("Bắt đầu upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                tvTrangThai.setText("Đang upload... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                tvTrangThai.setText("Thành công: " + resultData.get("url").toString());
                linkHinh = resultData.get("url").toString();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                tvTrangThai.setText("Lỗi " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                tvTrangThai.setText("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }

}
