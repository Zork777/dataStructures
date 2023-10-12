import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;


public class StackBinaryTreeTests {
    @Test
    public void test() {
        StackBinaryTree tree = new StackBinaryTree();
        tree.add(9, "9");
        tree.add(5, "5");
        tree.add(15, "15");
        tree.add(3, "3");
        tree.add(7, "7");
        tree.add(10, "10");
        tree.add(21, "21");

        System.out.print("Directly recur:");
        tree.printTreeDirectlyR();

        System.out.print("\nDirectly stack:");
        tree.printTreeDirectlyStack();

        System.out.print("\nIncrease recur:");
        tree.printTreeIncreaseR();

        System.out.print("\nIncrease stack:");
        tree.printTreeIncreaseStack();

        System.out.print("\nsort stack:");
        ArrayList result = tree.sort();
        for (Object o: result){
            System.out.print(" " + o.toString());
        }
    }
}
