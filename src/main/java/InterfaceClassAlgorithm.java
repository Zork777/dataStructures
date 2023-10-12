public interface InterfaceClassAlgorithm<K, V> {
        int getSize();
        int heightTree();
        boolean isEmpty();
        boolean add(K key, V value);
        boolean removeByKey (K key);
        boolean removeByValue (V value);
        boolean containsValue(V value);
        boolean containsKey(K key);

        void reset();

}
