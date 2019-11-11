package com.sport.traininghelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrainingMethods {

  private static final TrainingMethods INSTANCE = new TrainingMethods();

  private TrainingMethods() {
  }

  public static TrainingMethods getInstance() {
    return INSTANCE;
  }

  public int insert(TrainingContract trainingContract)
  {
    int code=0;
    try {

      SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
      ContentValues values = new ContentValues();
      values.put(TrainingContract.TrainingEntry.COLUMN_NAME_TITLE, trainingContract.getTitle());
      values.put(TrainingContract.TrainingEntry.COLUMN_NAME_RUNNING_TARGET, trainingContract.getRunningDistance());
      values.put(TrainingContract.TrainingEntry.COLUMN_NAME_RUNNING_TIME, trainingContract.getRunningTime());
      values.put(TrainingContract.TrainingEntry.COLUMN_NAME_STRETCHING_TIME, trainingContract.getStrechingTime());
      values.put(TrainingContract.TrainingEntry.COLUMN_NAME_WARM, trainingContract.getWarmingTime());
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      values.put(TrainingContract.TrainingEntry.COLUMN_NAME_WHEN, dateFormat.format(trainingContract.getWhen()));

      // Inserting Row
      code = (int) db.insert(TrainingContract.TrainingEntry.TABLE_NAME, null,values);
      DatabaseManager.getInstance().closeDatabase();


    }
    catch(Exception e) {
      System.out.println(e.toString());
    }
    return code;
  }


  public List<TrainingContract> select() throws ParseException {
    List<TrainingContract> trainings = new ArrayList<>();
    TrainingContract trainingContract;
    SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
    String selectQuery = " SELECT * FROM " + TrainingContract.TrainingEntry.TABLE_NAME;

    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
      do {
        trainingContract = new TrainingContract();
        trainingContract.setId(cursor.getString(cursor.getColumnIndex(BaseColumns._ID)));
        trainingContract.setTitle(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NAME_TITLE)));
        trainingContract.setRunningDistance(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NAME_RUNNING_TARGET)));
        trainingContract.setRunningTime(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NAME_RUNNING_TIME)));
        trainingContract.setStrechingTime(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NAME_STRETCHING_TIME)));
        trainingContract.setWarmingTime(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NAME_WARM)));
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        trainingContract.setWhen(dateFormat.parse(cursor.getString(cursor.getColumnIndex(TrainingContract.TrainingEntry.COLUMN_NAME_WHEN))));

        trainings.add(trainingContract);
      } while (cursor.moveToNext());
    }


    cursor.close();
    DatabaseManager.getInstance().closeDatabase();

    return trainings;
  }

}
