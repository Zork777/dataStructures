/*
Дерамида- дерево поиска и пирамида. По ключам построено бинарное дерево поиска, а по приоритетам пирамиду (невозрастающую).
добавление-O(Log n)
удаление по ключу-O(Log n)
поиск по ключу-O(Log n)
удаление по value-O(n)
поиск по value-O(n)
*/
import java.util.Comparator;

public class Treap <K extends Comparable<K>, V extends Comparable<V>> implements InterfaceClassAlgorithm <K, V> {

    private class Node {
        K key;
        int priority;
        boolean flagDoubleValue;
        V value;
        int countNode;
        Node left, right, parent;

        public Node (K key, V value) {
            this.key = key;
            this.value = value;
            this.priority = getRandom();
            this.countNode = 1;
        }

        public void updateCount(){
            this.countNode = 1;

            if (this.left != null){
                this.countNode += this.left.countNode;
            }

            if (this.right != null){
                this.countNode += this.right.countNode;
            }
        }

        public void setLeftChild(Node node){
            this.left = node;
            if (node != null) {
                node.parent = this;
            }
        }

        public void setRightChild(Node node){
            this.right = node;
            if (node != null) {
                node.parent = this;
            }
        }

        private int getRandom() {
            return (int) (Math.random() *  Integer.MAX_VALUE);
        }


    }
    private final Comparator<K> comparatorK;
    private final Comparator<V> comparatorV;
    private int size;
    private Node root;
    public Treap(){
        this.comparatorK = K::compareTo;
        this.comparatorV = V::compareTo;
        this.size = 0;
        this.root = null;
    }
    @Override
    public int getSize() {
        if (root == null){
            return 0;
        }
        return root.countNode;
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
        return this.size == 0;
    }

    @Override
    public boolean add(K key, V value) {
        Node newNode = new Node (key, value);
        if (root == null) {
            root = newNode;
        } else {
            add(root, newNode);
        }
        size++;
        return true;
    }

    private void add (Node node, Node newNode) {
        if (node.priority < newNode.priority) {
            replace(node, newNode);

            TreapPair<Node> splitRes = split(node, newNode.key);
            newNode.setLeftChild(splitRes.getLhs());
            newNode.setRightChild(splitRes.getRhs());
        } else {
            int cmp = comparatorK.compare(newNode.key, node.key);
            if (cmp == 0) {
                node.flagDoubleValue = !node.flagDoubleValue;
            }

            if (cmp > 0 || cmp == 0 && node.flagDoubleValue) {
                //go to right
                if (node.right == null) {
                    node.setRightChild(newNode);
                } else {
                    add(node.right, newNode);
                }
            } else {
                // goto left
                if (node.left == null) {
                    node.setLeftChild(newNode);
                } else {
                    add(node.left, newNode);
                }
            }
        }
        newNode.updateCount();
        node.updateCount();
    }

    @Override
    public boolean removeByKey(K key) {
        if (root != null) {
            return removeByKey(root, key);
        }
        return false;
    }

    private boolean removeByKey(Node node, K key){
        boolean res = false;
        if (node == null) {
            return false;
        }

        int cmp = comparatorK.compare(key, node.key);
        if (cmp == 0) {
            Node mergeRes = merge(node.left, node.right);
            replace(node, mergeRes);
            size--;
            return true;

        } else if (cmp > 0) {
            res = removeByKey(node.right, key);
        } else {
            res = removeByKey(node.left, key);
        }

        node.updateCount();
        return res;
    }

    @Override
    public boolean removeByValue(V value) {
        if (root != null) {
            return removeByValue(root, value);
        }
        return false;
    }

    private boolean removeByValue(Node node, V value){
        boolean res = false;
        if (node == null) {
            return false;
        }

        int cmp = comparatorV.compare(value, node.value);
        if (cmp == 0) {
            Node mergeRes = merge(node.left, node.right);
            replace(node, mergeRes);
            size--;
            return true;
        } else {
            res = removeByValue(node.right, value) || removeByValue(node.left, value);
        }

        node.updateCount();
        return res;
    }

    @Override
    public boolean containsValue(V value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, V value) {
        boolean res = false;
        if (node == null) {
            return false;
        }
        int cmp = comparatorV.compare(value, node.value);
        if (cmp == 0) {
            return true;
        } else {
            res = containsValue(node.right, value) || containsValue(node.left, value);
        }
        return res;
    }
    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    @Override
    public void reset() {
        this.size = 0;
        this.root = null;
    }

    private boolean containsKey(Node node, K key){
        if (node == null) {
            return false;
        }
        int cmp = comparatorK.compare(key, node.key);
        if (cmp == 0) {
            return true;
        } else if (cmp > 0) {
            return containsKey(node.right, key);
        } else {
            return containsKey(node.left, key);
        }
    }

    private TreapPair<Node> split (Node node, K key) {
        if (node == null) {
            return new TreapPair<>();
        }

        Node lhs, rhs;
        int cmp = comparatorK.compare(key, node.key);
        if (cmp > 0) {
            lhs = node;
            TreapPair<Node> splitRes = split(node.right, key);
            rhs = splitRes.getRhs();
            lhs.setRightChild(splitRes.getLhs());
        } else {
            rhs = node;
            TreapPair<Node> splitRes = split(node.left, key);
            lhs = splitRes.getLhs();
            rhs.setLeftChild(splitRes.getRhs());
        }
        if (rhs != null) { rhs.updateCount();}
        if (lhs != null) { lhs.updateCount();}
        return new TreapPair<>(lhs, rhs);
    }

    private Node merge(Node lhs, Node rhs) {
        if (lhs == null) {
            return rhs;
        }

        if (rhs == null) {
            return lhs;
        }

        Node res;

        if (lhs.priority >= rhs.priority) {
            res = lhs;
            Node mergeRes = merge(lhs.right, rhs);
            res.setRightChild(mergeRes);
        } else {
            res = rhs;
            Node mergeRes = merge(lhs, rhs.left);
            res.setLeftChild(mergeRes);
        }
        res.updateCount();
        return res;
    }

    private void replace(Node node, Node newNode) {
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
            string = string + "K=" + node.key + ":V=" + node.value + "; ";
            string = string + toString(node.right);
        }
        return string;
    }
}
