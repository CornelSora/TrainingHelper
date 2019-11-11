package com.sport.traininghelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TrainingDbHelper extends SQLiteOpenHelper {
  // If you change the database schema, you must increment the database version.
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "Training.db";

    private static final String SQL_CREATE_ENTRIES =
    "CREATE TABLE IF NOT EXISTS " + TrainingContract.TrainingEntry.TABLE_NAME + " (" +
      TrainingContract.TrainingEntry._ID + " INTEGER PRIMARY KEY," +
      TrainingContract.TrainingEntry.COLUMN_NAME_TITLE + " TEXT," +
      TrainingContract.TrainingEntry.COLUMN_NAME_WARM + " TEXT," +
      TrainingContract.TrainingEntry.COLUMN_NAME_WHEN + " TEXT," +
      TrainingContract.TrainingEntry.COLUMN_NAME_RUNNING_TARGET + " TEXT," +
      TrainingContract.TrainingEntry.COLUMN_NAME_RUNNING_TIME + " TEXT," +
      TrainingContract.TrainingEntry.COLUMN_NAME_STRETCHING_TIME + " TEXT)";

  private static final String SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS " + TrainingContract.TrainingEntry.TABLE_NAME;

  public TrainingDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_DELETE_ENTRIES);
    db.execSQL(SQL_CREATE_ENTRIES);
  }
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // This database is only a cache for online data, so its upgrade policy is
    // to simply to discard the data and start over
    db.execSQL(SQL_DELETE_ENTRIES);
    onCreate(db);
  }
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }
}
