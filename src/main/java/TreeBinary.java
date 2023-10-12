/*
Несбалансированное бинарное дерево поиска
-поиск, минимум, максимум, удаление, добавление
добавление-O(Log n)
удаление -O(Log n)
поиск -O(Log n)
*/

import java.util.Iterator;

public class TreeBinary <K, V extends Comparable<V>> implements InterfaceClassAlgorithm <K, V> {

    private class Node {
        V value;
        Node left, right, parent;
        boolean doubleValue;

        Node(){

        }

        Node (V value) {
            this.value = value;
        }
    }

    public class TreeBinaryIterator implements Iterator<V>{

        private Node node;
        public TreeBinaryIterator(Node root){
            if (root !=null) {
                node = findLeftChild(root);
            }
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public V next() {
            if (node == null) {
                throw new IllegalStateException("Iterator error");
            }
            V value = node.value;
            nextNode();
            return value;
        }

        private void nextNode(){
            if (node.right != null) {
                node = findLeftChild(node.right);
            } else {
                while (node.parent != null && node.parent.right == node){
                    node = node.parent;
                }
                node = node.parent;
            }
        }
    }

    private int size;
    private Node root;

    public TreeBinary(){
        this.size = 0;
        this.root = null;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(K key, V value) {
        Node prev = null;
        Node temp =root;

        while (temp != null) {
            int compare = value.compareTo(temp.value);
            if (compare == 0) {
                temp.doubleValue = !temp.doubleValue;
            }
            prev = temp;

            if (compare > 0 || compare == 0 && temp.doubleValue){
                temp = temp.right;
            } else {
                temp = temp.left;
            }
        }

        Node newNode = new Node(value);
        if (prev == null) {
            root = newNode;
        } else {
            int compare = value.compareTo(prev.value);

            if (compare > 0 || compare == 0 && prev.doubleValue){
                prev.right = newNode;
            } else {
                prev.left = newNode;
            }
            newNode.parent = prev;
        }
        size++;
        return true;
    }

    @Override
    public boolean removeByKey(K key) {
        return false;
    }

    @Override
    public boolean removeByValue(V value) {
        Node foundNode = findNode(value);
        if (foundNode == null) {
            return false;
        }
        removeNode(foundNode);
        return true;
    }

    @Override
    public boolean containsValue(V value) {
        Node temp = findNode(value);
        return temp != null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void reset() {
        this.size = 0;
        this.root = null;
    }

    public Iterator<V> iterator(){
        return new TreeBinaryIterator(root);
    }
    public V maxValue(){
        Node temp = root;
        if (temp == null) {
            return null;
        }
        while (temp.right != null) {
            temp = temp.right;
        }
        return temp.value;
    }

    public V minValue(){
        if (root == null) {
            return null;
        }
        return findLeftChild(root).value;
    }

    public Object[] values(){
        Object[] values = new Object[size];
        int i = 0;

        Iterator<V> iterator = iterator();
        while (iterator.hasNext()) {
            values[i++] = iterator.next();
        }
        return values;
    }

    public int heightTree() {
        return heightTree(root);
    }

    @Override
    public String toString() {
        String string = "";
        Iterator<V> iterator = iterator();
        while (iterator.hasNext()) {
            string = string + " " + iterator.next();
        }
        return string;
    }

    public void print(){
        print (root);
    }

    private void print(Node node){
        if (node != null){
            print (node.left);
            System.out.print(" " + node.value);
            print(node.right);
        }
    }


    private Node findNode(V value){
        Node temp = root;
        while (temp != null) {
            int compare = value.compareTo(temp.value);
            if (compare == 0) {
                return temp;
            }
            temp = compare > 0 ? temp.right : temp.left;
        }
        return null;
    }

    private void removeNode(Node node){
        if (node.left == null){
            replaceNode(node, node.right);
        } else if (node.right == null) {
            replaceNode(node, node.left);
        } else {
            Node nodeLeft = findLeftChild(node.right);
            if (nodeLeft != node.right) {
                replaceNode(nodeLeft, nodeLeft.right);
                nodeLeft.right = node.right;
                nodeLeft.right.parent = nodeLeft;
            }
            nodeLeft.left = node.left;
            nodeLeft.left.parent = nodeLeft;
            replaceNode(node, nodeLeft);
        }
        size--;
    }

    private Node findLeftChild(Node node){
        while (node.left != null){
            node = node.left;
        }
        return node;
    }
    private void replaceNode(Node node, Node newNode){
        if (node.parent == null) {
            root = newNode;
        } else if (node.parent.left == node) {
            node.parent.left = newNode;
        } else {
            node.parent.right = newNode;
        }

        if (newNode != null){
            newNode.parent = node.parent;
        }
    }

    private int heightTree(Node node){
        if (node == null || node.left == null && node.right == null) {
            return 0;
        }
        return 1 + Math.max(heightTree(node.left), heightTree(node.right));
    }
}
