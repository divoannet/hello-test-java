import testUtil.*;

import static testUtil.Asserts.*;

public class HelloClassTests2 {

    Object[] data = new Object[2];

    @Before
    public void initData() {
        System.out.println("[2] BeforeTest");
    }

    @TestCase
    public void checkName() {
        System.out.println("[2] Test1");
        isEqual(14, 12);
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
