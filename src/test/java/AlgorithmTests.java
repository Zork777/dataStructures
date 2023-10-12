import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class AlgorithmTests {

    final int N = 1_000_000;
    final String[] classNameKeyTestSkip = { "TreeSetCustom",
                                            "HashSetCustom",
                                            "MaxHeap",
                                            "ListStack",
                                            "TreeBinary",
                                            "TreeImplicit",
                                            "TwoThreeTree",
                                            "BTree"};
    int[] keys = new int[N];
    int[] values = new int[N];

    InterfaceClassAlgorithm[] obj = {new TreeSetCustom(),
                                    new HashSetCustom(N),
                                    new TreeMapCustom(),
                                    new HashMapCustom(N),
                                    new MaxHeap<>(N),
                                    new ListStack(),
//                                    new StackBinaryTree(),
                                    new TreeBinary(),
                                    new Treap<>(),
                                    new TreeImplicit(),
                                    new TwoThreeTree(0),
                                    new BTree(10, 0)};
//    InterfaceClassAlgorithm[] obj = {new BTree(3, 0)};

    ArrayList<AlgorithmDataResult> dataResults = new ArrayList<AlgorithmDataResult>();

    @Test
    public void mainTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < N; i++){
            keys[i] = i ;
            values[i] = i + N;
        }
        values = shuffleArray(values);
        keys = shuffleArray(keys);


        for (InterfaceClassAlgorithm algorithmClass : obj) {
            AlgorithmDataResult result = new AlgorithmDataResult();
            AllTests allTests = new AllTests(algorithmClass, values, keys);

            result.name = algorithmClass.getClass().getName();
            System.out.println("Algorithm=" + result.name);

            //Test size
            allTests.sizeTest();

            //Test add
            result.add = allTests.addTest();


            //Print size Tree
            result.height = algorithmClass.heightTree();
            System.out.println("size tree = " + result.height);

            //Test contains value
            result.containsValue = allTests.containsValue();
            if (result.containsValue == -1) { //break timer
                    algorithmClass.reset();
                    allTests.addTest();
                }

            //Test contains key
            if (Arrays.toString(classNameKeyTestSkip).contains(result.name)) {
                System.out.println("contains key test - SKIP");
            }else {
                result.containsKey = allTests.containsKey();
            }
            if (result.containsKey == -1) { //break timer
                algorithmClass.reset();
                allTests.addTest();
            }

            //Test remove value
            result.removeValue = allTests.removeValue();
            if (result.removeValue == -1) { //break timer
                algorithmClass.reset();
                allTests.addTest();
            }

            //Test remove key
            if (Arrays.toString(classNameKeyTestSkip).contains(result.name)) {
                System.out.println("remove key test - SKIP");
            }else {
                result.removeKey = allTests.removeKey();
                }
            if (result.removeKey == -1) { //break timer
                algorithmClass.reset();
                allTests.addTest();
            }


            System.out.println("***************************\n");
            dataResults.add(result);
        }

        System.out.println("N=" + N);
        System.out.printf("+ %-13s + %-7s + %-6s + %-13s + %-13s +%n", "-------------", "------", "------", "-------------", "-------------");
        System.out.printf("| %-13s | %-7s | %-6s | %-13s | %-13s |%n", "  algorithm", "height", " add", " remove (ms)", "contain (ms)");
        System.out.printf("| %-13s | %-7s | %-6s | %-4s | %-6s | %-4s | %-6s |%n", "         ", "   ", "   ", "key", "value", "key", "value");
        System.out.printf("+ %-13s + %-7s + %-6s + %-4s + %-6s + %-4s + %-6s + %n", "-------------", "------", "------", "----", "------", "----", "------");
        dataResults.forEach(data ->
                System.out.printf( "| %-13s | %-7s | %-6s |%-5s | %-6s | %-4s | %-6s |%n",
                        data.name, data.height, data.add, data.removeKey, data.removeValue, data.containsKey, data.containsValue));
        System.out.printf("+ %-13s + %-7s + %-6s + %-4s + %-6s + %-4s + %-6s + %n", "-------------", "------", "------", "----", "------", "----", "------");
    }

    private int[] shuffleArray(int[] array){
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        return array;
    }
}
