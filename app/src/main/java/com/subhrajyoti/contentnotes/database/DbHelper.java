package com.subhrajyoti.contentnotes.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";
    private static final String COMMA = ",";
    private static final String SPACE = " ";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String CREATE_TABLE_NOTE = "CREATE TABLE " + NoteContract.NoteEntry.TABLE_NAME + "("
                + NoteContract.NoteEntry.NOTE_ID + SPACE + "INTEGER PRIMARY KEY" + COMMA
                + NoteContract.NoteEntry.COLUMN_TITLE + SPACE + "TEXT NOT NULL" + COMMA
                + NoteContract.NoteEntry.COLUMN_DESCRIPTION + SPACE + "TEXT"
                + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_NOTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
