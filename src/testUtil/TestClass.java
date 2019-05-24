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

    TestClass(Class<?> testClass) {
        this.testClass = testClass;
    }

    Result[] run() {
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
                result[i].setError(e.getMessage());
            } catch (Throwable e) {
                if (testCase.exception != null && testCase.exception.isAssignableFrom(e.getClass())) {
                    result[i].setMessage("expected exception [" + e.getClass() + "]");
                } else {
                    e.printStackTrace();
                }
            }
            i++;
        }

        return result;
    }

    private void runTestCaseMethod(TCase testCase, Object obj, int i) throws Throwable {
        try {
            testCase.method.invoke(obj);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void runServiceMethods(List<Method> methods, Object obj) {
        for (Method method : methods) {
            try {
                method.invoke(obj);
            } catch (Throwable e) {
                throw new Error(e);
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
        Method method;
        Class<? extends Throwable> exception;
        TCase(Method testCase) {
            this.method = testCase;
        }

        void setException(Class<? extends Throwable> exception) {
            this.exception = exception;
        }
    }
}
