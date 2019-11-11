package com.sport.traininghelper;

import android.provider.BaseColumns;

import java.util.Calendar;
import java.util.Date;

public final class TrainingContract {
  // To prevent someone from accidentally instantiating the contract class,
  // make the constructor private.
  private String id;
  private String title;
  private Date when;
  private String warmingTime;
  private String runningTime;
  private String runningDistance;
  private String strechingTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getWhen() {
    return when;
  }

  public void setWhen(Date when) {
    this.when = when;
  }

  public String getWarmingTime() {
    return warmingTime;
  }

  public void setWarmingTime(String warmingTime) {
    this.warmingTime = warmingTime;
  }

  public String getRunningTime() {
    return runningTime;
  }

  public void setRunningTime(String runningTime) {
    this.runningTime = runningTime;
  }

  public String getRunningDistance() {
    return runningDistance;
  }

  public void setRunningDistance(String runningDistance) {
    this.runningDistance = runningDistance;
  }

  public String getStrechingTime() {
    return strechingTime;
  }

  public void setStrechingTime(String strechingTime) {
    this.strechingTime = strechingTime;
  }

  public TrainingContract() {}

  /* Inner class that defines the table contents */
  public static class TrainingEntry implements BaseColumns {
    public static final String TABLE_NAME = "training";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_WHEN = "when_time";
    public static final String COLUMN_NAME_WARM = "warming_time";
    public static final String COLUMN_NAME_RUNNING_TIME = "running_time";
    public static final String COLUMN_NAME_RUNNING_TARGET = "running_distance";
    public static final String COLUMN_NAME_STRETCHING_TIME = "streching_time";

  }
}
