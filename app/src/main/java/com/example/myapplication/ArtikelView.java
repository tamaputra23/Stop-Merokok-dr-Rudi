package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.recyclerview.widget.RecyclerView;

public class ArtikelView extends RecyclerView.ViewHolder  {
    View mView;
    TextView tvJudul, tvNomor;
    public ArtikelView(View itemView) {
        super(itemView);
        mView = itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
    }
    public void setDetails(Context ctx, String judul, String nomor){
        tvNomor = mView.findViewById(R.id.tvNomor);
        tvJudul = mView.findViewById(R.id.tvJudul);
        tvJudul.setText(judul);
        tvNomor.setText(nomor);
    }
    private ArtikelView.ClickListener mClickListener;
    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(ArtikelView.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
