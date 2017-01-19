package com.subhrajyoti.contentnotes.recyclerview;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.subhrajyoti.contentnotes.R;
import com.subhrajyoti.contentnotes.database.NoteContract;
import com.subhrajyoti.contentnotes.utils.CursorRecyclerAdapter;



public class RecyclerViewAdapter extends CursorRecyclerAdapter<MainViewHolder> {



    public RecyclerViewAdapter(Cursor c) {
        super(c);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new MainViewHolder(v);
    }


    @Override
    public void onBindViewHolder(MainViewHolder holder, Cursor cursor) {
        holder.title.setText(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_TITLE)));
        holder.desc.setText(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.COLUMN_DESCRIPTION)));

    }

}