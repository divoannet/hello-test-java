
public class HelloClassTest {
    private Object[] data = new Object[1];

    @Before
    public void initData() {
        this.data[0] = new HelloClass(12, "Vasya");
    }

    @Test
    public void testData() {
        HelloTests.isEqual(this.data[0].name, "Vasya");
    }
}
