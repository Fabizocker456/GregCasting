package eu.seahousen.gregcasting;

public class DebugUtil {
    public static void dumpCallstack() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        System.err.println("-- STACK TRACE --");
        for(int i = 0; i < 15; i++) {
            System.err.println("trace " + trace[i].getClassName() + ":" + trace[i].getMethodName() + " [" + trace[i].getFileName() + ":" + trace[i].getLineNumber() + "]");
        }
        System.err.println("-- END STACK TRACE --");
    }
}
