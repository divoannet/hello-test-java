package tests;

import testUtil.*;

import static testUtil.Asserts.*;

public class HelloClassTests3 {

    Object[] data = new Object[3];

    @Before
    public void initData() {
        System.out.println("[3] BeforeTest");
    }

    @TestCase
    public void checkName() {
        System.out.println("[3] Test1");
        isEqual(14, 12);
    }

    @TestCase
    public void checkAge() {
        System.out.println("[3] Test2");
    }

    @TestCase
    public void checkId() {
        System.out.println("[3] Test3");
    }

    @After
    public void clean() {
        System.out.println("[3] After");
    }
}
