/*
Несбалансированное бинарное дерево поиска.

добавление-O(Log n)
удаление-O(Log n)
поиск-O(Log n)

*/
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TreeSetCustom<V extends Comparable<V>, K> implements InterfaceClassAlgorithm<K, V> {

    private class Node {
        V value;
        Node parent, left, right;

        public Node (V value) {
            this.value = value;
        }
    }

    private Node root;
    private int size;
    private Comparator<V> comparator;


    public TreeSetCustom(){
        this.comparator = V::compareTo;
        this.root = null;
        this.size = 0;
    }


    //поиск элемента
    public boolean containsValue (V value) {
        return findNode(value) != null;
    }

    public boolean removeByValue (V value) {
        Node nodeFound = findNode(value);
        if (nodeFound == null) {
            return false;
        }
        else {
            remove (nodeFound);
            return true;
        }
    }
    public boolean add(K key, V value){
        Node previous = null;
        Node temp = root;

        //проходим по дереву до низа
        while (temp != null) {
            int cmp = comparator.compare(value, temp.value);
            if (cmp == 0) {
                return false; //дубликаты исключаются
            }

            previous = temp;

            if (cmp > 0) {
                temp = temp.right;
            }
            else {
                temp = temp.left;
            }
        }

        //новый лист c value
        Node newNode = new Node (value);
        if (previous == null) {
            root = newNode;
        }
        else {
            int cmp = comparator.compare(value, previous.value);
            if (cmp > 0) {
                previous.right = newNode;
            }
            else {
                previous.left = newNode;
            }

            newNode.parent = previous;
        }
        size++;
        return true;
    }

    @Override
    public boolean removeByKey(K key) {
        return true;
    }


    private Node compareValue(V value1, Node value2) {
        int cmp = comparator.compare(value1, value2.value);
        if (cmp == 0) {
            return value2;
        }

        if (cmp > 0) {
            return value2.right;
        }
        else {
            return value2.left;
        }
    }

    public Object[] toArray() {
        List<Object> array = new ArrayList<>(size);
        fillArray(root, array);
        return array.toArray();
    }


    public int getSize(){
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

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        Object[] array = toArray();
        String string = "";
        for (Object o: array){
            string = string + " " + o;
        }
        return string;
    }

    private Node findNode(V value){
        Node temp = root;
        while (temp != null) {
            int cmp = comparator.compare(value, temp.value);
            if (cmp == 0) {
                return temp;
            }
            temp = cmp > 0 ? temp.right : temp.left;
        }
        return null;
    }
    private void fillArray(Node node, List<Object> array) {
        if (node == null) {
            return;
        }

        fillArray(node.left, array);
        array.add(node.value);
        fillArray(node.right, array);
    }

    private void remove (Node node){
        if (node.left == null) {
            replace(node, node.right);
        }
        else if (node.right == null){
            replace(node, node.left);
        }
        else {
            Node lastNode = findLastLeftChild(node.right);
            if (lastNode != node.right) {
                replace(lastNode, lastNode.right);
                lastNode.right = node.right;
                lastNode.right.parent = lastNode;
            }
            lastNode.left = node.left;
            lastNode.left.parent = lastNode;
            replace(node, lastNode);
        }
        size--;
    }
    @Override
    public boolean containsKey(K key) {
        return true;
    }

    @Override
    public void reset() {
        this.root = null;
        this.size = 0;
    }

    private Node findLastLeftChild(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void replace(Node node, Node newNode){
        if (node.parent == null) {
            root = newNode;
        }
        else if (node.parent.left == node){
            node.parent.left = newNode;
        }
        else {
            node.parent.right = newNode;
        }
        if (newNode != null) {
            newNode.parent = node.parent;
        }
    }
}
