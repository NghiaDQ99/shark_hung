package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.models.Position;

import java.util.ArrayList;

public class PositionSpinnerAdapter extends ArrayAdapter<Position> {
    private ArrayList<Position> positions;
    private Context mContext;

    public PositionSpinnerAdapter(Context context, ArrayList<Position> positions) {
        super(context, android.R.layout.simple_spinner_item, positions);
        this.positions = positions;
        this.mContext = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(mContext).inflate(R.layout.position_spinner_item, parent, false);
        }

        TextView textView = itemView.findViewById(android.R.id.text1);
        textView.setText(positions.get(position).getName());

        return itemView;
    }
}
