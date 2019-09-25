package com.krom.moneymanager.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.krom.moneymanager.R;
import com.krom.moneymanager.app.Transaction;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private String[] categoryTitles;
    private int[] categoryImages;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;

    public CategoriesAdapter(Context context, String[] categoryTitles, int[] categoryImages){
        this.inflater = LayoutInflater.from(context);
        this.categoryTitles = categoryTitles;
        this.categoryImages = categoryImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.categories_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categoryTitles[position]);
        holder.categoryImage.setImageResource(categoryImages[position]);

    }

    public String getItem(int id){
        return categoryTitles[id];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return categoryImages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView categoryName;
        ImageView categoryImage;

        ViewHolder(View itemView){
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            //categoryName.setText(categoryTitles[getAdapterPosition()]);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            //categoryImage.setImageResource(categoryImages[getAdapterPosition()]);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemCLick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemCLick(View view, int position);
    }

    //    public CategoriesAdapter(Context context, String[] categoryTitle, int[] categoryImages){
//        this.context = context;
//        this.categoryTitle = categoryTitle;
//        this.categoryImages = categoryImages;
//
//    }
//
//    @Override
//    public int getCount() {
//        return categoryTitle.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View listItem = convertView;
//
//        if (listItem == null){
//            listItem = LayoutInflater.from(context).inflate(R.layout.categories_layout, parent, false);
//        }
//
//        TextView categoryName = listItem.findViewById(R.id.categoryName);
//        categoryName.setText(categoryTitle[position]);
//
//        ImageView categoryImage = listItem.findViewById(R.id.categoryImage);
//        categoryImage.setImageResource(categoryImages[position]);
//
//
//        return listItem;
//    }
}
