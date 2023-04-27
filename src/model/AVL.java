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


    

    public String printTree(){
        if (root == null) return "Raiz vacia";
        else return printTreeInternal(root);
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

