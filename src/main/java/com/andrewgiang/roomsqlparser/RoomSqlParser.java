package com.andrewgiang.roomsqlparser;

import com.squareup.javapoet.JavaFile;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

public class RoomSqlParser {


  public RoomSqlParser() {
  }

  public List<JavaFile> parseSql(CharStream stream, String javaModelPackage) {
    SQLiteLexer lexer = new SQLiteLexer(stream);
    CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
    SQLiteParser parser = new SQLiteParser(commonTokenStream);
    List<JavaFile> javaFiles = new ArrayList<>();
    ParseTreeWalker.DEFAULT.walk(new SQLiteBaseListener() {
      RoomBuilder builder;

      @Override
      public void enterCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        builder = new RoomBuilder();
      }

      @Override
      public void exitCreate_table_stmt(SQLiteParser.Create_table_stmtContext ctx) {
        javaFiles.add(builder.build(javaModelPackage));
      }

      @Override
      public void exitColumn_def(SQLiteParser.Column_defContext ctx) {
        Field.Builder builder = new Field.Builder();
        builder.setColumnName(ctx.column_name().getText())
            .setAffinity(Affanity.from(ctx.type_name().start.getText()));
        for (SQLiteParser.Column_constraintContext constraintContext : ctx.column_constraint()) {
          builder.addConstraint(ColumnConstraint.from(constraintContext.getText()));
        }
        this.builder.addField(builder.build());
      }

      @Override
      public void enterTable_name(SQLiteParser.Table_nameContext ctx) {
        builder.tableName(ctx.getText());
      }

    }, parser.parse());

    return javaFiles;
  }
}
