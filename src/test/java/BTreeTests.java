import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BTreeTests {
    final int N = 1000000 ;
    @Test
    public void addTest(){
        BTree bTree = new BTree(2,0);
        Assert.assertEquals(0, bTree.getSize());
        for (int i = 0; i < N; ++i){
            bTree.add(0, i);
            Assert.assertEquals(i + 1, bTree.getSize());
        }
    }

    @Test
    public void removeTest(){
        BTree bTree = new BTree(2,0);
        List<Integer> data = new ArrayList<>(N);

        for (int i = 0; i < N; ++i){
            data.add(i);
        }

        Collections.shuffle(data);

        data.forEach(value -> bTree.add(0, value));
        Assert.assertEquals(N, bTree.getSize());

        data.forEach(value -> bTree.removeByValue(value));
        Assert.assertEquals(0, bTree.getSize());
    }


    @Test
    public void containsValueTest() {
        BTree bTree = new BTree(2,0);

        List<Integer> data = new ArrayList<>(N);

        for (int i = 0; i < N; ++i){
            data.add(i);
        }

        Collections.shuffle(data);

        data.forEach(value -> bTree.add(0, value));
        Assert.assertEquals(N, bTree.getSize());

        data.forEach(value -> Assert.assertTrue(bTree.containsValue(value)));
    }
}
