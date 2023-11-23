package com.example.closetgremlin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ViewHolder> {

    private ClothData[] clothData;
    private Context context;

    public ClothingAdapter(ClothData[] clothData, Context context) {
        this.clothData = clothData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.clothing_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ClothData clothDataItem = clothData[position];
        holder.textViewName.setText(clothDataItem.getMovieName());
        holder.textViewDate.setText(clothDataItem.getMovieDate());
        holder.clothingImage.setImageResource(clothDataItem.getMovieImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, clothDataItem.getMovieName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return clothData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView clothingImage;
        public TextView textViewName;
        public TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clothingImage = itemView.findViewById(R.id.cardImageView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textDate);
        }
    }

}
