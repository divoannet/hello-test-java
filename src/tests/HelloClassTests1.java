import testUtil.*;

import static testUtil.Asserts.*;

public class HelloClassTests1 {

    Object[] data = new Object[1];

    @Before
    public void initData() {
        System.out.println("[1] BeforeTest");
    }

    @TestCase
    public void checkName() {
        System.out.println("[1] Test1");
        isEqual(14, 12);
    }

    @TestCase
    public void checkAge() {
        System.out.println("[1] Test2");
    }

    @TestCase
    public void checkId() {
        System.out.println("[1] Test3");
    }

    @After
    public void clean() {
        System.out.println("[1] After");
    }
}
