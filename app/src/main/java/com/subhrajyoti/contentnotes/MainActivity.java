package com.subhrajyoti.contentnotes;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.subhrajyoti.contentnotes.database.NoteContract;
import com.subhrajyoti.contentnotes.recyclerview.RecyclerTouchListener;
import com.subhrajyoti.contentnotes.recyclerview.RecyclerViewAdapter;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerViewAdapter = new RecyclerViewAdapter(null);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                Cursor cursor = recyclerViewAdapter.getCursor();
                cursor.moveToPosition(position);
                int index = cursor.getColumnIndex(NoteContract.NoteEntry.NOTE_ID);
                index = cursor.getInt(index);
                Log.d("TAG", String.valueOf(index));
                String selection = NoteContract.NoteEntry.NOTE_ID + " = ?";
                getContentResolver().delete(NoteContract.NoteEntry.CONTENT_URI, selection, new String[]{String.valueOf(index)});

            }
        }));

        setSupportActionBar(toolbar);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= "title ".concat(String.valueOf(new Random().nextInt()));
                String desc = "description ".concat(String.valueOf(new Random().nextInt()));
                ContentValues values = new ContentValues();
                values.put(NoteContract.NoteEntry.COLUMN_TITLE, title);
                values.put(NoteContract.NoteEntry.COLUMN_DESCRIPTION, desc);
                getContentResolver().insert(NoteContract.NoteEntry.CONTENT_URI,values);
            }
        });

        getSupportLoaderManager().initLoader(1, null,this );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {NoteContract.NoteEntry.NOTE_ID, NoteContract.NoteEntry.COLUMN_TITLE,
                NoteContract.NoteEntry.COLUMN_DESCRIPTION};
        return new CursorLoader(MainActivity.this, NoteContract.NoteEntry.CONTENT_URI, projection, null,
                null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("Count",String.valueOf(data.getCount()));
            recyclerViewAdapter.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        recyclerViewAdapter.swapCursor(null);
    }
}
