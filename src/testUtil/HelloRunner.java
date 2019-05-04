package testUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class HelloRunner {
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RESET = "\u001B[0m";

    List<Object> errors = new ArrayList<Object>();

    public static void main(String[] args) {
        Object result = new HelloRunner().run(args);
    }

    Object run(String... args) {
        System.out.println("Start tests");
        System.out.println(" ");

        List<Class<?>> testedClasses = getClasses(args);

        for (Class<? extends Object> testedClass : testedClasses) {
            runClassTest(testedClass);
        }

        return null;
    }

    private void runClassTest(Class<? extends Object> testedClass) {
        List<Method> testCases = getAnnotatedMethods(testedClass, TestCase.class);
        List<Method> befores = getAnnotatedMethods(testedClass, Before.class);
        List<Method> afters = getAnnotatedMethods(testedClass, After.class);

        try {
            for (Method testCase : testCases) {

                var c = testedClass.getDeclaredConstructor().newInstance();

                runServiceMethods(befores, c);
                runTestCaseMethod(testCase, c);
                runServiceMethods(afters, c);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void runTestCaseMethod(Method testCase, Object obj) {
        try {
            testCase.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void runServiceMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
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
                System.out.println(ANSI_RED + "There is no such class as " + arg + ANSI_RESET);
            }
        }

        return testedClasses;
    }

    private Class<?> getClass(String arg) throws ClassNotFoundException {
        return Class.forName(arg);
    }
}
