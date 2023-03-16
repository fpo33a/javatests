import java.lang.reflect.Method;

public class Main {

    public static void main (String args[]) {
        System.out.println("This is Main");

        // by just invoking constructor or a static method it runs the "static" code of StaticTest who is using annotation
        // StaticTest.test();
        StaticTest staticTest = new StaticTest();


    }
}
