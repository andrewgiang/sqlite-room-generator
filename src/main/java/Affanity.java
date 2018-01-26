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
        try {
            return Affanity.valueOf(text.toUpperCase());
        }catch (Exception e){
            return null;
        }
    }

    public TypeName getType() {
        return typeSpec;
    }
}
