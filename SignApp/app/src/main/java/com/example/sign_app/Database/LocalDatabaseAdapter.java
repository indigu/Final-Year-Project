package com.example.sign_app.Database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sign_app.R;
import com.example.sign_app.Models.Memory;

public class LocalDatabaseAdapter extends CursorAdapter {

    public LocalDatabaseAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_local_database_list_item, viewGroup, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        Memory memory = new Memory(cursor);

        holder.titleTextView.setText(memory.getTitle());
        holder.imageView.setImageBitmap(memory.getImage());
    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView titleTextView;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.list_item_image_view);
            titleTextView = view.findViewById(R.id.list_item_text_view);
        }
    }
}