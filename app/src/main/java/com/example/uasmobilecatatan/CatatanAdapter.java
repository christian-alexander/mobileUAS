package com.example.uasmobilecatatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatatanAdapter extends RecyclerView.Adapter<CatatanAdapter.ViewHolder> {
    private ArrayList<CatatanM> catatanMArrayList;
    private Context context;
    private CatatanClickInterface catatanClickInterface;

    int lastPos = -1;

    public CatatanAdapter(ArrayList<CatatanM> catatanMArrayList, Context context, CatatanClickInterface catatanClickInterface) {
        this.catatanMArrayList = catatanMArrayList;
        this.context = context;
        this.catatanClickInterface = catatanClickInterface;
    }

    @NonNull
    @Override
    public CatatanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catatan_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CatatanAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CatatanM catatanM = catatanMArrayList.get(position);
        holder.itemcatatan.setText(catatanM.getCatatan());
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catatanClickInterface.onCatatanClick(position);


            }
        });
    }

    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return catatanMArrayList.size();

    }

    public interface CatatanClickInterface{
        void onCatatanClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView itemcatatan;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemcatatan = itemView.findViewById(R.id.itemcatatan);

        }
    }





}
