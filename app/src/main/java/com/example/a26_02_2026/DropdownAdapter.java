package com.example.a26_02_2026;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DropdownAdapter extends ArrayAdapter<Integer> implements View.OnClickListener{

    private  Integer[] icons;
    private ImageView icon;
    public DropdownAdapter(Integer[] icons, Context context) {
        super(context, 0, icons);
        this.icons = icons;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item, parent, false);
        }

        Integer current = icons[position];
        icon = currentItemView.findViewById(R.id.iconImg);
        icon.setColorFilter(Color.parseColor("#0F2854"));

        icon.setImageResource(current);

        return currentItemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View dropDownView = convertView;

        if (dropDownView == null) {
            dropDownView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item, parent, false);
        }

        Integer current = icons[position];
        ImageView icon = dropDownView.findViewById(R.id.iconImg);
        icon.setImageResource(current);
        icon.setColorFilter(Color.parseColor("#0F2854"));

        return dropDownView;
    }


    @Override
    public void onClick(View v) {

    }
}
