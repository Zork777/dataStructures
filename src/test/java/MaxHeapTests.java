import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class MaxHeapTests {
    @Test
    public void addArrayTest(){
        Object [] array = new Object[]{8, 3, 6, 7, -1, 4, 5, -20, 100};
        MaxHeap maxHeap = new MaxHeap<>(array);
        System.out.println("before tree=" + maxHeap);

        Assert.assertEquals(array.length, maxHeap.getSize());
        while (!maxHeap.isEmpty()) {
            Assert.assertTrue(maxHeap.remove());
//            System.out.println("before tree=" + maxHeap);
        }

    }

    @Test
    public void bigTestRandomData(){
        final int N = 1000;
        List<Integer> data = new ArrayList<>(N);
        for (int i = 0; i < N; i++){
            data.add(i);
        }

        Collections.shuffle(data);
        MaxHeap maxHeap = new MaxHeap<>(data.toArray(new Integer[0]));
        for (int i = 1; i <= N; i++) {
            Assert.assertEquals(N - i, maxHeap.maxElement());
            maxHeap.remove();
        }
        System.out.println(maxHeap);
    }
    @Test
    public void simpleAddRemove(){
        final int N = 10;
        MaxHeap maxHeap = new MaxHeap<>(N);
        maxHeap.add(5);
        maxHeap.add(1);
        maxHeap.add(3);
        maxHeap.add(6);
        maxHeap.add(10);
        maxHeap.add(7);

        Assert.assertEquals(10, maxHeap.maxElement());
        maxHeap.remove();
        Assert.assertEquals(7, maxHeap.maxElement());
        maxHeap.remove();
        Assert.assertEquals(6, maxHeap.maxElement());
        maxHeap.remove();
        Assert.assertEquals(5, maxHeap.maxElement());
        maxHeap.remove();
        Assert.assertEquals(3, maxHeap.maxElement());
        maxHeap.remove();
        Assert.assertEquals(1, maxHeap.maxElement());
        maxHeap.remove();
        Assert.assertEquals(0, maxHeap.getSize());
    }

    @Test
    public void sortArrayDesc(){
        final int N = 1_000;
        List<Integer> data = new ArrayList<>(N);

        for (int i = 0; i < N; i++){
            data.add(i);
        }

        Collections.shuffle(data);
        MaxHeap maxHeap = new MaxHeap<>(data.toArray(new Integer[0]));

        System.out.println("before data=" + maxHeap);

        Object [] sortArray = maxHeap.sortDesc();
        System.out.println("after data (Desc)=" + Arrays.toString(sortArray));
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(i, sortArray[(N - 1) - i]);
        }

        sortArray = maxHeap.sort();
        System.out.println("after data (Incr)=" + Arrays.toString(sortArray));
        for (int i = 0; i < N; i++) {
            Assert.assertEquals(i, sortArray[i]);
        }

    }

}
