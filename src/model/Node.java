public class Node <K extends Comparable<K>,T>{

    private  Node<K,T> parent;
    private  Node<K,T> right;
    private  Node<K,T> left;
    private K key;
    private T value;

    public Node<K, T> getParent() {
        return parent;
    }

    public void setParent(Node<K, T> parent) {
        this.parent = parent;
    }

    public Node<K, T> getRight() {
        return right;
    }

    public void setRight(Node<K, T> right) {
        this.right = right;
    }

    public Node<K, T> getLeft() {
        return left;
    }

    public void setLeft(Node<K, T> left) {
        this.left = left;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
