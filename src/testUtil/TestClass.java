package testUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class TestClass {
    Class<?> testClass;
    int passedCount = 0;
    int failedCount = 0;

    private Result[] result;

    public TestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    public Result[] run() {
        List<Method> testCases = getAnnotatedMethods(testClass, TestCase.class);
        List<Method> befores = getAnnotatedMethods(testClass, Before.class);
        List<Method> afters = getAnnotatedMethods(testClass, After.class);

        this.result = new Result[testCases.size()];

        int i = 0;
        for (Method testCase : testCases) {
            try {
                var c = testClass.getDeclaredConstructor().newInstance();

                result[i] = new Result(testCase.getName());

                runServiceMethods(befores, c);
                runTestCaseMethod(testCase, c, i);
                runServiceMethods(afters, c);

                passedCount += 1;
            } catch (AssertionError | InvocationTargetException e) {
                failedCount += 1;
                result[i].setError(e.getCause().toString());
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            i++;
        }

        return result;
    }

    private void runTestCaseMethod(Method testCase, Object obj, int i) {
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
