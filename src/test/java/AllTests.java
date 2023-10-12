import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AllTests {

    InterfaceClassAlgorithm algorithmClass;
    int[] keys;
    int[] values;
    int N;
    Counter counter;
    final long breakTime = 30000;

    AllTests(InterfaceClassAlgorithm algorithmClass, int[] keys, int[] values) {
        this.algorithmClass = algorithmClass;
        this.values = values;
        this.keys = keys;
        N = values.length;
        counter = new Counter();
    }

    public void sizeTest() {
        System.out.print("test size: ");
        Assert.assertEquals(0, algorithmClass.getSize());
        Assert.assertEquals(0, algorithmClass.heightTree());
        System.out.println("OK");
    }

    public long addTest() {
        long duration;
        counter.reset();
        System.out.print("test add: ");

        for (int i = 0; i < N; i++) {
            Assert.assertTrue(algorithmClass.add(keys[i], values[i]));
            Assert.assertEquals(i + 1, algorithmClass.getSize());
        }
        duration = counter.duration();
        System.out.println("OK, time:" + duration + "ms");
        return duration;
    }

    public long containsValue() {
        long duration;
        counter.reset();
        System.out.print("contains value: ");

        for (int value: values) {
            Assert.assertTrue(algorithmClass.containsValue(value));
            if (counter.duration() > breakTime) {
                System.out.print("TIMER! ");
                return -1;
            }
        }
        duration = counter.duration();
        Assert.assertFalse(algorithmClass.containsValue(-9999));
        System.out.println("OK, time:" + duration + "ms");
        return duration;
    }

    public long containsKey(){
        long duration;
        counter.reset();
        System.out.print("contains key: ");

        for (int key : keys) {
            Assert.assertTrue(algorithmClass.containsKey(key));
            if (counter.duration() > breakTime) {
                System.out.print("TIMER! ");
                return -1;
            }
        }
        duration = counter.duration();
        Assert.assertFalse(algorithmClass.containsValue(-9999));
        System.out.println("OK, time:" + duration + "ms");
        return duration;
    }

    public long removeValue(){
        long duration;
        counter.reset();
        System.out.print("remove value: ");

        for (int i = 0; i < N; ++i) {
            Assert.assertTrue(algorithmClass.removeByValue(values[i]));
            Assert.assertEquals(N - i - 1, algorithmClass.getSize());
            if (counter.duration() > breakTime) {
                System.out.print("TIMER! ");
                return -1;
            }
        }
        duration = counter.duration();
        System.out.println("OK, time:" + duration + "ms");

        //check remove values
        for (int value: values) {
            Assert.assertFalse(algorithmClass.containsValue(value));
        }
        return duration;
    }

    public long removeKey(){
        long duration;
        counter.reset();
        System.out.print("remove key: ");
        for (int key : keys) {
            Assert.assertTrue(algorithmClass.removeByKey(key));
            if (counter.duration() > breakTime) {
                System.out.print("TIMER! ");
                return -1;
            }
        }
        duration = counter.duration();
        System.out.println("OK, time:" + duration + "ms");

        //check remove keys
        for (int key: keys) {
            Assert.assertFalse(algorithmClass.containsKey(key));
        }
        return duration;
    }
}
