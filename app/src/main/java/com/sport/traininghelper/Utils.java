package com.sport.traininghelper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Utils {
  private static List<TrainingContract> trainingContractList;

  public static List<TrainingContract> getTrainingContractList() {
    if (trainingContractList == null) {
      trainingContractList = new ArrayList<>();
    }
    return  trainingContractList;
  }

  public static void setTrainingContractList(List<TrainingContract> trainingContracts) {
    trainingContractList = new ArrayList<>();
    trainingContractList.addAll(trainingContracts);
  }

  public static TrainingContract getTrainingById(int id) {
    for(int i = 0; i < trainingContractList.size(); i++) {
      final TrainingContract trainingContract = trainingContractList.get(i);
      if (trainingContract.getId() == (int)id) {
        return trainingContract;
      }
    }
    return null;
  }
}
