package com.example.shopping_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping_app.model.Product;
import com.example.shopping_app.ui.ProductDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.Holder> implements Filterable {
    ArrayList<Product> productList;
    ArrayList<Product> copyproductList;
    Context mContext;

    public productAdapter(ArrayList<Product> products, Context mCtx) {
        this.productList = products;
        copyproductList = new ArrayList<>(productList);
        this.mContext = mCtx;
    }


    @NonNull
    @NotNull
    @Override
    public productAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context c = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(c);
        View CategoryView = inflater.inflate(R.layout.list_item,parent,false);
        Holder holder = new Holder(CategoryView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull productAdapter.Holder holder, int position) {
        Product currentProduct = productList.get(position);
        String imageUrl = currentProduct.getImage();
        double price = currentProduct.getPrice();
        String name = currentProduct.getTitle();
        float rate = currentProduct.getRate();
        int ratecount = currentProduct.getRatecount();

        Glide.with(mContext).load(imageUrl).into(holder.productimage);
        holder.productname.setText(name);
        holder.productprice.setText("₹"+(price));
        holder.ratecount.setText("("+ratecount+")");
        holder.productrate.setRating(rate);

        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra("productId",currentProduct.getId());
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView productname, productprice, ratecount;
        RatingBar productrate;
        ImageView productimage;
        CardView productCard;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            productname = itemView.findViewById(R.id.productName);
            productprice=itemView.findViewById(R.id.productPrice);
            ratecount = itemView.findViewById(R.id.productRateCount);
            productrate = itemView.findViewById(R.id.productRate);
            productimage = itemView.findViewById(R.id.productImage);
            productCard = itemView.findViewById(R.id.productCard);
        }
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Product> filteredList = new ArrayList<>();


            if (copyproductList.size()>0) {
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(copyproductList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Product item : copyproductList) {
                        if (item.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

}
