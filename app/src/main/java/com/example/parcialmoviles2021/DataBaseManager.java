package com.example.parcialmoviles2021;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "parcialMoviles";
    private static final int DATABASE_VERSION = 1;

    private static final String MOVIE_TABLE = "movie";

    private static final String MOVIE_ID = "id";
    private static final String MOVIE_NAME = "name";
    private static final String DESCRIPTION = "description";

    private static final String ERROR_TAG = "ERROR";

    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + MOVIE_TABLE +
                "(" +
                MOVIE_ID + " INTEGER PRIMARY KEY," +
                MOVIE_NAME + " TEXT," +
                DESCRIPTION + " TEXT" +
                ")";

        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
            onCreate(db);
        }
    }

    public void addMovie(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(MOVIE_NAME, movie.getName());
            values.put(DESCRIPTION, movie.getDescription());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(MOVIE_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(ERROR_TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Movie> getAllMovies() {

        List<Movie> movies = new ArrayList<>();

        String POSTS_SELECT_QUERY = String.format("SELECT * FROM %s", MOVIE_TABLE);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {

                    Movie newMovie = new Movie();
                    newMovie.setName(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_NAME)));
                    newMovie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                    movies.add(newMovie);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(ERROR_TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return movies;
    }

}