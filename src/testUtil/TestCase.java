package testUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface TestCase {

    static class DefaultClass extends Throwable {}

    Class<? extends Throwable> expected() default DefaultClass.class;

}
