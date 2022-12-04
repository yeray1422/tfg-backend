package com.gregoriofer.tfgbackend.comments.model;

import java.util.HashMap;

public class Comments {

  private String id;
  private String name;
  private String comment;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public HashMap<String, Object> toMap() {
    HashMap<String, Object> commentHashMap = new HashMap<>();
    commentHashMap.put("id", id);
    commentHashMap.put("name", name);
    commentHashMap.put("comment", comment);

    return commentHashMap;
  }

}
