package testUtil;

import java.util.LinkedList;

public class RunTest {
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RESET = "\u001B[0m";

    private final LinkedList<Class<?>> queue;
    private final TestThread[] threads;

    public RunTest(LinkedList<Class<?>> classList, int threadsCount) {
        this.queue = classList;
        this.threads = new TestThread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new TestThread();
            threads[i].start();
        }
    }

    private class TestThread extends Thread {
        @Override
        public void run() {
            Class<?> r;

            while (true) {
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        return;
                    }
                    r = queue.removeFirst();
                }

                runTestClass(r);
            }
        }

        private void runTestClass(Class<?> testClass) {
            Result[] results = new TestClass(testClass).run();
            int success = 0;
            int failed = 0;

            StringBuilder output = new StringBuilder();
            output.append("\nTEST CLASS [" + testClass.getName() + "]\n");

            for (Result result : results) {
                if (result.success) {
                    output.append(ANSI_GREEN + "[+] " + result.name + " passed" + ANSI_RESET + "\n");
                    if (!result.message.equals("")) {
                        output.append(ANSI_GREEN + "message: " + result.message + ANSI_RESET + "\n");
                    }
                    success += 1;
                } else {
                    output.append(ANSI_RED + "[-] " + result.name + " failed" + ANSI_RESET+ "\n");
                    output.append("Cause: " + result.message + "\n");
                    failed += 1;
                }
            }

            output.append("\n--------------\n");
            output.append(ANSI_GREEN + "passed: " + success + ANSI_RESET+ "\n");
            output.append(ANSI_RED + "failed: " + failed + ANSI_RESET+ "\n");

            System.out.println(output.toString());
        }
    }
}
