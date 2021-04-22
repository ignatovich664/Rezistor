package com.example.rezistor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Адаптер для списка цветовых полос маркировки резистора
 * @author Ignatovich
 */
public class CustomArrayAdapter extends ArrayAdapter<LineColor> {

    private LineColor[] objects;
    private Context context;

    public CustomArrayAdapter(Context context, int resourceId, LineColor[] objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView label = (TextView) row.findViewById(R.id.spItem);
        label.setText(objects[position].name);
        View col = (View) row.findViewById(R.id.spColor);
        col.setBackgroundColor(objects[position].color);
        return row;
    }
}
