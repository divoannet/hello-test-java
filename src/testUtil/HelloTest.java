package testUtil;

public class HelloTest {

    public static void main(String[] args) {
        System.out.println(Before.class);
    }

    protected static void isTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
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
