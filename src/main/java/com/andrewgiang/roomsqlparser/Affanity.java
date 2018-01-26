package com.andrewgiang.roomsqlparser;

import com.squareup.javapoet.TypeName;


public enum Affanity {
  TEXT(TypeName.get(String.class)),
  NUMERIC(TypeName.FLOAT),
  INTEGER(TypeName.INT);

  private TypeName typeSpec;

  Affanity(TypeName typeName) {
    this.typeSpec = typeName;
  }

  public static Affanity from(String text) {
    for (Affanity affanity : Affanity.values()) {
      if (text.equalsIgnoreCase(affanity.name())) {
        return affanity;
      }
    }
    return null;
  }

  public TypeName getType() {
    return typeSpec;
  }
}
