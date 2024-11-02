package fpl.datn.damau_ph21672.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.PhieuMuonActivity;
import fpl.datn.damau_ph21672.R;
import fpl.datn.damau_ph21672.dao.LoaiSachDAO;
import fpl.datn.damau_ph21672.dao.PhieuMuonDAO;
import fpl.datn.damau_ph21672.model.LoaiSach;
import fpl.datn.damau_ph21672.model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    private Context context;
    private ArrayList<PhieuMuon> list;
    private PhieuMuonDAO phieuMuonDAO;

    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.list = list;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuMuon phieuMuon = list.get(position);
        holder.ngayMuon.setText(phieuMuon.getNgayMuon());
        holder.trangThai.setText(phieuMuon.getTrangThai() == 1 ? "Đã trả" : "Chưa trả");
        holder.tenND.setText(phieuMuon.getTenND());
        holder.itemView.setOnClickListener(v -> {
            // Gọi hàm từ activity để sửa phiếu mượn
            if (context instanceof PhieuMuonActivity) {
                ((PhieuMuonActivity) context).showEditPhieuMuonDialog(phieuMuon);
            }
        });
        holder.ivDel.setOnClickListener(v -> {
            // Hiển thị dialog xác nhận trước khi xóa
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa phiếu mượn");
            builder.setMessage("Bạn có chắc chắn muốn xóa phiếu mượn này không?");

            builder.setPositiveButton("Xóa", (dialog, which) -> {
                // Xóa phiếu mượn khỏi cơ sở dữ liệu
                boolean result = phieuMuonDAO.xoaPhieuMuon(phieuMuon.getMaPM());

                if (result) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    // Xóa phiếu mượn khỏi danh sách và cập nhật RecyclerView
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size());
                } else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ngayMuon, trangThai, tenND;
        ImageView ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ngayMuon = itemView.findViewById(R.id.txtNgayMuon);
            trangThai = itemView.findViewById(R.id.txtTrangThai);
            tenND = itemView.findViewById(R.id.txtTenND);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }
}
