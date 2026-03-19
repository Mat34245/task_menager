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

    public DropdownAdapter(Integer[] icons, Context context) {
        super(context, 0, icons);
        this.icons = icons;
    }

    @Override
    public void onClick(View v) { }

    View dropDownView;
    Integer current;
    ImageView iconImg;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item, parent, false);
        }

        onInitialize(position, currentItemView);

        return currentItemView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        dropDownView = convertView;

        if (dropDownView == null) {
            dropDownView = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_item, parent, false);
        }

        onInitialize(position, dropDownView);

        return dropDownView;
    }

    public void onInitialize(int position, View currentItemView) {
        current = icons[position];
        iconImg = currentItemView.findViewById(R.id.iconImg);

        iconImg.setImageResource(current);
        iconImg.setColorFilter(Color.parseColor("#0F2854"));
    }
}
