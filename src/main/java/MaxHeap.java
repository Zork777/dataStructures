//Utils
//Алгоритм сортировки чисел на основе структуры данных невозрастающая пирамида
//в корне находится максимальное значение среди всех элементов
//sortDesc() выдает отсортированный массив по убыванию время работы O(nLog n)


import java.util.Arrays;
import java.util.Comparator;

public class MaxHeap<K, V extends Comparable<V>> implements InterfaceClassAlgorithm <K, V> {

    private Object[] data;
    private final Comparator <V> comparator;
    private int size;
    public MaxHeap(int capacity) {
        this.data = new Object [capacity];
        this.comparator = V::compareTo;
        this.size = 0;
    }

    public MaxHeap(Object[] array){
        this.data = array;
        this.comparator = V::compareTo;
        this.size = array.length;
        buildHeap();
    }

    public Object[] sort() {
        buildHeap();
        for (int i = data.length - 1; i > 0; --i){
            swapElement(0, i);
            size = i;
            siftDownElement(0);
        }
        size = data.length;
        return data;
    }

    public Object[] sortDesc() {
        Object[] dataSort = sort();
        Object temp;
        for (int i = 0; i <= data.length / 2 - 1; i++) {
            temp = dataSort[data.length - i - 1];
            dataSort[data.length - i - 1] = dataSort[i];
            dataSort[i] = temp;
        }
        return data;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int heightTree() {
        return size/2;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(V value){
        return add(null, value);
    }
    @Override
    public boolean add(K key, V value) {
        //check V to number
        if (!isNumber(value)) {
            return false;
        }
        if (size == data.length){
            System.out.println("Heap is full");
            return false;
        }
        data[size] = value;

        siftUpElement(size);
        size++;

        return true;
    }

    @Override
    public boolean removeByKey(K key) {
        return true;
    }

    public boolean remove(){
        return removeByValue(null);
    }
    @Override
    public boolean removeByValue(V value) {
        data[0] = data[size - 1];
        data[size - 1] = null;
        size--;
        siftDownElement(0);
        return true;
    }


    @Override
    public boolean containsValue(V value) {
        if (isEmpty()) { return false;}
        return findValue(value, (size - 1) / 2, sort());
    }


    @Override
    public boolean containsKey(K key) {
        return true;
    }

    @Override
    public void reset() {
        this.data = new Object [this.data.length];
        this.size = 0;
    }

    public V maxElement() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return (V) data[0];
    }

    private void buildHeap(){
        for (int i = (data.length / 2) - 1; i >= 0; i--){
            siftDownElement(i);
        }
    }
    private boolean findValue(V value, int index, Object[] array) {

        if (index > array.length && index < 0) {
            return false;
        }

        if (comparator.compare((V) array[index], value) == 0) {
            return true;
        }
        if (comparator.compare(value, (V) array[index]) > 0) {
            array = Arrays.stream(array, index, array.length).toArray();
        }
        else {
            array = Arrays.stream(array, 0, index).toArray();

        }
        if (index == 0) {
            return false; //выход т.к. не нашли элемент
        }
        return findValue(value, array.length / 2, array);
    }
    private void siftDownElement(int i) {
        while (true) {
            int lhs = 2 * i + 1;
            int rhs = lhs + 1;
            int maxIndex = i;
            if (lhs < size && comparator.compare((V) data[lhs], (V) data[maxIndex]) > 0){
                maxIndex = lhs;
            }

            if (rhs < size && comparator.compare((V) data[rhs], (V) data[maxIndex]) > 0){
                maxIndex = rhs;
            }

            if (i == maxIndex) {
                break;
            }

            swapElement(i, maxIndex);
            i = maxIndex;
        }
    }

    private void siftUpElement(int i) {
        int parentId = (i - 1) / 2;
        while (i > 0 && comparator.compare((V) data[parentId], (V) data[i]) < 0){
            swapElement(i, parentId);
            i = parentId;
            parentId = (i - 1) / 2;
        }
    }

    private void swapElement(int i1, int i2) {
        Object temp = data[i1];
        data[i1] = data [i2];
        data[i2] = temp;
    }

    private boolean isNumber(V value){
        try {
            int i = Integer.valueOf((Integer) value);
        } catch (NumberFormatException n) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String string = "[ ";
        for (int i = 0; i < size; i++) {
            string = string + data[i] + " ";
        }
        string = string + " ]";
        return string;
    }
}
