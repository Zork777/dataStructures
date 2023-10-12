/*
Utils
Несбалансированное бинарное дерево поиска с обходом дерева с использованием стека и с использованием рекурсии.
*/

import java.util.*;

public class StackBinaryTree <K extends Comparable<K>, V> implements InterfaceClassAlgorithm <K, V> {

    private class Node {
        K key;
        V value;
        Node left, right;

        public Node (K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }


    private Node root;
    private int size;
    private Comparator<K> comparator;

    public StackBinaryTree(){
        this.comparator = K::compareTo;
        this.size = 0;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int heightTree() {
        if (isEmpty()) {
            return 0;
        }
        return heightTree(root) ;
    }

    private int heightTree(Node node) {
        if (node == null || node.left == null && node.right == null) {
            return 0;
        }
        return 1 + Math.max(heightTree(node.left), heightTree(node.right)) ;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(K key, V value) {
        Node prev = null;
        Node temp = root;

        while (temp != null) {
            prev = temp;
            int cmp = comparator.compare(key, temp.key);

            if (cmp >= 0) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }

        Node newNode = new Node(key, value);
        if (prev == null) {
            root = newNode;
        } else {
            int cmp = comparator.compare(key, prev.key);
            if (cmp > 0) {
                prev.right = newNode;
            } else {
                prev.left = newNode;
            }
        }
        size++;
        return true;
    }

    public void printTreeDirectlyStack(){
        if (root == null) {
            return;
        }

        ListStackForBinaryTree<V> stack = new ListStackForBinaryTree<>();
        stack.push((V)root);

        while (!stack.isEmpty()){
            Node curNode = (Node) stack.pop();
            System.out.print(" " + curNode.key);
            if (curNode.right != null) {
                stack.push((V) curNode.right);
            }
            if (curNode.left !=null){
                stack.push((V) curNode.left);
            }

        }


    }

    public void printTreeIncreaseStack(){
        ListStackForBinaryTree<V> stack = new ListStackForBinaryTree<>();

        Node temp = root;
        while (temp != null){
            stack.push((V) temp);
            temp = temp.left;
        }

        while (!stack.isEmpty()){
            Node curNode = (Node) stack.pop();
            System.out.print(" " + curNode.key);

            temp = curNode.right;
            while (temp != null){
                stack.push((V) temp);
                temp = temp.left;
            }
        }
    }

    public ArrayList sort (){
        ArrayList result = new ArrayList<>(size);
        ListStackForBinaryTree<V> stack = new ListStackForBinaryTree<>();

        Node temp = root;
        while (temp != null){
            stack.push((V) temp);
            temp = temp.left;
        }

        while (!stack.isEmpty()){
            Node curNode = (Node) stack.pop();
            result.add(curNode);

            temp = curNode.right;
            while (temp != null){
                stack.push((V) temp);
                temp = temp.left;
            }
        }
        return result;
    }


    public void printTreeIncreaseR(){
        printTreeIncreaseR(root);
    }
    private void printTreeIncreaseR(Node node){
        if (node != null) {
            printTreeIncreaseR(node.left);
            System.out.print(" " + node.key);
            printTreeIncreaseR(node.right);
        }
    }

    public void printTreeDescR(){
        printTreeDescR(root);
    }

    private void printTreeDescR(Node node){
        if (node != null) {
            printTreeDescR(node.left);
            printTreeDescR(node.right);
            System.out.print(" " + node.key);
        }
    }
    public void printTreeDirectlyR(){
        printTreeDirectlyR(root);
    }
    private void printTreeDirectlyR(Node node){
        if (node != null) {
            System.out.print(" " + node.key);
            printTreeDirectlyR(node.left);
            printTreeDirectlyR(node.right);
        }
    }
    @Override
    public boolean removeByKey(K key) {
        pause(10);
        return false;
    }

    @Override
    public boolean removeByValue(V value) {
        pause(10);
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        ArrayList result = sort();
        Node node;
        for (Object o : result) {
            node = (Node) o;
            if (node.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        ArrayList result = sort();
        Node node;
        for (Object o : result) {
            node = (Node) o;
            if (node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        this.size = 0;
    }

    private static void pause(long timeInMilliSeconds) {

        long timestamp = System.currentTimeMillis();


        do {

        } while (System.currentTimeMillis() < timestamp + timeInMilliSeconds);

    }
}
