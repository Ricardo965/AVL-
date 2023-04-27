import java.util.LinkedList;
import java.util.Queue;

public class AVL <K extends Comparable,V> {
    private  Node<K,V> root;

    /**
     * Consiste en una busqueda del padre al cual se va a conectar el nodo a insertar.
     * @param key
     * @param value
     */
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
        //AVLRebalance();
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
        return depth(current.getRight(), 0) - depth(current.getLeft(), 0);
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

    private void AVLRebalance(Node<K,V> x){
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
                        leftRotation(current);
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


    

    public String printTree(){
        if (root == null) return "Raiz vacia";
        else return printTreeInternal(root);
    }

    public  String printTreeByLevel(){
        if(root == null) return "Arbol vacio";
        String txt = "";
        Queue<Node<K,V>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            txt += node.getValue() + " ";

            if (node.getLeft() != null) {
                queue.add(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.add(node.getRight());
            }
        }
        return txt;
    }


    public int balancedFactor(Node current){
        int left =0, right = 0;

        if (current.getLeft() != null) left = longestPath(current.getLeft(), 0);
        if (current.getRight() != null) right = longestPath(current.getRight(), 0);

        return  right - left ;

    }

    public void leftRotation (Node current){
        Node tem = null;
        if (current.getRight().getRight() != null ) tem = current.getRight().getRight();

        if (current.getParent() == null) root = current.getRight();
        else {
            if (current.getParent(). getRight() == current) current.getParent().setRight(current.getRight());
            else current.getParent().setLeft(current.getLeft());
        }

        current.getRight().setParent(current.getParent());
        current.setParent(current.getRight());
        current.getParent().setLeft(current);

        current.setRight(tem);
    }

    public void rightRotation (Node current){
        Node tem = null;

        if (current.getLeft().getRight() != null ) tem = current.getLeft().getRight();

        if (current.getParent() == null) root = current.getLeft();
        else {
            if (current.getParent(). getRight() == current) current.getParent().setRight(current.getLeft());
            else current.getParent().setLeft(current.getLeft());
        }

        current.getLeft().setParent(current.getParent());
        current.setParent(current.getLeft());
        current.getParent().setRight(current);

        current.setLeft(tem);
    }

    public int longestPath(){
        if (root==null)return 0;
        else return (longestPath(root,0) +1);
    }

    private int longestPath (Node current, int counter){

        if (current.getLeft() == null && current.getRight() == null){
            return counter;
        }
        if (current.getRight() != null && current.getLeft()==null) { //Unico camino por la derecha
            return longestPath(current.getRight(),counter+1);
        }

        if (current.getRight() == null && current.getLeft() != null){
            return longestPath(current.getLeft(),counter+1);
        }

        if (current.getLeft() != null && current.getRight() != null){
            if (longestPath(current.getRight(),counter+1) > longestPath(current.getLeft(),counter+1)){
                return longestPath(current.getRight(),counter+1);
            }else return longestPath(current.getLeft(),counter+1);

        }else return 0;// unreacheble
    }

    private String printTreeInternal(Node current){
        if (current == null)return "";
        return printTreeInternal(current.getLeft()) + current.getKey() + ", " + printTreeInternal(current.getRight());
    }
    public Node<K, V> getRoot() {
        return root;
    }
    public void setRoot(Node<K, V> root) {
        this.root = root;
    }
}

