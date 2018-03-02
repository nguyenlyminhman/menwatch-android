package com.greenwich.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenwich.menwatch.R;
import com.greenwich.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nguye on 3/2/2018.
 */

public class BrandProductAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrBrandProduct;

    public BrandProductAdapter(Context context, ArrayList<Product> arrBrandProduct) {
        this.context = context;
        this.arrBrandProduct = arrBrandProduct;
    }

    @Override
    public int getCount() {
        return arrBrandProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return arrBrandProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        TextView txtBS_ProductName, txtBS_ProductPrice, txtBS_ProductDetails;
        ImageView ivBrandStyleProduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_brand_style_product, null);
            viewHolder.txtBS_ProductName = view.findViewById(R.id.txtBS_ProductName);
            viewHolder.txtBS_ProductPrice = view.findViewById(R.id.txtBS_ProductPrice);
            viewHolder.txtBS_ProductDetails = view.findViewById(R.id.txtBS_ProductDetails);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHolder.txtBS_ProductName.setText(product.getName());
        viewHolder.txtBS_ProductPrice.setText("$"+product.getPrice());
        viewHolder.txtBS_ProductDetails.setMaxLines(3);
        viewHolder.txtBS_ProductDetails.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtBS_ProductDetails.setText(product.getDescription());
        Picasso.with(context).load(product.getImg1())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.ivBrandStyleProduct);
        return view;
    }
}
