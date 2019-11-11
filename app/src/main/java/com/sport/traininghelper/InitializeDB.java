package com.sport.traininghelper;

import android.app.Application;
import android.content.Context;

public class InitializeDB extends Application {

  static Context context;
  static TrainingDbHelper dbHelper;



  @Override
  public void onCreate() {
    super.onCreate();
    context = this.getApplicationContext();
    dbHelper = new TrainingDbHelper(context);
    DatabaseManager.initializeInstance(dbHelper);

  }

}
