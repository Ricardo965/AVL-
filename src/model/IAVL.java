public interface IAVL < K extends Comparable<K>,T> {

    void insert(Node<K,T> nodeToAdd);
    void delete(K key);
    void AVLRebalance(Node<K,T> current);
    void rightRotate();
    void leftRotate();

}
