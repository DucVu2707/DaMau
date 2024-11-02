package fpl.datn.damau_ph21672.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

import fpl.datn.damau_ph21672.R;
import fpl.datn.damau_ph21672.dao.LoaiSachDAO;
import fpl.datn.damau_ph21672.model.LoaiSach;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvMaloai.setText("ID:" + list.get(position).getMaloai());
        holder.tvTenLoai.setText(list.get(position).getTenloai());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn xóa " + list.get(holder.getAdapterPosition()).getTenloai() + " không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getMaloai());
                        switch (check) {
                            case -1:
                                Toast.makeText(context, "Xóa thất bại~", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Vẫn còn sách của loại này~", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "Xóa thành công~", Toast.LENGTH_SHORT).show();
                                loadData();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Không", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaloai, tvTenLoai;
        ImageView ivEdit, ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaloai = itemView.findViewById(R.id.tvMaLoai);
            tvTenLoai = itemView.findViewById(R.id.tvTenLoai);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);

        }
    }

    private void showDialogUpdate(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_cate, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.setCancelable(false);
        TextView txtTieuDe = view.findViewById(R.id.txtTieuDe);
        EditText edtThemLoai = view.findViewById(R.id.edtTenLoai);
        Button btnLuu = view.findViewById(R.id.btnLuu);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        txtTieuDe.setText("Cập nhật loại sách");
        btnLuu.setText("Cập nhật");
        edtThemLoai.setText(loaiSach.getTenloai());
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtThemLoai.getText().toString();
                LoaiSach loaiSachUpdate = new LoaiSach(loaiSach.getMaloai(), tenLoai);
                boolean check = loaiSachDAO.suaLoaiSach(loaiSachUpdate);
                if (check) {
                    Toast.makeText(context, "Cập nhật thành công~", Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void loadData() {
        list.clear();
        list = loaiSachDAO.getDSLoaiSach();
        notifyDataSetChanged();
    }
}
