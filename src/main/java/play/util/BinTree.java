package play.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huanghaifeng
 */
@Slf4j
public class BinTree {

    @Data
    @AllArgsConstructor
    public static class Node {

        private int val;
        private Node left;
        private Node right;
    }

    public static void preOrder(BinTree.Node root, List<Integer> result) {
        if (root == null) {
            return;
        }

        result.add(root.getVal());
        preOrder(root.getLeft(), result);
        preOrder(root.getRight(), result);
    }

    public static void preOrderStack(Node root, List<Integer> result) {
        LinkedList<Node> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                result.add(root.getVal());
                stack.push(root);
                root = root.getLeft();
            }

            root = stack.pop().getRight();
        }
    }

    public static void inOrder(BinTree.Node root, List<Integer> result) {
        if (root == null) {
            return;
        }

        inOrder(root.getLeft(), result);
        result.add(root.getVal());
        inOrder(root.getRight(), result);
    }

    public static void inOrderStack(Node root, List<Integer> result) {
        LinkedList<Node> stack = new LinkedList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }

            root = stack.pop();
            result.add(root.getVal());
            root = root.getRight();
        }
    }

    public static void postOrder(BinTree.Node root, List<Integer> result) {
        if (root == null) {
            return;
        }

        postOrder(root.getLeft(), result);
        postOrder(root.getRight(), result);
        result.add(root.getVal());
    }

    public static void postOrderStack(Node root, List<Integer> result) {
        LinkedList<Node> stack = new LinkedList<>();
        Node pre = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }

            root = stack.pop();
            if (root.getRight() == null || root.getRight().equals(pre)) {
                result.add(root.getVal());
                pre = root;
                root = null;
            } else {
                stack.push(root);
                root = root.getRight();
            }
        }
    }

    private static void levelOrder(Node root, List<Integer> result) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Node tmp = queue.poll();
                if (tmp == null) {
                    break;
                }
                result.add(tmp.getVal());
                if (tmp.getLeft() != null) {
                    queue.offer(tmp.getLeft());
                }
                if (tmp.getRight() != null) {
                    queue.offer(tmp.getRight());
                }
            }
        }
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        Node n1 = new Node(1, null, null);
        Node n2 = new Node(2, null, null);
        Node n3 = new Node(3, null, null);
        Node n4 = new Node(4, null, null);
        Node n5 = new Node(5, null, null);

        Node root = n3;
        n3.setLeft(n1);
        n3.setRight(n4);
        n1.setLeft(n2);
        n4.setRight(n5);

        log.info("root={}", root);

        ArrayList<Integer> res1 = new ArrayList<>();
        preOrder(root, res1);
        log.info("res1={}", res1);
        res1.clear();
        preOrderStack(root, res1);
        log.info("res1={}", res1);

        ArrayList<Integer> res2 = new ArrayList<>();
        inOrder(root, res2);
        log.info("res2={}", res2);
        res2.clear();
        inOrderStack(root, res2);
        log.info("res2={}", res2);

        ArrayList<Integer> res3 = new ArrayList<>();
        postOrder(root, res3);
        log.info("res3={}", res3);
        res3.clear();
        postOrderStack(root, res3);
        log.info("res3={}", res3);

        ArrayList<Integer> res4 = new ArrayList<>();
        levelOrder(root, res4);
        log.info("res4={}", res4);
    }
}
