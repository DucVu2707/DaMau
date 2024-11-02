package fpl.datn.damau_ph21672.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.BookActivity;
import fpl.datn.damau_ph21672.R;
import fpl.datn.damau_ph21672.dao.SachDAO;
import fpl.datn.damau_ph21672.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;
    private SachDAO sachDAO;
    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        this.sachDAO = new SachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_book, parent, false);

        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaSach.setText(String.valueOf("ID:" +list.get(position).getMasach()));
        holder.tvTenSach.setText(list.get(position).getTensach());
        holder.tvGia.setText(String.valueOf(list.get(position).getGiaban()) + "VND");
        holder.tvTacgia.setText(list.get(position).getTacgia());
        holder.tvTenloai.setText(list.get(position).getTenloai());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDel.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xóa sách")
                    .setMessage("Bạn có chắc chắn muốn xóa sách này không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        int check = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                        if (check == 1) {
                            Toast.makeText(context, "Xóa sách thành công!", Toast.LENGTH_SHORT).show();
                            list.remove(holder.getAdapterPosition()); // Xóa sách khỏi danh sách
                            notifyItemRemoved(holder.getAdapterPosition()); // Cập nhật RecyclerView
                            notifyItemRangeChanged(holder.getAdapterPosition(), list.size()); // Cập nhật lại thứ tự
                        } else if (check == 0) {
                            Toast.makeText(context, "Sách này đang được sử dụng trong phiếu mượn, không thể xóa!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Xóa sách thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaSach, tvTenSach, tvTacgia, tvGia, tvTenloai;
        ImageView ivEdit, ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvTacgia = itemView.findViewById(R.id.tvTacgia);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvTenloai = itemView.findViewById(R.id.tvTenLoai);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }
    private void showDialogUpdate(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_book, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        // Ánh xạ các view trong dialog
        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTacGia = view.findViewById(R.id.edtTacGia);
        EditText edtGiaBan = view.findViewById(R.id.edtGiaBan);
        EditText edtMaLoai = view.findViewById(R.id.edtMaLoai);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        TextView txtTieude = view.findViewById(R.id.tvTitle);

        txtTieude.setText("Cập nhật sách:");
        // Đặt thông tin sách vào các EditText để người dùng sửa đổi
        edtTenSach.setText(sach.getTensach());
        edtTacGia.setText(sach.getTacgia());
        edtGiaBan.setText(String.valueOf(sach.getGiaban()));
        edtMaLoai.setText(String.valueOf(sach.getMaloai()));

        // Xử lý sự kiện nút Lưu
        btnLuu.setOnClickListener(v -> {
            // Cập nhật lại thông tin sách từ các EditText
            sach.setTensach(edtTenSach.getText().toString());
            sach.setTacgia(edtTacGia.getText().toString());
            sach.setGiaban(Integer.parseInt(edtGiaBan.getText().toString()));
            sach.setMaloai(Integer.parseInt(edtMaLoai.getText().toString()));

            // Gọi phương thức sửa sách trong SachDAO
            boolean check = sachDAO.suaSach(sach);
            if (check) {
                Toast.makeText(context, "Cập nhật sách thành công~", Toast.LENGTH_SHORT).show();
                loadData(); // Tải lại danh sách sau khi sửa sách thành công
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Cập nhật sách thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút Hủy
        btnHuy.setOnClickListener(v -> dialog.dismiss());
    }
    private void loadData() {
        list = sachDAO.getDsSach(); // Lấy danh sách mới
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

}
