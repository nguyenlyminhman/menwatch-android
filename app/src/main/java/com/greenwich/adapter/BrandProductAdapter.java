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
        TextView txtB_ProductName, txtB_ProductPrice, txtB_ProductDetails;
        ImageView ivBrandProduct;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_brand_product, null);
            viewHolder.txtB_ProductName = view.findViewById(R.id.txtB_ProductName);
            viewHolder.txtB_ProductPrice = view.findViewById(R.id.txtB_ProductPrice);
            viewHolder.txtB_ProductDetails = view.findViewById(R.id.txtB_ProductDetails);
            viewHolder.ivBrandProduct = view.findViewById(R.id.ivBrandProduct);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHolder.txtB_ProductName.setText(product.getName());
        viewHolder.txtB_ProductPrice.setText("$"+product.getPrice());
        viewHolder.txtB_ProductDetails.setMaxLines(3);
        viewHolder.txtB_ProductDetails.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtB_ProductDetails.setText(product.getDescription());
        Picasso.with(context).load(product.getImg1())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.ivBrandProduct);
        return view;
    }
}
