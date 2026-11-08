package com.example.lavenderadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CandleAdapter extends RecyclerView.Adapter<CandleAdapter.CandleViewHolder> {

    private Context context;
    private List<Candle> candleList;

    public CandleAdapter(Context context, List<Candle> candleList) {
        this.context = context;
        this.candleList = candleList;
    }

    @NonNull
    @Override
    public CandleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_candle, parent, false);
        return new CandleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandleViewHolder holder, int position) {
        Candle candle = candleList.get(position);

        holder.tvName.setText(candle.getName());
        holder.tvPrice.setText("$" + candle.getPrice());

        // تحميل الصورة باستخدام مكتبة Glide
        Glide.with(context)
                .load(candle.getImageUrl())
                .into(holder.imgCandle);

        holder.btnUpdate.setOnClickListener(v -> {
            // كود التعديل لاحقاً
        });

        holder.btnDelete.setOnClickListener(v -> {
            // كود الحذف لاحقاً
        });
    }

    @Override
    public int getItemCount() {
        return candleList.size();
    }

    public static class CandleViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCandle;
        TextView tvName, tvPrice, btnUpdate, btnDelete;

        public CandleViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCandle = itemView.findViewById(R.id.imgCandleItem);
            tvName = itemView.findViewById(R.id.tvCandleName);
            tvPrice = itemView.findViewById(R.id.tvCandlePrice);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}