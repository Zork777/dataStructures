/*
Хранение value на основе односвязного списка по принципу стека.
добавление-O(1)
удаление-O(1)
поиск-O(n)
*/
public class ListStack<K, V> implements InterfaceClassAlgorithm<K, V> {

    private class Node {
        V value;
        Node next;

        public Node (V value){
            this.value = value;
        }
    }

    private Node head;
    public int size;


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int heightTree() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean add(K key, V value) {
        return push(value);
    }

    @Override
    public boolean removeByKey(K key) {
        return false;
    }

    @Override
    public boolean removeByValue(V value) {
        value = pop();
        return value != null;
    }

    @Override
    public boolean containsValue(V value) {
        Node node = head;
        int n = size;
        while (node != null && n > 0) {
            --n;
            if (node.value.equals(value)){
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void reset() {
        this.size = 0;
    }


    public ListStack(){
        this.size = 0;
    }
    public boolean push(V value){
        Node node = new Node(value);
        node.next = head;
        head = node;
        size++;
        return true;
    }

    public V pop(){
        if (isEmpty()) {
            return null;
        }
        V value = head.value;
        head = head.next;
        size--;
        return value;
    }
}
