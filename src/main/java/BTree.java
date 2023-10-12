
import java.lang.reflect.Array;
import java.util.Comparator;

/*
B-Дерево. Сбалансированное дерево, листья дерева на одной высоте c N ключами.
добавление-O(Log n)
удаление по value-O(Log n)
поиск по value-O(Log n)
*/
public class BTree<K, V extends Comparable> implements InterfaceClassAlgorithm <K, V> {

    private class Node {
        V[] values;
        Node[] childs;
        int countValue;
        boolean isLeaf;

        boolean isFull(){
            return countValue == 2 * t - 1;
        }

        public Node(boolean isLeaf, V val) {
            this.isLeaf = isLeaf;
            this.countValue = 0;
            this.values = (V[]) Array.newInstance(val.getClass(), 2 * t -1);
            if (!this.isLeaf) {
                this.childs = (Node[]) Array.newInstance(this.getClass(), this.values.length + 1);
            }
        }
    }

    private Node root;
    private int size;
    private final int t;
    private final Comparator<V> comparator;

    public BTree(int t, V val){
        this.comparator = V::compareTo;
        this.t = t;
        size = 0;
        root = new Node(true, val);
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
        int n = 0;
        while (!node.isLeaf) {
            ++n;
            node = node.childs[0];
        }
        return n;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(K key, V value) {
        add(value);
        return true;
    }

    private void add(V value){
        if (root.isFull()) {
            Node newRoot = new Node(false, value);
            newRoot.childs[0] = this.root;

            this.root = newRoot;
            split(this.root, 0);
        }
        add(root, value);
    }

    private void add(Node node, V value){
        if (node.isLeaf){
            int i = node.countValue - 1;
            while (i >= 0 && comparator.compare(node.values[i], value) > 0){
                node.values[i + 1] = node.values[i];
                --i;
            }
            node.values[i + 1] = value;
            node.countValue += 1;
            ++size;
        } else {
            int i = 0;
            while(i < node.countValue && comparator.compare(node.values[i], value) < 0){
                ++i;
            }

            Node child = node.childs[i];
            if (child.isFull()) {
                split(node, i);
                if (comparator.compare(value, node.values[i]) > 0){
                    child = node.childs[i + 1];
                }
            }
            add(child, value);
        }
    }
    @Override
    public boolean removeByKey(K key) {
        return false;
    }

    @Override
    public boolean removeByValue(V value) {
        boolean res = false;
        res = remove(root, value);
        if (!root.isLeaf && root.countValue == 0) {
            root = root.childs[0];
        }
        return res;
    }


    @Override
    public boolean containsValue(V value) {
        if (isEmpty()) {
            return false;
        } else {
            return findValue(root, value);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void reset() {
        size = 0;
        root = new Node(true, root.values[0]);
    }


    private void split(Node node, int childIndex){
        Node lhs = node.childs[childIndex];
        Node rhs = new Node(lhs.isLeaf, (V)(Integer)0);
        lhs.countValue = rhs.countValue = t - 1;

        for (int i = 0; i < rhs.countValue; ++i){
            rhs.values[i] = lhs.values[t + i];
        }

        if (!lhs.isLeaf) {
            for (int i =0; i <= rhs.countValue; ++i){
                rhs.childs[i] = lhs.childs[t + i];
            }
        }


        for (int i = node.countValue - 1; i >= childIndex; --i){
            node.values[i + 1] = node.values[i];
        }

        for (int i = node.countValue; i > childIndex; --i){
            node.childs[i + 1] = node.childs[i];
        }

        node.values[childIndex] = lhs.values[t - 1];
        node.childs[childIndex + 1] = rhs;
        node.countValue += 1;
    }

    private boolean remove(Node node, V value) {
            int i = 0;
            while (i < node.countValue && comparator.compare(node.values[i], value) < 0) {
                ++i;
            }

            if (node.isLeaf) {
                if (i == node.countValue || comparator.compare(node.values[i], value) != 0) {
                    return false; //value not found!
                }

                for (int n = i + 1; n < node.countValue; ++n){
                    node.values[n - 1] = node.values[n];
                }
                node.countValue -= 1;
                --size;
            } else {
                Node child = node.childs[i];
                if (i < node.countValue && comparator.compare(node.values[i], value) == 0) {
                    Node rch = node.childs[i + 1];
                    if (child.countValue >= t){
                        V largeValue = findLargeValue(child);
                        node.values[i] = largeValue;
                        remove(child, largeValue);
                    } else if (rch.countValue >= t) {
                        V smallValue = findSmallValue(rch);
                        node.values[i] = smallValue;
                        remove(rch, smallValue);
                    } else {
                        merge(node, i);
                        remove(child, value);
                    }
                } else {
                    if (child.countValue >= t) {
                        remove(child, value);
                    } else {
                        if (i < node.countValue) {
                            //правый потомок существует
                            Node rch = node.childs[i + 1];
                            if (rch.countValue >= t) {
                                // rotate left
                                child.values[t - 1] = node.values[i];
                                child.countValue += 1;

                                node.values[i] = rch.values[0];
                                for (int n = 1; n < rch.countValue; ++n){
                                    rch.values[n - 1] = rch.values[n];
                                }

                                if (!child.isLeaf) {
                                    child.childs[t] = rch.childs[0];
                                    for (int n = 1; n <= rch.countValue; ++n){
                                        rch.childs[n - 1] = rch.childs[n];
                                    }
                                }

                                rch.countValue -= 1;
                                remove(child, value);

                            } else {
                                merge(node, i);
                                remove(child, value);
                            }
                        } else {
                            Node lch = node.childs[i - 1];
                            if (lch.countValue >= t) {
                                // rotate right
                                for (int n = child.countValue - 1; n >= 0; --n){
                                    child.values[n + 1] = child.values[n];
                                }

                                child.values[0] = node.values[i - 1];

                                if (!child.isLeaf) {
                                    for (int n = child.countValue; n >= 0; --n){
                                        child.childs[n + 1] = child.childs[n];
                                    }
                                    child.childs[0] = lch.childs[lch.countValue];
                                }
                                child.countValue += 1;
                                node.values[i - 1] = lch.values[lch.countValue - 1];
                                lch.countValue -= 1;

                                remove(child, value);
                            } else {
                                merge(node, i - 1);
                                remove(lch, value);
                            }
                        }
                    }
                }
            }
        return true;
    }

    private V findSmallValue(Node node) {
        while (!node.isLeaf) {
            node = node.childs[0];
        }
        return node.values[0];
    }

    private V findLargeValue(Node node) {
        while (!node.isLeaf) {
            node = node.childs[node.countValue];
        }
        return node.values[node.countValue - 1];
    }

    private void merge (Node node, int keyIndex){
        Node curChild = node.childs[keyIndex];
        Node rightChild = node.childs[keyIndex + 1];

        curChild.countValue = 2 * t -1;

        curChild.values[t - 1] = node.values[keyIndex];
        for (int i = 0; i < rightChild.countValue; ++i){
            curChild.values[t + i] = rightChild.values[i];
        }

        if (!curChild.isLeaf) {
            for (int i = 0; i <= rightChild.countValue; ++i) {
                curChild.childs[t + i] = rightChild.childs[i];
            }
        }

        for (int i = keyIndex + 1; i < node.countValue; ++i) {
            node.values[i - 1] = node.values[i];
        }

        for (int i = keyIndex + 2; i <= node.countValue; ++i) {
            node.childs[i - 1] = node.childs[i];
        }

        node.countValue -= 1;
    }

    private boolean findValue(Node node, V value){
        boolean res = false;
        int indexValue = 0;
        int indexChild = 0;
        for (int i = 0; i < node.countValue; ++i) {
            int cmp = comparator.compare(node.values[i], value);
            if (cmp <= 0) {
                indexValue = i;
                ++indexChild;
            }
        }
        int cmp = comparator.compare(node.values[indexValue], value);
        if (cmp == 0 || res) {
            return true;
        }
        if (!node.isLeaf) {
            res = findValue(node.childs[indexChild], value);
        }
        return res;
    }
}
