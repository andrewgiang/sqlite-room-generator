package com.andrewgiang.roomsqlparser;

import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

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

  TypeName getAffanityType() {
    if (!constraints.contains(ColumnConstraint.NOTNULL)) {
      if (affinity.getType().isPrimitive()) {
        return affinity.getType().box();
      }
    }
    return affinity.getType();
  }

  boolean isPrimary() {
    return constraints.contains(ColumnConstraint.PRIMARYKEY);
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
