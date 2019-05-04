package testUtil;

public class HelloTest {

    /**
     * asserts
     */

    public static void isTrue(boolean condition) {
        if (!condition) {
            fail("Excepted true but found " + condition);
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
