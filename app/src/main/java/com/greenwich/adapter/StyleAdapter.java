package com.greenwich.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.greenwich.menwatch.R;
import com.greenwich.model.Style;

import java.util.ArrayList;

/**
 * Created by nguye on 2/27/2018.
 */

public class StyleAdapter extends BaseAdapter{
    ArrayList<Style> arrStyle;
    Context context;

    public StyleAdapter(ArrayList<Style> arrStyle, Context context) {
        this.arrStyle = arrStyle;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrStyle.size();
    }

    @Override
    public Object getItem(int i) {
        return arrStyle.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView txtStyleName;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        StyleAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new StyleAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_style, null);
            viewHolder.txtStyleName = view.findViewById(R.id.txtStyleName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (StyleAdapter.ViewHolder) view.getTag();
        }
        Style style = (Style) getItem(i);
        viewHolder.txtStyleName.setText(style.getStyleName());
        return view;
    }
}
