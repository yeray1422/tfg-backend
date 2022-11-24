package com.gregoriofer.tfgbackend.soe.model;

public class SoE {

  private Integer id;
  private String type;
  private String emoji;
  private String name;
  private String usage;
  private String[] pieces;
  private String[][] locations;
  private String[][] descriptions;

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

  public String[] getPieces() {
    return pieces;
  }

  public void setPieces(String[] pieces) {
    this.pieces = pieces;
  }

  public String[][] getLocations() {
    return locations;
  }

  public void setLocations(String[][] locations) {
    this.locations = locations;
  }

  public String[][] getDescriptions() {
    return descriptions;
  }

  public void setDescriptions(String[][] descriptions) {
    this.descriptions = descriptions;
  }
}
