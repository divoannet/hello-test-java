package testUtil;

public class HelloTest {

    /**
     * asserts
     */

    protected static void isTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    /* TODO: isFalse */
    /* TODO: isNull */
    /* TODO: isNotNull */
    /* TODO: isEqual */
    /* TODO: isNotEqual */

    private static void fail(String message) {
        String str = message != null ?  message : "Error";
        throw new AssertionError(str);
    }

    public static void fail() {
        fail(null);
    }

}
