package com.example.projekt2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt2.R;
import com.example.projekt2.dataBase.Element;

import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {
    //w odróżnieniu od lab1c adapter nie otrzymuje listy //
    // elementów jako parametru konstruktora w momencie //tworzenia
    // obiektu adaptera lista może nie być dostępna
    LayoutInflater mLayoutInflater;
    List<Element> mElementList;

    public interface onItemClickListener2 {
        void onItemClickListener2(Element element);
    }

    onItemClickListener2 mListener;
    public ElementListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mElementList = null;
        try {
            this.mListener = (onItemClickListener2) context;
        }catch (Exception e) {

        }

    }

    //tworzy nowy wiersz
    @NonNull
    @Override
    public ElementListAdapter.ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View wiersz = mLayoutInflater.inflate(R.layout.wiersz_layout, parent, false);
        return new ElementViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementListAdapter.ElementViewHolder holder, int position) {
        holder.manufacturer.setText(mElementList.get(position).getManufacturer());
        holder.model.setText(mElementList.get(position).getModel());
        holder.version.setText(mElementList.get(position).getVersion());
    }

    @Override
    public int getItemCount() {
        //w momencie tworzenia obiektu adaptera lista może nie
        //być dostępna

        if (mElementList != null)
            return mElementList.size();
        return 0;
    }

    //ponieważ dane wyświetlane na liście będą się zmieniały //
    // ta metoda umożliwia aktualizację
    //danych w adapterze (i w konsekwencji) wyświetlanych w RecyclerView
    public void setElementList(List<Element> elementList) {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    interface OnItemClickListener {
        void onItemClickListener(Element element);
    }

    public class ElementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView manufacturer;
        TextView model;
        TextView version;
        public ElementViewHolder(@NonNull View itemView) {
            super(itemView);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            model = itemView.findViewById(R.id.model);
            version = itemView.findViewById(R.id.version);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mListener.onItemClickListener2(mElementList.get(getAdapterPosition()));
        }
    }
//…
}
