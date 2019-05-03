import testUtil.After;
import testUtil.Before;
import testUtil.HelloTest;
import testUtil.TestCase;

public class HelloClassTests extends HelloTest {

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
        System.out.println("Test1");
    }

    @TestCase
    public void checkId() {
        System.out.println("Test1");
        isTrue(true, "lol");
    }

    @After
    public void clean() {
        System.out.println("testUtil.After");
        isTrue(false, "Oh no!");
    }
}
