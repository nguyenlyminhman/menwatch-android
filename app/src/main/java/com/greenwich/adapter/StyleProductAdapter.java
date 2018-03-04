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
 * Created by nguye on 3/4/2018.
 */

public class StyleProductAdapter extends BaseAdapter {

    ArrayList<Product> arrStyleProduct;
    Context context;

    public StyleProductAdapter(Context context, ArrayList<Product> arrStyle) {
        this.arrStyleProduct = arrStyle;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrStyleProduct.size();
    }

    @Override
    public Object getItem(int i) {
        return arrStyleProduct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class ViewHolder {
        TextView txtS_ProductName, txtS_ProductPrice, txtS_ProductDetails;
        ImageView ivStyleProduct;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        StyleProductAdapter.ViewHolder viewHolder = null;

        if(view == null){
            viewHolder = new StyleProductAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_style_product, null);
            viewHolder.txtS_ProductName = view.findViewById(R.id.txtS_ProductName);
            viewHolder.txtS_ProductPrice = view.findViewById(R.id.txtS_ProductPrice);
            viewHolder.txtS_ProductDetails = view.findViewById(R.id.txtS_ProductDetails);
            viewHolder.ivStyleProduct = view.findViewById(R.id.ivStyleProduct);
            view.setTag(viewHolder);
        }else{
            viewHolder = (StyleProductAdapter.ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(i);
        viewHolder.txtS_ProductName.setText(product.getName());
        viewHolder.txtS_ProductPrice.setText("$"+product.getPrice());
        viewHolder.txtS_ProductDetails.setMaxLines(3);
        viewHolder.txtS_ProductDetails.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtS_ProductDetails.setText(product.getDescription());
        Picasso.with(context).load(product.getImg1())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.ivStyleProduct);
        return view;
    }
}
