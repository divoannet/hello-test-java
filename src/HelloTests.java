class HelloTests {

    static <T, U> boolean isEqual(T arg1, U arg2) {
        Class class1 = arg1.getClass();
        Class class2 = arg2.getClass();

        if (!class1.equals(class2)) {
            return false;
        }

        return arg1.equals(arg2);
    }

}
