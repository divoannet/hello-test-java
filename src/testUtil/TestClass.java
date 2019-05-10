package testUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class TestClass {
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RESET = "\u001B[0m";

    Class<?> testClass;
    int passedCount = 0;
    int failedCount = 0;

    public TestClass(Class<?> testClass) {
        this.testClass = testClass;

        runClassTest(testClass);
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
                System.err.println(ANSI_RED + "Cause: " + e.getCause() + ANSI_RESET);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        output();
    }

    private void output() {
        out.println(" ");
        out.println("--------------");
        out.println("Tests finished");
        out.println(ANSI_GREEN + "passed: " + passedCount + ANSI_RESET);
        out.println(ANSI_RED + "failed: " + failedCount + ANSI_RESET);
        out.println("--------------");
        out.println(" ");
    }

    private void runTestCaseMethod(Method testCase, Object obj) {
        try {
            testCase.invoke(obj);
        } catch (AssertionError | InvocationTargetException e) {
            throw new AssertionError(e.getCause());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void runServiceMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (AssertionError | InvocationTargetException e) {
                throw new AssertionError(e.getCause());
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

}
