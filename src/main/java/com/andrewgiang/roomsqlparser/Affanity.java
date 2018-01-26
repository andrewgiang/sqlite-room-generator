package com.andrewgiang.roomsqlparser;

import com.squareup.javapoet.TypeName;


public enum Affanity {
  TEXT(TypeName.get(String.class)),
  NVARCHAR(TypeName.get(String.class)),
  NUMERIC(TypeName.FLOAT),
  INTEGER(TypeName.INT),
  UNKNOWN(TypeName.OBJECT);
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
    System.err.println(text + " is an unknown affanity");
    return UNKNOWN;
  }

  public TypeName getType() {
    return typeSpec;
  }
}
