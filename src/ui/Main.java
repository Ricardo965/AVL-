public class Main {
    public static void main(String[] args) {

        AVL tree = new AVL();
        tree.insertNode(15, "a");
        tree.insertNode(6, "a");
        tree.insertNode(7, "a");
        tree.insertNode(10, "a");
        tree.insertNode(17, "a");


        System.out.println(tree.longestPath());
        System.out.println(tree.balancedFactor(tree.getRoot()));

    }
}