import java.util.LinkedList;
import java.util.Queue;

public class AVL <K extends Comparable<K>,V> implements IAVL<K,V> {
    private  Node<K,V> root;

    @Override
    public void insertNode(K key,  V value){

        Node nodeToInsert = new Node<>(key,value);

        if (root == null) {
            root = nodeToInsert;
            return;
        }

        Node father = null;
        Node<K,V> temporalFather = root;

        while (temporalFather != null){ // Este ciclo lo uso para obtener el padre al cual debo ingresar el nodo.
            father = temporalFather;
            // El orden de comparación es relavante.
            if (key.compareTo(temporalFather.getKey()) < 0) temporalFather = temporalFather.getLeft();
            else temporalFather = temporalFather.getRight();
        }

        if (nodeToInsert.getKey().compareTo(father.getKey()) < 0 ) father.setLeft(nodeToInsert);
        else father.setRight(nodeToInsert);
        nodeToInsert.setParent(father);
        AVLRebalance(nodeToInsert);
    }

    public Node<K,V> successor(Node<K,V> node){

        if (root == null) return null;
        if (node.getRight() != null) return minimum(node.getRight());

        Node<K,V> father = node.getParent();
        Node<K,V> temporal = node;

        if (father == null) return null;

        while(father.getRight() == temporal ){
            temporal = father;
            father = father.getParent();
            if (father== null) return null;
        }
        return father;
    }

    @Override
    public void delete(K key){
        if (root == null) {
            System.out.println("Arbol vacio");
            return;
        }

        Node<K,V> toDelete = search(key);
        if (toDelete == null ) {
            System.out.println("No se encontró nodo a eliminar");
            return;
        }
        Node<K,V> temporalToDelete;
        Node<K,V> connection;

        if (toDelete.getRight() == null || toDelete.getLeft() == null ) temporalToDelete = toDelete;
        else temporalToDelete = successor(toDelete);

        if (temporalToDelete.getLeft() != null) connection = temporalToDelete.getLeft();
        else connection = temporalToDelete.getRight();

        if (connection != null) connection.setParent(temporalToDelete.getParent());

        if (temporalToDelete.getParent() == null){
            root.setKey(connection.getKey());
            root.setValue(connection.getValue());
        }else{
            if (temporalToDelete == temporalToDelete.getParent().getLeft())
                temporalToDelete.getParent().setLeft(connection);
            else temporalToDelete.getParent().setRight(connection);
        }

        if (temporalToDelete != toDelete){
            toDelete.setKey(temporalToDelete.getKey());
            toDelete.setValue(temporalToDelete.getValue());
        }
        AVLRebalance(toDelete);
    }

    public Node<K,V> search(K key){
        if (root == null) return null;
        else return search(key,root);
    }

    public Node<K,V> search(K key,  Node<K,V> current){
        if (current.getKey().compareTo(key) == 0) return current;
        if (key.compareTo(current.getKey()) < 0 ) return search(key,current.getLeft());
        else return search(key,current.getRight());
    }

    public Node<K,V> minimum (){
        if (root == null) return null;
        else return minimum(root);
    }

    public Node<K,V> minimum(Node<K,V > current){
        if (current.getLeft() == null) return current;
        return minimum(current.getLeft());
    }

    private int balanceFactor(Node<K,V> current){

        int d = depth(current.getRight(), 0);
        int l = depth(current.getLeft(), 0);
        return  d - l ;
    }

    private int depth(Node<K,V> current, int n) {
        if (current == null) {
            return n-1;
        }
        if (depth(current.getRight(), n+1) >= depth(current.getLeft(), n+1)) {
            return depth(current.getRight(), n+1);
        } else {
            return depth(current.getLeft(), n+1);
        }
    }
    @Override
    public void AVLRebalance(Node<K,V> x){
        Node<K,V> current = x;
        while (current != null){
            if(Math.abs(balanceFactor(current)) > 1){
                if (balanceFactor(current) > 0){ //leftRotation hijo derecho mas gordo

                    if (balanceFactor(current.getRight()) == 1){
                        leftRotation(current);
                    } else if (balanceFactor(current.getRight()) == -1) {
                        rightRotation(current.getRight());
                        leftRotation(current);
                    } else {
                        leftRotation(current);
                    }

                } else { //rightRotation
                    if (balanceFactor(current.getLeft()) == 1){
                        leftRotation(current.getLeft());
                        rightRotation(current);
                    } else if (balanceFactor(current.getLeft()) == -1) {
                        rightRotation(current);
                    } else {
                        rightRotation(current);
                    }
                }
            }

            current = current.getParent();
        }
    }

    public  String printTreeByLevel(){
        if(root == null) return "Arbol vacio";
        String txt = "";
        Queue<Node<K,V>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            txt += node.getKey() + " ";

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return txt;
    }
    @Override
    public void rightRotation (Node<K,V> current){
        Node<K,V> tem = null;
        if (current.getLeft().getRight() != null ) tem = current.getLeft().getRight();
        // Asignar padre
        if (current.getParent() == null) root = current.getLeft();
        else {
            if (current.getParent(). getRight() == current) current.getParent().setRight(current.getLeft());
            else current.getParent().setLeft(current.getLeft());
        }

        current.getLeft().setParent(current.getParent());
        current.setParent(current.getLeft());
        current.getParent().setRight(current);

        current.setLeft(tem);
        if(tem != null) tem.setParent(current);
    }
    @Override
    public void leftRotation (Node<K,V> current){
        Node<K,V> tem = null;

        if (current.getRight().getLeft() != null ) tem = current.getRight().getLeft();

        if (current.getParent() == null) root = current.getRight();
        else {
            if (current.getParent(). getRight() == current) current.getParent().setRight(current.getRight());
            else current.getParent().setLeft(current.getRight());
        }

        current.getRight().setParent(current.getParent());
        current.setParent(current.getRight());
        current.getParent().setLeft(current);

        current.setRight(tem);
        if(tem != null) tem.setParent(current);


    }
    public Node<K, V> getRoot() {
        return root;
    }
    public void setRoot(Node<K, V> root) {
        this.root = root;
    }

}

