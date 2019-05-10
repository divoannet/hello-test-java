package tests;

import hello.HelloClass;
import testUtil.*;
import static testUtil.Asserts.*;

public class HelloClassTests {

    private Object[] data = new Object[1];

    @Before
    public void initData() {
        data[0] = new HelloClass(1, "Name");
    }

    @TestCase
    public void checkName() {
        System.out.println(data[0]);
    }

    @TestCase
    public void checkAge() {
        System.out.println("Test2");
    }

    @TestCase
    public void checkId() {
        System.out.println("Test3");
    }

    @After
    public void clean() {
        System.out.println("After");
    }
}
