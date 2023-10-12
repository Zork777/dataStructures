/*
Хеш таблица с бакетами
В бакетах хранятся элементы поиск осуществляется по уникальному ключу

добавление-O(1)
удаление-O(1)
поиск по хешу-O(1)
удаление по value-O(n)
поиск по value-O(n)
*/

import java.util.Objects;

public class HashMapCustom <K, V> implements InterfaceClassAlgorithm <K, V>{

    private class Node <K, V> {
        K key;
        int keyHash;
        V value;
        Node <K,V> nextNode;

        public Node(K key, int keyHash, V value){
            this.key = key;
            this.keyHash = keyHash;
            this.value = value;
        }
    }

    private int size;
    private Node <K, V>[] tableNode;

    public HashMapCustom(int capacity){
        this.size = 0;
        tableNode = new Node[capacity];
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
        int heightTree = 0;
        for (int i = 0; i < tableNode.length; i++){
            heightTree = Math.max(heightTree(tableNode[i]), heightTree);
        }
        return heightTree;
    }

    private int heightTree(Node bucket) {
        int numberElement = 0;
        while (bucket != null){
            numberElement++;
            bucket = bucket.nextNode;
        }
        return numberElement;
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

    private V put(K key, V value){
        int hash = getHash(key);
        int bucketId = getBucketId(key);

        Node <K, V> temp = tableNode[bucketId];
        while (temp != null){
            if (Objects.equals(hash, temp.keyHash) && Objects.equals(key, temp.key)) {
                V prev = temp.value;
                temp.value = value;
                return null;
            }
            temp = temp.nextNode;
        }
        size++;
        Node <K, V> newNode = new Node<>(key, hash, value);
        newNode.nextNode = tableNode[bucketId];
        tableNode[bucketId] = newNode;
        return null;
    }

    private int getBucketId(K key) {
        if (key == null){
            return 0;
        }
        int hash = Math.abs(key.hashCode());
        int bucketId = tableNode.length > hash ? (tableNode.length - 1) % (hash + 1) : hash % (tableNode.length - 1) + 1;
        return bucketId;
    }

    private int getHash(K key) {
        return key != null ? key.hashCode() : 0;
    }


    @Override
    public boolean removeByKey(K key) {
        int hash = getHash(key);
        int bucketId = getBucketId(key);

        Node <K, V> temp = tableNode[bucketId];
        Node <K, V> prev = null;

        while (temp != null) {
            if (Objects.equals(hash, temp.keyHash) && Objects.equals(key, temp.key)) {
                break;
            }
            prev = temp;
            temp = temp.nextNode;
        }
        if (temp == null) {
            return false;
        }
        size--;

        if (prev == null) {
            tableNode[bucketId] = temp.nextNode;
        } else {
            prev.nextNode = temp.nextNode;
        }
        return true;
    }

    @Override
    public boolean removeByValue(V value) {
        if (isEmpty()) {return false;};

        Node <K, V> prev = null;

        for (int i = 0; i < tableNode.length; i++){
            Node <K, V> temp = tableNode[i];
            prev = null;
            while (temp != null) {
                if (temp.value.equals(value)){
                    if (prev == null) {
                        tableNode[i] = temp.nextNode;
                    } else {
                        prev.nextNode = temp.nextNode;
                    }
                    size--;
                    return true;
                }
                prev = temp;
                temp = temp.nextNode;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        Object[] values = values();
        for (Object o : values) {
            if (Objects.equals(value, o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = getHash(key);
        int bucketId = getBucketId(key);

        Node <K, V> temp = tableNode[bucketId];
        while (temp != null) {
            if (Objects.equals(hash, temp.keyHash) && Objects.equals(key, temp.key)) {
                return true;
            }
            temp = temp.nextNode;
        }
        return false;
    }

    @Override
    public void reset() {
        this.size = 0;
        tableNode = new Node[tableNode.length];
    }

    private Object[] keys() {
        Object[] keys = new Object[size];
        int idKey = 0;
        for (int i = 0; i < tableNode.length; i++){
            Node <K, V> temp = tableNode[i];
            while (temp != null) {
                keys[idKey++] = temp.key;
                temp = temp.nextNode;
            }
        }
        return keys;
    }

    private Object[] values() {
        Object[] values = new Object[size];
        int idVal = 0;
        for (int i = 0; i < tableNode.length; i++){
            Node <K, V> temp = tableNode[i];
            while (temp != null) {
                values[idVal++] = temp.value;
                temp = temp.nextNode;
            }
        }
        return values;
    }
    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < tableNode.length; i++){
            Node <K, V> temp = tableNode[i];
            while (temp != null) {
                string = string + String.format("{%s:%s}", temp.key, temp.value) + ", ";
                temp = temp.nextNode;
            }
        }
        return string;
    }
}
