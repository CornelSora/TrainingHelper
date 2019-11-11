package com.sport.traininghelper;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ItemAddActivity extends AppCompatActivity {

  final Calendar myCalendar = Calendar.getInstance();
  EditText textViewDate = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);
    Toolbar toolbar = findViewById(R.id.add_toolbar);
    setSupportActionBar(toolbar);

    // Show the Up button in the action bar.
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    textViewDate = findViewById(R.id.text_date_training);
    textViewDate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        new DatePickerDialog(ItemAddActivity.this, date, myCalendar
          .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
          myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

    textViewDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) return;
        new DatePickerDialog(ItemAddActivity.this, date, myCalendar
          .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
          myCalendar.get(Calendar.DAY_OF_MONTH)).show();
      }
    });

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_item);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        EditText titleText = findViewById(R.id.text_training_title);
        EditText dateText = findViewById(R.id.text_date_training);
        EditText warmText = findViewById(R.id.text_warm);
        EditText runningTimeText = findViewById(R.id.text_running_duration);
        EditText runningDistanceText = findViewById(R.id.text_running_distance);
        EditText stretchingTimeText = findViewById(R.id.text_stretching);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String title = titleText.getText() != null ? titleText.getText().toString() : "";
        String date = dateText.getText() != null ? dateText.getText().toString() : "";
        String warmingTime = warmText.getText() != null ? warmText.getText().toString() : "";
        String runningTime = runningTimeText.getText() != null ? runningTimeText.getText().toString() : "";
        String runningDistance = runningDistanceText.getText() != null ? runningDistanceText.getText().toString() : "";
        String stretching = stretchingTimeText.getText() != null ? stretchingTimeText.getText().toString() : "";

        if ("".equals(title.trim())) {
          titleText.setError("Title is required");
          titleText.findFocus();
          return;
        }

        TrainingContract trainingContract = new TrainingContract();
        trainingContract.setTitle(title);
        try {
          trainingContract.setWhen(dateFormat.parse(date));
        } catch (ParseException e) {
        }
        trainingContract.setWarmingTime(warmingTime);
        trainingContract.setRunningTime(runningTime);
        trainingContract.setRunningDistance(runningDistance);
        trainingContract.setStrechingTime(stretching);

        TrainingMethods.getInstance().insert(trainingContract);

        finish();
      }
    });
  }

  DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,
                          int dayOfMonth) {
      // TODO Auto-generated method stub
      myCalendar.set(Calendar.YEAR, year);
      myCalendar.set(Calendar.MONTH, monthOfYear);
      myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
      updateLabel();
    }

  };

  private void updateLabel() {
    String myFormat = "yyyy-MM-dd"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

    textViewDate.setText(sdf.format(myCalendar.getTime()));
  }
}
