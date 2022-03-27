package com.sanjeevani.rent.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sanjeevani.rent.Models.House;
import com.sanjeevani.rent.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder>{
    private Context context;
    private ArrayList<House> foods;
    private ItemClickListener clickListener;


    public HouseAdapter(Context context, ArrayList<House> foods, ItemClickListener clickListener) {
        this.context = context;
        this.foods = foods;
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HouseViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_rent, parent, false));
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull @NotNull HouseAdapter.HouseViewHolder holder, int position) {
        House data = foods.get(position);

        holder.category.setText(data.getHome_category());
        holder.mTvPrice.setText(String.valueOf(data.getHome_selling_price()));
        holder.address.setText(String.valueOf(data.getHome_address()));

//        Image Loading
        Glide.with(this.context).load(R.drawable.logo).into(holder.houseimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(foods.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder {

        private ImageView houseimg;
        private TextView category;
        private TextView mTvPrice;
        private TextView address;

        public HouseViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            houseimg = itemView.findViewById(R.id.iv_homeImage);
            category = itemView.findViewById(R.id.category_tv);
            mTvPrice = itemView.findViewById(R.id.sellingprice);
            address = itemView.findViewById(R.id.address);
        }
    }
    public interface ItemClickListener {
        void onItemClick(House food);
    }
}
