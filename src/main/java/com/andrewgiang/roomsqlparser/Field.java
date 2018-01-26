package com.andrewgiang.roomsqlparser;

import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import static com.andrewgiang.roomsqlparser.CaseFormatUtil.ALPHA_NUMERIC_UNDERSCORE_REGEX;
import static com.google.common.base.Preconditions.checkNotNull;

class Field {
  private Field(String columnName, Affanity affinity, List<ColumnConstraint> constraints) {
    this.columnName = columnName;
    this.affinity = affinity;
    this.constraints = constraints;
  }

  private String columnName;
  private Affanity affinity;
  private List<ColumnConstraint> constraints;

  String getColumnName() {
    return columnName;
  }

  String getColumnNameAlphaNumeric(){
    return columnName.replaceAll(ALPHA_NUMERIC_UNDERSCORE_REGEX, "");
  }

  TypeName getAffanityType() {
    if (!constraints.contains(ColumnConstraint.NOTNULL)) {
      if (affinity.getType().isPrimitive()) {
        return affinity.getType().box();
      }
    }
    return affinity.getType();
  }

  boolean isPrimaryKey() {
    return constraints.contains(ColumnConstraint.PRIMARYKEY);
  }

  boolean isAutoIncrementPrimary() {
    return constraints.contains(ColumnConstraint.PRIMARYKEYAUTOINCREMENT);
  }


  static class Builder {
    private String columnName;
    private Affanity affinity;
    private List<ColumnConstraint> constraints = new ArrayList<>();

    Builder setColumnName(String columnName) {
      this.columnName = columnName;
      return this;
    }

    Builder setAffinity(Affanity affinity) {
      this.affinity = affinity;
      return this;
    }

    Field build() {
      checkNotNull(columnName);
      checkNotNull(affinity);
      return new Field(columnName, affinity, constraints);
    }

    Builder addConstraint(ColumnConstraint constraint) {
      if (constraint != null) {
        constraints.add(constraint);
      }
      return this;
    }
  }
}
