package com.gregoriofer.tfgbackend.soe.model;

public class SoE {

  private Integer id;
  private String type;
  private String emoji;
  private String name;
  private String part;
  private String[] location;
  private String[] description;

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

  public String getPart() {
    return part;
  }

  public void setPart(String part) {
    this.part = part;
  }

  public String[] getLocation() {
    return location;
  }

  public void setLocation(String[] location) {
    this.location = location;
  }

  public String[] getDescription() {
    return description;
  }

  public void setDescription(String[] description) {
    this.description = description;
  }
}
