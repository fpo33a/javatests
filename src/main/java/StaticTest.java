import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StaticTest {

    static {
        System.out.println("This is static part of StaticTest");

        Annoted annoted = new Annoted();
        Method m = null;
        long t1 = 0, t2 = 0;
        try {
            m = annoted.getClass().getMethod("hello");
            FpAnnotation anno = m.getAnnotation(FpAnnotation.class);
            if (anno.captureTime().compareToIgnoreCase("yes") == 0) t1 = System.nanoTime();
            String result = (String) m.invoke(annoted);
            if (anno.captureTime().compareToIgnoreCase("yes") == 0) t2 = System.nanoTime();
            if (anno.captureTime().compareToIgnoreCase("yes") == 0)
                System.out.println("invoking "+m.getName()+" : " + anno.message() + " : " +result+ " took "+(t2-t1)+" nano sec");
            else
                System.out.println("invoking "+m.getName()+" : " + anno.message() + " : " +result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        System.out.println("This is test");
    }

    public StaticTest () {
        System.out.println("StaticTest constructor !");
    }
}
