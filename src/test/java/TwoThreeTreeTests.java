import org.junit.Assert;
import org.junit.Test;

public class TwoThreeTreeTests {
    @Test
    public void addTest(){
        TwoThreeTree twoThreeTree = new TwoThreeTree<>(0);
        twoThreeTree.add(0, 4);
        twoThreeTree.add(0, 2);
        twoThreeTree.add(0, 10);
        twoThreeTree.add(0,-2);

        System.out.println(twoThreeTree.heightTree());
    }

    @Test
    public void removeTest(){
        TwoThreeTree twoThreeTree = new TwoThreeTree<>(0);

        int N = 10;
        for (int i = 0; i < N; ++i){
            twoThreeTree.add(0, i);
        }

        Assert.assertEquals(N, twoThreeTree.getSize());
        for (int i = 0; i < N; i++){
            twoThreeTree.removeByValue(i);
        }

        Assert.assertEquals(0, twoThreeTree.getSize());
    }


    @Test
    public void containsValueTest() {
        TwoThreeTree twoThreeTree = new TwoThreeTree<>(0);

        int N = 10;
        for (int i = 0; i < N; ++i) {
            twoThreeTree.add(0, i);
        }
        for (int i = 0; i < N; ++i) {
            Assert.assertTrue(twoThreeTree.containsValue(i));
        }
    }
}
