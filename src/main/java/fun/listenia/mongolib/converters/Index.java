package fun.listenia.mongolib.converters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Index {
    enum Type {
        SINGLE,
        TEXT,
        _2D,
        _2DSPHERE;
    }

    Type type() default Type.SINGLE;
    boolean unique() default true;
}
