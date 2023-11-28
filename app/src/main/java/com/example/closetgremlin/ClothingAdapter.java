package com.example.closetgremlin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.ArrayList;

public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.ViewHolder> {

    private ArrayList<ClothData> clothData;
    private Context context;
    private MainActivity mainActivity;

    public ClothingAdapter(ArrayList<ClothData> clothData, Context context, MainActivity mainActivity) {
        this.clothData = clothData;
        this.context = context;
        this.mainActivity = mainActivity;
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
        final ClothData clothDataItem = clothData.get(position);
        holder.textViewName.setText(clothDataItem.getName());
        holder.textViewType.setText("Type: " + clothDataItem.getType());
        holder.textViewOccasion.setText("Occasion: " + clothDataItem.getOccasion());
        holder.textViewLocation.setText("Location: " + clothDataItem.getLocation());
        if(clothDataItem.getBitmap()) {
            holder.clothingImage.setImageBitmap(clothDataItem.getClothingImage());
        }
        else {
            holder.clothingImage.setImageResource(clothDataItem.getClothingResource());
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mainActivity.getDb().delete(mainActivity.getClothData().get(position).getId());
                if (result) {
                    try {
                        mainActivity.setClothes();
                    } catch (ParseException e) {
                        Log.d("CLOTHING", e.toString());
                    }
                    Log.d("DATABASE", "Clothing has been deleted successfully.");
                }
                else
                    Log.d("DATABASE", "Clothing failed to delete.");
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, clothDataItem.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return clothData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView clothingImage;
        public TextView textViewName;
        public TextView textViewType;
        public TextView textViewOccasion;
        public TextView textViewLocation;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clothingImage = itemView.findViewById(R.id.cardImageView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewType = itemView.findViewById(R.id.textType);
            textViewOccasion = itemView.findViewById(R.id.textOccasion);
            textViewLocation = itemView.findViewById(R.id.textLocation);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

}
