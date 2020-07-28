package com.codeunite.paymyrch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;

public class AdminCustomGrid extends BaseAdapter {
    Context context;
    int[] imageId;
    String[] titileGrid;

    public AdminCustomGrid(Context context, int[] imageId, String[] titleGrid) {
        this.context = context;
        this.imageId = imageId;
        this.titileGrid = titleGrid;
    }

    public int getCount() {
        return this.imageId.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("WrongConstant") LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        if (convertView != null) {
            return convertView;
        }
        View view = new View(this.context);
        view = inflater.inflate(R.layout.admin_custom_grid, parent, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.grid_image);
        TextView textView = (TextView) view.findViewById(R.id.grid_text);
        textView.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "fonts/fontregular.ttf"));
        imageView.setImageResource(this.imageId[position]);
        textView.setText(this.titileGrid[position]);
        return view;
    }
}
