public class Main {
    public static void main(String[] args) {

        AVL<Integer,Integer> tree = new AVL();
        tree.insertNode(2,2);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(9,9);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(7,7);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(5,5);
        System.out.println(tree.printTreeByLevel());
        tree.delete(9);
        System.out.println(tree.printTreeByLevel());


    }
}