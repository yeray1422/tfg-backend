package com.gregoriofer.tfgbackend.soe.model;

import java.util.Map;

public class SoE {

  private Integer id;
  private String name;
  private Map<String, Map<String, String>> parts;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Map<String, String>> getParts() {
    return parts;
  }

  public void setParts(Map<String, Map<String, String>> parts) {
    this.parts = parts;
  }

}
