import static java.net.NetworkInterface.getByIndex;

/*
Дерево по неявному ключу.
Обращение к элементам возможно по индексу как к массиву.
Обращение по индексу - O(1)
Удаление по индексу - O(1)
добавление-O(Log n)
удаление по value-O(n)
поиск по value-O(n)
*/
public class TreeImplicit <K, V> implements InterfaceClassAlgorithm <K, V>{

    private class Node{
        int priority;
        int count;
        V value;

        Node left, right, parent;

        public Node (V value){
            this.priority = getRandom();
            this.value = value;
            this.count = 1;
        }

        public void updateCount(){
            this.count = 1 + getCountSafe(left) + getCountSafe(right);
        }
    }

    private Node root;

    public TreeImplicit() {
        this.root = null;
    }
    private int getCountSafe(Node node){
        return node == null ? 0 : node.count;
    }
    @Override
    public int getSize() {
        return getCountSafe(root);
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
        return getCountSafe(root) == 0;
    }

    @Override
    public boolean add(K key, V value) {
        add(value);
        return true;
    }

    private void add (V value) {
        Node newNode = new Node(value);
        root = merge(root, newNode);
    }
    @Override
    public boolean removeByKey(K key) {
        return false;
    }

    @Override
    public boolean removeByValue(V value) {
        for (int i = 0; i < getSize(); i++){
            if (value.equals(get(i))){
                removeByIndex(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < getSize(); i++) {
            if (value.equals(get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void reset() {
        this.root = null;
    }

    public void removeByIndex(int index){
        if (index < 0 || index >= getSize()) {
            throw new ArrayIndexOutOfBoundsException("Error index=" + index);
        }

        TreapPair<Node> firstSplit = split(root, index);
        TreapPair<Node> secondSplit = split(firstSplit.getRhs(), 1);
        root = merge(firstSplit.getLhs(), secondSplit.getRhs());
    }

    public V get(int index){
        if (index < 0 || index >= getSize()) {
            throw new ArrayIndexOutOfBoundsException("Error index=" + index);
        }
        return getByIndex(root, index);
    }

    private V getByIndex(Node node, int index){
        int leftCount = getCountSafe(node.left);
        if (leftCount == index) {
            return node.value;
        } else if (index < leftCount) {
            return getByIndex(node.left, index);
        } else {
            index = index - leftCount - 1;
            return getByIndex(node.right, index);
        }
    }
    private Node merge (Node left, Node right) {
        if (left == null) { return right; }
        if (right == null) { return left; }

        Node res;

        if (left.priority <= right.priority) {
            res = right;
            res.left = merge(left, res.left);
            res.left.parent = res;
        } else {
            res = left;
            res.right = merge(res.right, right);
            res.right.parent = res;
        }
        res.updateCount();
        return res;
    }

    private TreapPair<Node> split (Node node, int index) {
        if (node == null){
            return new TreapPair<>();
        }
        Node lhs, rhs;
        int leftCount = getCountSafe(node.left);
        if (index <= leftCount) {
            rhs = node;
            TreapPair<Node> splitRes = split(node.left, index);
            lhs = splitRes.getLhs();
            rhs.left = splitRes.getRhs();
            if (rhs.left != null) {
                rhs.left.parent = rhs;
            }
        } else {
            lhs = node;
            TreapPair<Node> splitRes = split(node.right, index - leftCount - 1);
            rhs = splitRes.getRhs();
            lhs.right = splitRes.getLhs();
            if (lhs.right != null) {
                lhs.right.parent = lhs;
            }
        }
        if (rhs != null) { rhs.updateCount();}
        if (lhs != null) { lhs.updateCount();}
        return new TreapPair<>(lhs, rhs);
    }

    private int getRandom() {
        return (int) (Math.random() *  Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        if (!isEmpty()) {
            return toString(root);
        }
        return "null";
    }

    private String toString(Node node){
        String string = "";
        if (node != null) {
            string = string + toString(node.left);
            string = string + node.value + " ";
            string = string + toString(node.right);
        }
        return string;
    }
}
