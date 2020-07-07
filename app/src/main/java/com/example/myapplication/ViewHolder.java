package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder  {
    View mView;
    ImageView imageView;
    TextView tvnama;
    public ViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }
    public void setDetails(Context ctx, String judul, String nama){
        imageView = mView.findViewById(R.id.ivPicturesview);
        Picasso.get().load(judul).into(imageView);
        tvnama = mView.findViewById(R.id.tvNama);
        tvnama.setText(nama);
    }
    private ViewHolder.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
