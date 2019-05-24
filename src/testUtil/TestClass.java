package testUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    Class<?> testClass;
    int passedCount = 0;
    int failedCount = 0;

    private Result[] result;
    private List<TCase> testcases;

    public TestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    public Result[] run() {
        testcases = getTestCases(testClass);
        List<Method> befores = getAnnotatedMethods(testClass, Before.class);
        List<Method> afters = getAnnotatedMethods(testClass, After.class);

        this.result = new Result[testcases.size()];

        int i = 0;
        for (TCase testCase : testcases) {
            try {
                var c = testClass.getDeclaredConstructor().newInstance();

                result[i] = new Result(testCase.method.getName());

                runServiceMethods(befores, c);
                runTestCaseMethod(testCase, c, i);
                runServiceMethods(afters, c);

                passedCount += 1;
            } catch (AssertionError e) {
                failedCount += 1;
                result[i].setError(e.getCause().toString());
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
            i++;
        }

        return result;
    }

    private void runTestCaseMethod(TCase testCase, Object obj, int i) {
        try {
            testCase.method.invoke(obj);
        } catch (AssertionError | InvocationTargetException e) {
            // TODO: Where is my AssertionError? =(
            if (testCase.exception != null) {
                System.out.println(testCase.exception.isAssignableFrom(e.getClass()));
            } else {
                throw new AssertionError(e.getCause());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Class<? extends Throwable> getException(Method testCase, int i) {
        TestCase annotation = testCase.getAnnotation(TestCase.class);
        if (annotation == null || annotation.expected() == TestCase.DefaultClass.class) {
            return null;
        }
        result[i].setMessage("Expected exception: " + annotation.getClass().getName());
        return annotation.expected();
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

    private List<TCase> getTestCases(Class<? extends Object> testedClass) {
        List<TCase> result = new ArrayList<>();

        Method[] methods = testedClass.getMethods();

        for (Method method : methods) {
            TestCase a = method.getAnnotation(TestCase.class);
            if (a != null) {
                TCase tcase = new TCase(method);
                if (a.expected() != TestCase.DefaultClass.class) {
                    tcase.setException(a.expected());
                }
                result.add(tcase);
            }
        }

        return result;
    }

    private class TCase {
        public Method method;
        public Class<? extends Throwable> exception;
        public TCase(Method testCase) {
            this.method = testCase;
        }

        public void setException(Class<? extends Throwable> exception) {
            this.exception = exception;
        }
    }
}
