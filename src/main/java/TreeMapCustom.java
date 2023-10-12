/*
Несбалансированное бинарное дерево поиска с использованием поиска по уникальному ключу
добавление-O(Log n)
удаление по ключу-O(Log n)
поиск по ключу-O(Log n)
удаление по value-O(n)
поиск по value-O(n)
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class TreeMapCustom <K extends Comparable <K>, V extends Comparable <V>> implements InterfaceClassAlgorithm <K, V>{

    private class Node {
        K key;
        V value;
        Node parent, left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Comparator<K> keyComparator;
    private Comparator<V> valueComparator;
    private int size;
    private Node root;

    public TreeMapCustom() {
        this.keyComparator = K::compareTo;
        this.valueComparator = V::compareTo;
        this.size = 0;
        this.root = null;
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
        V result = put(key, value);
        return result == null;
    }

    private V put(K key, V value) {
        Node prev = null;
        Node temp = root;
        while (temp != null) {
            int cmp = keyComparator.compare(key, temp.key);
            if (cmp == 0) {
                V prevValue = temp.value;
                temp.value = value;
                return prevValue;
            }
            prev = temp;
            temp = cmp < 0 ? temp.left : temp.right;
        }
        Node newNode = new Node(key, value);
        if (prev == null) {
            root = newNode;
        }
        else {
            int cmp = keyComparator.compare(key, prev.key);
            if (cmp < 0) {
                prev.left = newNode;
            }
            else {
                prev.right = newNode;
            }
            newNode.parent = prev;
        }
        size++;
        return null;
    }

    @Override
    public boolean removeByValue(V value) {
        Node foundNode = findNodeByValue(root, value);
        if (foundNode == null) {
            return false;
        }
        remove(foundNode);
        return true;
    }

    @Override
    public boolean removeByKey(K key) {
        Node foundNode = findNodeByKey(key);
        if (foundNode == null) {
            return false;
        }
        remove(foundNode);
        return true;
    }

    private void remove(Node node){
        if (node.left == null) {
            replaceNode(node, node.right);
        } else if (node.right == null) {
            replaceNode(node, node.left);
        } else {
            Node lastLeftChild = findLastLeftChild(node.right);
            if (lastLeftChild != node.right) {
                replaceNode(lastLeftChild, lastLeftChild.right);
                lastLeftChild.right = node.right;
                lastLeftChild.right.parent = lastLeftChild;
            }
            lastLeftChild.left = node.left;
            lastLeftChild.left.parent = lastLeftChild;
            replaceNode(node, lastLeftChild);
        }
        size--;
    }

    private Node findLastLeftChild(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void replaceNode (Node node, Node newNode) {
        if (node.parent == null) {
            root = newNode;
        } else if (node.parent.left == node) {
            node.parent.left = newNode;
        } else {
            node.parent.right = newNode;
        }
        if (newNode != null) {
            newNode.parent = node.parent;
        }
    }

    @Override
    public boolean containsValue(V value) {
        Node foundNode = findNodeByValue(root, value);
        return foundNode != null;
    }

    private Node findNodeByValue(Node node, V value) {
        Node temp = node;
        while (temp != null) {
            if (value.equals(temp.value)) {
                return temp;
            }
            temp = findNodeByValue(temp.left, value);
        }

        temp = node;
        while (temp != null) {
            if (value.equals(temp.value)) {
                return temp;
            }
            temp = findNodeByValue(temp.right, value);
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Node foundNode = findNodeByKey(key);
        return foundNode != null;
    }

    @Override
    public void reset() {
        this.size = 0;
        this.root = null;
    }

    private Node findNodeByKey(K key) {
        Node temp =root;
        while (temp != null) {
            int cmp = keyComparator.compare(key, temp.key);
            if (cmp == 0) {
                return temp;
            }

            temp = cmp < 0 ? temp.left : temp.right;
        }
        return null;
    }

    private Object[] keys() {
        List<Object> keys = new ArrayList<>(size);
        getAllObject(root, keys, node -> node.key);
        return keys.toArray();
    }

    private Object[] values() {
        List<Object> values = new ArrayList<>(size);
        getAllObject(root, values, node -> node.value);
        return values.toArray();
    }

    private void getAllObject(Node node, List<Object> array, Function<Node, Object> extractor) {
        if (node == null) {
            return;
        }
        getAllObject(node.left, array, extractor);
        Object o = extractor.apply(node);
        array.add(o);
        getAllObject(node.right, array, extractor);
    }
    @Override
    public String toString() {
        String string = "";
        Object[] keys = keys();
        Object[] values = values();

        for (int i = 0; i < size; i++) {
            string = string + String.format("{%s:%s}", keys[i], values[i]) + ", ";
        }

        return string;

    }
}
