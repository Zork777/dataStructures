import java.util.Date;

public class Counter {
    private long start;

    public Counter() {
        reset();
    }

    public void reset(){
        start = new Date().getTime();
    }

    public long duration(){
        return new Date().getTime() - start;
    }

}
