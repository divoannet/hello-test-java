package testUtil;

public class Asserts {

    /**
     * asserts
     */

    public static void isTrue(boolean condition) {
        if (!condition) {
            fail("Excepted true but found " + condition);
        }
    }

    public static void isFalse(boolean condition) {
        if (condition) {
            fail("Excepted false but found " + condition);
        }
    }

    public static void isNull(Object obj) {
        if (obj != null) {
            fail("Excepted null but found " + obj.getClass());
        }
    }

    public static void isNotNull(Object obj) {
        if (obj == null) {
            fail("Excepted not null but found " + obj.getClass());
        }
    }

    public static void isEqual(Object first, Object second) {
        if (first == null || second == null) {
            fail("One of parameters is null");
            return;
        }

        if (!first.equals(second)) {
            fail("Expected [" + first.toString() + "] but found [" + second.toString() + "]");
        }
    }

    private static void fail(String message) {
        String str = message != null ?  message : "Error";
        throw new AssertionError(str);
    }

    public static void fail() {
        fail(null);
    }

}
