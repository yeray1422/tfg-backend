package com.gregoriofer.tfgbackend.soe.model;

public class SoE {

  private Integer id;
  private String name;
  private String partNumber;
  private String locationImage;

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

  public String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(String partNumber) {
    this.partNumber = partNumber;
  }

  public String getLocationImage() {
    return locationImage;
  }

  public void setLocationImage(String locationImage) {
    this.locationImage = locationImage;
  }
}
