/*
Хеш таблица с бакетами
В бакетах хранятся элементы

добавление-O(1)
удаление-O(1)
поиск по хешу-O(1)
удаление по value-O(n)
поиск по value-O(n)
*/
import java.util.Objects;

public class HashSetCustom<V, K> implements InterfaceClassAlgorithm<K, V>{

    private class Node <V> {
        V value;
        int hash;
        Node <V> nextNode;

        public Node(V value, int hash) {
            this.value = value;
            this.hash = hash;
        }
    }

    private Node<V>[] tableNode;
    private int size;

    public HashSetCustom(int capacity) {
        this.tableNode = new Node [capacity];
        this.size = 0;
    }

    public int getSize(){
        return size;
    }

    @Override
    public int heightTree() {
        if (isEmpty()) {
        return 0;
    }
        int heightTree = 0;
        for (int i = 0; i < tableNode.length; i++){
            heightTree = Math.max(heightTree(tableNode[i]), heightTree);
        }
        return heightTree;
}

    private int heightTree(Node<V> bucket) {
        int numberElement = 0;
        while (bucket != null){
            numberElement++;
            bucket = bucket.nextNode;
        }
        return numberElement;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(K key, V value){
        int bucketId = getBucketId(value);
        int hash = value != null ? value.hashCode() : 0;

        Node <V> bucket = tableNode[bucketId];
        while (bucket != null){
            if (Objects.equals(hash, bucket.hash) && Objects.equals(value, bucket.value)) {
                return false;
            }
            bucket = bucket.nextNode;
        }
        Node<V> newNode = new Node<>(value, hash);
        newNode.nextNode = tableNode[bucketId];
        tableNode[bucketId] = newNode;
        size++;
        return true;
    }

    @Override
    public boolean removeByKey(K key) {
        return true;
    }

    private int getBucketId(V value) {
        if (value == null) {
            return 0;
        }
        int hash = Math.abs(value.hashCode());
        int bucketId = tableNode.length > hash ? (tableNode.length - 1) % (hash + 1) : hash % (tableNode.length - 1) + 1;
        return bucketId;
    }


    public boolean removeByValue(V value){
        int hash = value != null ? value.hashCode() : 0;
        int buckedId = getBucketId(value);
        Node<V> bucket = tableNode[buckedId];
        Node<V> prev = null;

        while (bucket != null) {
            if (Objects.equals(hash, bucket.hash) && Objects.equals(value, bucket.value)) {
                break;
            }
            prev = bucket;
            bucket = bucket.nextNode;
        }

        if (bucket == null) {
            return false;
        }

        if (prev == null) {
            tableNode[buckedId] = tableNode[buckedId].nextNode;
        }
        else {
            prev.nextNode = bucket.nextNode;
        }
        size--;
        return true;
    }

    @Override
    public boolean containsKey(K key) {
        return true;
    }

    @Override
    public void reset() {
        this.tableNode = new Node [tableNode.length];
        this.size = 0;
    }

    public boolean containsValue(V value){
        int hash = value != null ? value.hashCode() : 0;
        Node<V> bucket = tableNode[getBucketId(value)];
        while (bucket != null){
            if (Objects.equals(hash, bucket.hash) && Objects.equals(value, bucket.value)) {
                return true;
            }
            bucket = bucket.nextNode;
        }
        return false;
    }

    @Override
    public String toString() {

        String string = " ";

        for (int i = 0; i < tableNode.length; i++) {
            Node<V> bucket = tableNode[i];
            while (bucket !=null) {
                string = string + bucket.value + " ";
                bucket = bucket.nextNode;
            }
        }
        return string;
    }
}
