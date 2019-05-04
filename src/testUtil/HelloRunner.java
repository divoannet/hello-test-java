package testUtil;

import java.util.ArrayList;
import java.util.List;

public class HelloRunner {
    private String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        Object result = new HelloRunner().run(args);
    }

    Object run(String... args) {
        System.out.println("Start");
        System.out.println(" ");

        List<Class<?>> testedClasses = new ArrayList<Class<?>>();

        for (String arg : args) {
            try {
                testedClasses.add(getClass(arg));
            } catch (ClassNotFoundException e) {
                System.out.println(ANSI_RED + "There is no such class as " + arg + ANSI_RESET);
            }
        }

        return null;
    }

    private Class<?> getClass(String arg) throws ClassNotFoundException {
        return Class.forName(arg);
    }
}
