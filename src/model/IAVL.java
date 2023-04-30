public interface IAVL < K extends Comparable<K>,V> {

    void insertNode(K key,  V value);
    void delete(K key);
    void AVLRebalance(Node<K,V> current);
    void rightRotation(Node<K,V> current);
    void leftRotation(Node<K,V> current);

}
