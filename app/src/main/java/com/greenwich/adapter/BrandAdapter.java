package com.greenwich.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greenwich.menwatch.R;
import com.greenwich.model.Brand;

import java.util.ArrayList;

/**
 * Created by nguye on 2/25/2018.
 */

public class BrandAdapter extends BaseAdapter {

    ArrayList<Brand> arrBrand;
    Context context;

    public BrandAdapter(ArrayList<Brand> arrBrand, Context context) {
        this.arrBrand = arrBrand;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrBrand.size();
    }

    @Override
    public Object getItem(int i) {
        return arrBrand.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtBrandName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_brand, null);
            viewHolder.txtBrandName = view.findViewById(R.id.txtBrandName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Brand brand = (Brand) getItem(i);
        viewHolder.txtBrandName.setText(brand.getBrandName());
        return view;
    }
}
