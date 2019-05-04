import testUtil.*;

public class HelloClassTests {

    Object[] data = new Object[1];

    @Before
    public void initData() {
        System.out.println("BeforeTest");
    }

    @TestCase
    public void checkName() {
        System.out.println("Test1");
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
