package com.example.abakpresstest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abakpresstest.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private final Context context;
    LayoutInflater layoutInflater;

    private ArrayList<Product> products = new ArrayList<Product>();

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.products = products;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Product product = products.get(i);
        View view = layoutInflater.inflate(R.layout.product, viewGroup, false);
        if (view == null)
            return null;
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(product.getName());
        TextView imagesCnt = (TextView) view.findViewById(R.id.images_cnt);
        imagesCnt.setText(product.getImagesCnt() + " фото");

        ImageView icon = (ImageView) view.findViewById(R.id.image);
        String image = product.getImages().get(0).getPathThumb();
        if ( image != null && !image.equals("")) {
            new ThumbTask().execute(new ImageViewHolder(icon, image, context));
        } else {
            icon.setVisibility(View.GONE);
        }
        return view;
    }
}
