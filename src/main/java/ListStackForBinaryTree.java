public class ListStackForBinaryTree <V> {

    private class Node {
        V value;
        Node next;

        public Node (V value){
            this.value = value;
        }
    }

    private Node head;

    public boolean isEmpty() {
        return head == null;
    }

    public void push(V value){
        Node node = new Node(value);
        node.next = head;
        head = node;
    }

    public V pop(){
        if (isEmpty()) {
            return null;
        }
        V value = head.value;
        head = head.next;
        return value;
    }
}
