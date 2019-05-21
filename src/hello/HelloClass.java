package hello;

public class HelloClass {

    public int id;
    public String name;

    public HelloClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int factorial(int n) throws MyCustomException {
        if (n < 0) {
            throw new MyCustomException();
        } else if (n == 0) {
            return 1;
        } else {
            return factorial(n-1)*n;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    private class MyCustomException extends Exception {
        public MyCustomException(String message) {
            super(message);
        }
        public MyCustomException() {
            super("Custom exception");
        }
        public String toString() {
            return "Custom exception: " + getMessage();
        }
    }
}
