import org.junit.Test;

public class TreeBinaryTests {
    @Test
    public void printTest(){
        TreeBinary treeBinary = new TreeBinary<>();

        final int N = 100;

        for (int i = 0; i < N; i++){
            treeBinary.add(i, (int) (Math.random() * N));
        }

        System.out.println(treeBinary);
        treeBinary.print();
    }
}
