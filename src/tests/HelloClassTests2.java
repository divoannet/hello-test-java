package tests;

import hello.HelloClass;
import testUtil.*;


public class HelloClassTests2 {

    HelloClass data;

    @Before
    public void initData() {
        System.out.println("[2] BeforeTest");
        data = new HelloClass(3, "New");
    }

    @TestCase
    public void checkName() {
        System.out.println("[2] Test1");
        System.out.println(data.name);
    }

    @TestCase
    public void checkAge() {
        System.out.println("[2] Test2");
    }

    @TestCase
    public void checkId() {
        System.out.println("[2] Test3");
    }

    @After
    public void clean() {
        System.out.println("[2] After");
    }
}
