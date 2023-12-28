package com.example.sqliteroomsample.view;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqliteroomsample.R;

import java.util.ArrayList;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
    private ArrayList<ItemData> itemDataArrayList = new ArrayList<>();
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNote.setText(itemDataArrayList.get(position).note);
        holder.tvNote.setTextColor(itemDataArrayList.get(position).noteTextColor);
        holder.tvState.setText(itemDataArrayList.get(position).state);
        holder.tvState.setBackground(itemDataArrayList.get(position).stateBackgroundDrawable);
        holder.iv3Check.setImageDrawable(itemDataArrayList.get(position).checkDrawable);
    }

    @Override
    public int getItemCount() {
        return itemDataArrayList.size();
    }

    public void setItemDataArrayList(ArrayList<ItemData> itemDataArrayList){
        this.itemDataArrayList = itemDataArrayList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv3Check;
        TextView tvNote;
        TextView tvState;
        public MyViewHolder(View itemView){
            super(itemView);

            iv3Check = itemView.findViewById(R.id.iv3_check);
            tvNote = itemView.findViewById(R.id.tv_note);
            tvState = itemView.findViewById(R.id.tv_state);
        }
    }

    public static class ItemData{
        Drawable checkDrawable;
        String note;
        String state;
        Drawable stateBackgroundDrawable;
        int noteTextColor;
        public ItemData(Drawable checkDrawable, String note, String state, Drawable stateBackgroundDrawable, int noteTextColor){
            this.checkDrawable = checkDrawable;
            this.note = note;
            this.state = state;
            this.stateBackgroundDrawable = stateBackgroundDrawable;
            this.noteTextColor = noteTextColor;
        }

    }
}
