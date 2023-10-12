import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TreapTests {

    final int N = 5_000_000;
    @Test
    public void removeValueTest(){
        Treap treap = new Treap<>();

        ArrayList<Integer> data = new ArrayList<Integer>();

        for (int i = 0; i < N; ++i){
            data.add(i);
        }

        data.forEach(value -> Assert.assertTrue(treap.add(value, value + N)));
        Assert.assertEquals(N, treap.getSize());

        //remove by value
        data.forEach(value -> Assert.assertTrue(treap.removeByValue(value + N)));
        Assert.assertEquals(0, treap.getSize());

        data.forEach(value -> Assert.assertFalse(treap.containsValue(value + N)));
    }

    @Test
    public void removeKeyTest(){
        Treap treap = new Treap<>();

        ArrayList<Integer> data = new ArrayList<Integer>();

        for (int i = 0; i < N; ++i){
            data.add(i);
        }

        data.forEach(value -> Assert.assertTrue(treap.add(value, value + N)));
        Assert.assertEquals(N, treap.getSize());

        data.forEach(value -> Assert.assertTrue(treap.removeByKey(value)));
        Assert.assertEquals(0, treap.getSize());

        data.forEach(value -> Assert.assertFalse(treap.containsKey(value)));

    }
}
