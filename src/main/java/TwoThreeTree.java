
import java.lang.reflect.Array;
import java.util.Comparator;


/*
2-3 Дерево. Сбалансированное дерево, листья дерева на одной высоте
добавление-O(Log n)
удаление по value-O(Log n)
поиск по value-O(Log n)
*/
public class TwoThreeTree <K, V extends Comparable> implements InterfaceClassAlgorithm <K, V> {

    private class Node {
        V[] values;
        Node[] childs;
        int countValue;
        boolean isLeaf;

        public Node(boolean isLeaf, V val) {
            this.countValue = 0;
            this.values = (V[]) Array.newInstance(val.getClass(), 3);
            this.childs = (Node[]) Array.newInstance(this.getClass(), this.values.length + 1);
            this.isLeaf = isLeaf;
        }
    }

    private Node root;
    private int size;
    private final Comparator<V> comparator;

    public TwoThreeTree(V val){
        size = 0;
        root = new Node(true, val);
        this.comparator = V::compareTo;
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
        add(root, value);
        if (root.countValue == 3) {
            Node newRoot = new Node(false, value);
            newRoot.childs[0] = root;

            this.root = newRoot;
            split(root, 0);
        }
    }

    private void add(Node node, V value){
        if (node.isLeaf){
            int i = node.countValue - 1;
            while (i >= 0 && comparator.compare(node.values[i], value) > 0){
                node.values[i + 1] = node.values[i];
                i--;
            }
            node.values[i + 1] = value;
            node.countValue += 1;
            size++;
        } else {
            int i = 0;
            while(i < node.countValue && comparator.compare(node.values[i], value) < 0){
                i++;
            }
            add(node.childs[i], value);
            checkNodeSize(node, i);
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
        return findValue(root, value);
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

    private void checkNodeSize(Node node, int childIndex){
        Node child = node.childs[childIndex];
        if (child.countValue == 3 ) {
            split(node, childIndex);
        } else if (node.countValue == 0 || child.countValue == 0) {
            fixEmptyNode(node, childIndex);
        }
    }

    private void split(Node node, int childIndex){
        Node lhs = node.childs[childIndex];
        Node rhs = new Node(lhs.isLeaf, (V)(Integer)0);
        lhs.countValue = rhs.countValue = 1;

        rhs.values[0] = lhs.values[2];
        rhs.childs[0] = lhs.childs[2];
        rhs.childs[1] = lhs.childs[3];

        lhs.childs[2] = lhs.childs[3] = null;

        for (int i = node.countValue - 1; i >= childIndex; --i){
            node.values[i + 1] = node.values[i];
        }

        for (int i = node.countValue; i >= childIndex + 1; --i){
            node.childs[i + 1] = node.childs[i];
        }

        node.values[childIndex] = lhs.values[1];
        node.childs[childIndex + 1] = rhs;
        node.countValue += 1;
    }

    private boolean remove(Node node, V value) {

        if (node.isLeaf) {
            int i = 0;
            while (i < node.countValue && comparator.compare(node.values[i], value) < 0) {
                ++i;
            }
            if (i == node.countValue || comparator.compare(node.values[i], value) != 0) {
                return false; //value not found!
            }

            for (int n = i + 1; n < node.countValue; ++n){
                node.values[n - 1] = node.values[n];
            }
            node.countValue -= 1;
            --size;
        } else {
            int i = 0;
            while (i < node.countValue && comparator.compare(node.values[i], value) < 0) {
                ++i;
            }
            if (i < node.countValue && comparator.compare(node.values[i], value) == 0) {
                V newValue = findSmallValue(node.childs[i + 1]);
                node.values[i] = newValue;
                remove(node.childs[i + 1], newValue);
                checkNodeSize(node, i + 1);
            } else {
                remove(node.childs[i], value);
                checkNodeSize(node, i);
            }
        }
        return true;
    }

    private void fixEmptyNode(Node node, int emptyChildIndex) {
        Node emptyChild = node.childs[emptyChildIndex];
        if (emptyChildIndex < node.countValue) {
            Node rch = node.childs[emptyChildIndex + 1];
            if (rch.countValue == 1) {
                merge(node, emptyChildIndex);
            } else {
                emptyChild.values[0] = node.values[emptyChildIndex];
                emptyChild.childs[1] = rch.childs[0];
                emptyChild.countValue += 1;
                node.values[emptyChildIndex] = rch.values[0];

                for (int i = 1; i < rch.countValue; ++i){
                    rch.values[i-1] = rch.values[i];
                }

                for (int i = 1; i <= rch.countValue; ++i){
                    rch.childs[i-1] = rch.childs[i];
                }

                rch.childs[rch.countValue] = null;
//                rch.values[rch.countKey] = null;
                rch.countValue -= 1;
            }
        } else {
            Node lch = node.childs[emptyChildIndex - 1];
            if (lch.countValue == 1) {
                merge(node, emptyChildIndex - 1);
            } else {
                emptyChild.values[0] = node.values[emptyChildIndex - 1];
                emptyChild.childs[1] = emptyChild.childs[0];
                emptyChild.childs[0] = lch.childs[lch.countValue];
                emptyChild.countValue += 1;

                node.values[emptyChildIndex - 1] = lch.values[lch.countValue - 1];
                lch.childs[lch.countValue] = null;
//                lch.values[lch.countKey - 1] = null; //
                lch.countValue -= 1;
            }
        }
    }

    private void merge (Node node, int keyIndex){
        Node curChild = node.childs[keyIndex];
        Node rightChild = node.childs[keyIndex + 1];

        curChild.values[curChild.countValue] = node.values[keyIndex];
        for (int i = 0; i < rightChild.countValue; ++i){
            curChild.values[curChild.countValue + 1 + i] = rightChild.values[i];
        }

        for (int i = 0; i <= rightChild.countValue; ++i){
            curChild.childs[curChild.countValue + 1 + i] = rightChild.childs[i];
        }

        curChild.countValue = curChild.countValue + 1 + rightChild.countValue;

        for (int i = keyIndex + 1; i < node.countValue; ++i) {
            node.values[i - 1] = node.values[i];
        }

        for (int i = keyIndex + 2; i <= node.countValue; ++i) {
            node.childs[i - 1] = node.childs[i];
        }

        node.childs[node.countValue] = null;
//        node.values[node.countValue] = null; //
        node.countValue -= 1;
    }

    private boolean findValue(Node node, V value){
        boolean res = false;
            for (int i = 0; i < node.countValue; ++i){
                int cmp = comparator.compare(node.values[i], value);
                if (cmp == 0 || res) {
                    return true;
                }
                if (!node.isLeaf) {
                    if (cmp < 0) {
                        res = findValue(node.childs[i + 1], value);
                    } else {
                        res = findValue(node.childs[i], value);
                    }
                }
            }
        return res;
    }

    private V findSmallValue(Node node){
        while (!node.isLeaf) {
            node = node.childs[0];
        }
        return node.values[0];
    }
}
