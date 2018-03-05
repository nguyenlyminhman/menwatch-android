package com.greenwich.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenwich.menwatch.R;
import com.greenwich.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nguye on 3/5/2018.
 */

public class CartAdapter extends BaseAdapter {

    Context context;
    ArrayList<Cart> arrCart;

    public CartAdapter(Context context, ArrayList<Cart> arrCart) {
        this.context = context;
        this.arrCart = arrCart;
    }

    @Override
    public int getCount() {
        return arrCart.size();
    }

    @Override
    public Object getItem(int i) {
        return arrCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    public class ViewHolder {
        TextView txtCartItemName, txtCartItemPrice;
        EditText txtCartItemQuantity;
        ImageButton btnCartItemUpdate, btnCartItemRemove;
        ImageView ivCartItemImage;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_cart, null);
            viewHolder.txtCartItemName = view.findViewById(R.id.txtCartItemName);
            viewHolder.txtCartItemPrice = view.findViewById(R.id.txtCartItemPrice);
            viewHolder.txtCartItemQuantity = view.findViewById(R.id.txtCartItemQuantity);
            viewHolder.btnCartItemUpdate = view.findViewById(R.id.btnCartItemUpdate);
            viewHolder.btnCartItemRemove = view.findViewById(R.id.btnCartItemRemove);
            viewHolder.ivCartItemImage = view.findViewById(R.id.ivCartItemImage);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart objCart = (Cart) getItem(i);
        viewHolder.txtCartItemName.setText(objCart.getProductName());
        viewHolder.txtCartItemPrice.setText(" $"+objCart.getProductPrice());
        viewHolder.txtCartItemQuantity.setText(objCart.getProductQuantity()+"");
        Picasso.with(context).load(objCart.getProductImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error_image)
                .into(viewHolder.ivCartItemImage);
        return view;
    }
}
