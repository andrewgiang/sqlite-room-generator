import com.squareup.javapoet.JavaFile;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLiteParserTest {


    public static final String JAVA_MODEL_PACKAGE = "com.andrewgiang.model";
    private RoomSqlParser sqlParser;

    @Before
    public void setUp() throws Exception {
        sqlParser = new RoomSqlParser();
    }

    @Test
    public void simple_table_no_column_constraints() throws IOException {
        String expectedClass = "package com.andrewgiang.model;\n" +
                "\n" +
                "import android.arch.persistence.room.ColumnInfo;\n" +
                "import android.arch.persistence.room.Entity;\n" +
                "import java.lang.Integer;\n" +
                "\n" +
                "@Entity(\n" +
                "    tableName = \"groups\"\n" +
                ")\n" +
                "public class Groups {\n" +
                "  @ColumnInfo(\n" +
                "      name = \"group_id\"\n" +
                "  )\n" +
                "  public Integer groupId;\n" +
                "}\n";

        ANTLRInputStream stringStream = new ANTLRInputStream("CREATE TABLE groups ( " +
                " group_id integer" +
                ");");

        List<JavaFile> javaFiles = sqlParser.parseSql(stringStream, JAVA_MODEL_PACKAGE);
        assertTrue(javaFiles.size() == 1);

        assertEquals(expectedClass, javaFiles.get(0).toString());
    }


    @Test
    public void table_with_primary_key_non_null_column(){
        String expectedClass = "package com.andrewgiang.model;\n" +
                "\n" +
                "import android.arch.persistence.room.ColumnInfo;\n" +
                "import android.arch.persistence.room.Entity;\n" +
                "import android.arch.persistence.room.PrimaryKey;\n" +
                "\n" +
                "@Entity(\n" +
                "    tableName = \"groups\"\n" +
                ")\n" +
                "public class Groups {\n" +
                "  @ColumnInfo(\n" +
                "      name = \"group_id\"\n" +
                "  )\n" +
                "  @PrimaryKey\n"+
                "  public int groupId;\n" +
                "}\n";

        ANTLRInputStream stringStream = new ANTLRInputStream("CREATE TABLE groups ( " +
                " group_id integer PRIMARY KEY NOT NULL" +
                ");");
        List<JavaFile> javaFiles = sqlParser.parseSql(stringStream, JAVA_MODEL_PACKAGE);
        assertTrue(javaFiles.size() == 1);
        assertEquals(expectedClass, javaFiles.get(0).toString());

    }

}
