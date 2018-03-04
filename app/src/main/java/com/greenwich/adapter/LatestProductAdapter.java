package com.greenwich.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenwich.menwatch.MainActivity;
import com.greenwich.menwatch.ProductDetailsActivity;
import com.greenwich.menwatch.R;
import com.greenwich.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nguye on 2/28/2018.
 */

public class LatestProductAdapter extends RecyclerView.Adapter<LatestProductAdapter.ProductItemsHolder> {

    Context context;
    ArrayList<Product> arrProduct;

    public LatestProductAdapter(Context context, ArrayList<Product> arrProduct) {
        this.context = context;
        this.arrProduct = arrProduct;
    }

    @Override
    public ProductItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_latest_product, null);
        ProductItemsHolder productItemsHolder = new ProductItemsHolder(v);
        return productItemsHolder;
    }

    @Override
    public void onBindViewHolder(ProductItemsHolder holder, int position) {
        Product product = arrProduct.get(position);
        holder.txtProductName.setText(product.getName());
        holder.txtProductPrice.setText("$" + product.getPrice());
        Picasso.with(context).load(product.getImg1())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(holder.imageViewProduct);
    }

    @Override
    public int getItemCount() {
        return arrProduct.size();
    }


    public void getItemPosition(int position) {
        arrProduct.get(position);
    }

    public class ProductItemsHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewProduct;
        public TextView txtProductName, txtProductPrice;

        public ProductItemsHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);

            //get item in RecyclerView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                    intent.putExtra("product_details", arrProduct.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

        }
    }

}
