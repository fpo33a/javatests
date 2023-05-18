package JsonTest;

public class MyNode {

    private String path = "";
    private int index = 0;

    public MyNode (String path, int index) {
        this.index = index;
        this.path = path;
    }

    public String toString () {
        return path + "[" + index + "]";
    }
}
