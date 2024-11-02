package fpl.datn.damau_ph21672.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpl.datn.damau_ph21672.R;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ThongKeViewHolder> {

    private ArrayList<String> bookList;

    public ThongKeAdapter(ArrayList<String> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ThongKeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongke, parent, false);
        return new ThongKeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKeViewHolder holder, int position) {
        String[] bookInfo = bookList.get(position).split(" - ");
        holder.tvTenSach.setText(bookInfo[0]); // Tên sách
        holder.tvSoLuong.setText(bookInfo[1] + " lượt mượn"); // Số lần mượn
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ThongKeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSach, tvSoLuong;

        public ThongKeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
        }
    }
}
