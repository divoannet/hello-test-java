package testUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

public class HelloRunner {
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        new HelloRunner().run(args);
    }

    void run(String... args) {
        out.println("Start tests");
        out.println(" ");

        int streamCount = Integer.parseInt(args[0]);
        List<Class<?>> testedClasses = getClasses(Arrays.copyOfRange(args, 1, args.length));

        new RunTest(testedClasses, streamCount);
    }


    private List<Class<?>> getClasses(String... args) {
        List<Class<?>> testedClasses = new LinkedList<>();

        for (String arg : args) {
            try {
                testedClasses.add(getClass(arg));
            } catch (ClassNotFoundException e) {
                out.println(ANSI_RED + "There is no such class as " + arg + ANSI_RESET);
            }
        }

        return testedClasses;
    }

    private Class<?> getClass(String arg) throws ClassNotFoundException {
        return Class.forName(arg);
    }
}
