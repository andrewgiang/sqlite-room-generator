package com.andrewgiang.roomsqlparser;

public enum ColumnConstraint {
  PRIMARYKEY,
  NOTNULL;

  public static ColumnConstraint from(String text) {
    for (ColumnConstraint constraint : ColumnConstraint.values()) {
      if(constraint.name().equalsIgnoreCase(text)){
        return constraint;
      }
    }
    return null;
  }
}
