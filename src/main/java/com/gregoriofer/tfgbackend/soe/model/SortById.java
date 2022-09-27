package com.gregoriofer.tfgbackend.soe.model;

import java.util.Comparator;

public class SortById implements Comparator<SoE> {
  
  @Override
  public int compare(SoE soe1, SoE soe2) {
    return soe1.getId().compareTo(soe2.getId());
  }
}
