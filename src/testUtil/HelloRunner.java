package testUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static java.lang.System.out;

public class HelloRunner {
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RESET = "\u001B[0m";

    int passedCount = 0;
    int failedCount = 0;

    public static void main(String[] args) {
        Object result = new HelloRunner().run(args);
    }

    Object run(String... args) {
        out.println("Start tests");
        out.println(" ");

        List<Class<?>> testedClasses = getClasses(args);

        for (Class<? extends Object> testedClass : testedClasses) {
            runClassTest(testedClass);
        }

        return null;
    }

    private void runClassTest(Class<? extends Object> testedClass) {
        List<Method> testCases = getAnnotatedMethods(testedClass, TestCase.class); // TODO: run in multiply streams
        List<Method> befores = getAnnotatedMethods(testedClass, Before.class);
        List<Method> afters = getAnnotatedMethods(testedClass, After.class);

        for (Method testCase : testCases) {
            try {
                var c = testedClass.getDeclaredConstructor().newInstance();

                runServiceMethods(befores, c);
                runTestCaseMethod(testCase, c);
                runServiceMethods(afters, c);

                passedCount += 1;
                System.err.println(ANSI_GREEN + "+ " +  testCase + ANSI_RESET);
            } catch (AssertionError | InvocationTargetException e) {
                failedCount += 1;
                System.err.println(ANSI_RED + "- " +  testCase + ANSI_RESET);
                System.err.println(ANSI_RED + "Cause: " + e.toString() + ANSI_RESET); // TODO: output error message
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        out.println(" ");
        out.println("--------------");
        out.println("Tests finished");
        out.println(ANSI_GREEN + "passed: " + passedCount + ANSI_RESET);
        out.println(ANSI_RED + "failed: " + failedCount + ANSI_RESET);
    }

    private void runTestCaseMethod(Method testCase, Object obj) {
        try {
            testCase.invoke(obj);
        } catch (AssertionError | InvocationTargetException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void runServiceMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (AssertionError | InvocationTargetException e) {
                throw new AssertionError(e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Method> getAnnotatedMethods(Class<? extends Object> testedClass, Class<? extends Annotation> annotation) {
        List<Method> result = new ArrayList<Method>();

        Method[] methods = testedClass.getMethods();

        for (Method method : methods) {
            Annotation a = method.getAnnotation(annotation);
            if (a != null) {
                result.add(method);
            }
        }

        return result;
    }

    private List<Class<?>> getClasses(String... args) {
        List<Class<?>> testedClasses = new ArrayList<Class<?>>();

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
