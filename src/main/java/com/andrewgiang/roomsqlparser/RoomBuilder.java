package com.andrewgiang.roomsqlparser;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

class RoomBuilder {
  private String tableName;

  private List<Field> fieldList = new ArrayList<>();

  void tableName(String text) {
    tableName = text;
  }

  void addField(Field field) {
    fieldList.add(field);
  }


  JavaFile build(String modelPackage) {
    return JavaFile.builder(modelPackage, buildTypeSpec()).build();
  }

  private TypeSpec buildTypeSpec() {
    checkNotNull(tableName);
    TypeSpec.Builder builder = TypeSpec
        .classBuilder(CaseFormatUtil.convertTo(CaseFormat.UPPER_CAMEL, tableName))
        .addModifiers(Modifier.PUBLIC)
        .addAnnotation(buildTableAnnotationSpec(tableName));
    for (Field field : fieldList) {
      builder.addField(buildFieldSpec(field));
    }

    return builder.build();
  }


  private FieldSpec buildFieldSpec(Field field) {
    FieldSpec.Builder builder = FieldSpec.builder(
        field.getAffanityType(),
        CaseFormatUtil.convertTo(CaseFormat.LOWER_CAMEL, field.getColumnName()),
        Modifier.PUBLIC
    ).addAnnotation(buildColumnAnnotationSpec(field.getColumnName()));

    if (field.isPrimary()) {
      builder.addAnnotation(ClassName.get("android.arch.persistence.room", "PrimaryKey"));
    }

    return builder.build();
  }

  private AnnotationSpec buildColumnAnnotationSpec(String columnName) {
    return AnnotationSpec.builder(ClassName.get("android.arch.persistence.room", "ColumnInfo"))
        .addMember("name", "\"" + columnName + "\"").build();
  }

  private AnnotationSpec buildTableAnnotationSpec(String tableName) {
    return AnnotationSpec.builder(ClassName.get("android.arch.persistence.room", "Entity"))
        .addMember("tableName", "\"" + tableName + "\"").build();
  }
}
