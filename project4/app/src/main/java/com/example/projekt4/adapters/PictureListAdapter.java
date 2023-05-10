package com.example.projekt4.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt4.R;
import com.example.projekt4.activities.fragments.FragmentList;
import com.example.projekt4.database.Picture;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.PictureViewHolder> {

    LayoutInflater mLayoutInflater;
    List<Picture> mPictureList;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    public interface onItemClickListener2 {
        void onItemClickListener2(Picture picture);
    }

    onItemClickListener2 mListener;
    public PictureListAdapter(Context context, FragmentList fragmentList) {
        mLayoutInflater = LayoutInflater.from(context);
        mPictureList = null;
        try {
            mListener = (onItemClickListener2) fragmentList;
        }catch (Exception e) {

        }
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mLayoutInflater.inflate(R.layout.wiersz_layout, parent, false);
        return new PictureViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture p = mPictureList.get(position);
        holder.title.setText(p.getName());
        holder.author.setText(p.getAuthor());
        holder.date.setText(dateFormat.format(p.getDate()));
    }

    @Override
    public int getItemCount() {
        if(mPictureList != null) {
            return mPictureList.size();
        }
        return 0;
    }

    public void setPictureList(List<Picture> pictureList) {
        this.mPictureList = pictureList;
        notifyDataSetChanged();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView title;
    public TextView author;
    public TextView date;
        public PictureViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.textTitle);
        author = itemView.findViewById(R.id.textAuthor);
        date = itemView.findViewById(R.id.textDate);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener.onItemClickListener2(mPictureList.get(getAdapterPosition()));
    }
}
}
