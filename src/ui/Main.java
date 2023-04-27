public class Main {
    public static void main(String[] args) {

        AVL<Integer,Integer> tree = new AVL();
        tree.insertNode(9,2);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(18,9);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(1,7);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(-3,5);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(-2,5);
        System.out.println(tree.printTreeByLevel());
        tree.delete(18);
        System.out.println(tree.printTreeByLevel());

        tree.insertNode(5,5); // Problema de insertion
        System.out.println(tree.printTreeByLevel());


        tree.delete(1); // Problema
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(10,5);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(15,5);
        System.out.println(tree.printTreeByLevel());
        System.out.println("Arbol - Print ascendente");
        System.out.println(tree.printTree());
    }
}