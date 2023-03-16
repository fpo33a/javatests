
public class Annoted {

    @FpAnnotation(message="this is my message", captureTime="yes")
    public String hello ()
    {
        try {
            System.out.println ("Hello !");
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello !";
    }
}
