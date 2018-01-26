package com.andrewgiang.roomsqlparser;

public enum ColumnConstraint {
  PRIMARYKEY,
  NOTNULL;

  public static ColumnConstraint from(String constraintText) {
    try {
      return ColumnConstraint.valueOf(constraintText);
    } catch (Exception e) {
      return null;
    }
  }
}
