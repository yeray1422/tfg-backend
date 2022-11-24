package com.gregoriofer.tfgbackend.soe.model;

import java.util.Map;

public class SoE {

  private Integer id;
  private String type;
  private String emoji;
  private String name;
  private String usage;
  private Map<String, Map<String, Map<String, String>>> data;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEmoji() {
    return emoji;
  }

  public void setEmoji(String emoji) {
    this.emoji = emoji;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsage() {
    return usage;
  }

  public void setUsage(String usage) {
    this.usage = usage;
  }

  public Map<String, Map<String, Map<String, String>>> getData() {
    return data;
  }

  public void setData(Map<String, Map<String, Map<String, String>>> data) {
    this.data = data;
  }
}
