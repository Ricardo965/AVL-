import java.io.*;
import java.util.List;

public class Main {
    static String folder = "data";

    public static void main(String[] args) {



        AVL<Integer,Integer> tree = new AVL();
        tree.insertNode(2,2);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(9,9);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(7, 7);
        System.out.println(tree.printTreeByLevel());
        tree.insertNode(5,5);
        System.out.println(tree.printTreeByLevel());
        tree.delete(9);
        System.out.println(tree.printTreeByLevel());


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