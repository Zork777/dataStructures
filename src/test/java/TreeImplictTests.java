import org.junit.Assert;
import org.junit.Test;

public class TreeImplictTests {

    @Test
    public void getTest(){
        TreeImplicit treeImplicit = new TreeImplicit<>();
        final int N = 1000;

        for (int i = 0; i < N; i++){
            treeImplicit.add(i, i + 100);
        }

        for(int i = 0; i < N; i++){
            Assert.assertEquals(i + 100, treeImplicit.get(i));
        }
    }

    @Test
    public void removeByIndexTest(){
        TreeImplicit treeImplicit = new TreeImplicit<>();
        final int N = 100;

        for (int i = 0; i < N; i++){
            treeImplicit.add(i, i + 100);
        }

//        System.out.println(treeImplicit);
//        treeImplicit.removeByIndex(1);
//        System.out.println(treeImplicit);
//        treeImplicit.removeByIndex(8);
//        System.out.println(treeImplicit);
        for(int i = 0; i < N - 1; i++){
            treeImplicit.removeByIndex(0);
            Assert.assertEquals(i + 100 + 1, treeImplicit.get(0));
            Assert.assertEquals(N - i - 1, treeImplicit.getSize());
        }
    }
}
