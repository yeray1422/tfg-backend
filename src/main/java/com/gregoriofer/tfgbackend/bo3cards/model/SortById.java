package com.gregoriofer.tfgbackend.bo3cards.model;

import java.util.Comparator;

public class SortById implements Comparator<BO3Cards> {

  @Override
  public int compare(BO3Cards b1, BO3Cards b2) {
    return b1.getId().compareTo(b2.getId());
  }
}
