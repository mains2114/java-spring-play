package play.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huanghaifeng
 */
@Slf4j
public class BinTree {

    @Data
    public static class Node {

        private int val;
        private Node left;
        private Node right;
        private Node parent;

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        Node(int val, Node parent) {
            this.val = val;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "{" + val +
                ", L=" + left +
                ", R=" + right +
                '}';
        }
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

    public static void levelOrder(Node root, List<Integer> result) {
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

    public static void levelPrint(Node root) {
        List<List<Integer>> lines = new ArrayList<>();

        Node empty = new Node(-1);

        int height = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        lines.add(Lists.newArrayList(root.val));
        boolean hasChild = root.left != null || root.right != null;
        while (!queue.isEmpty() && hasChild) {
            height++;
            hasChild = false;
            ArrayList<Integer> line = new ArrayList<>();
            int queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                Node tmp = queue.poll();
                if (tmp == null) {
                    break;
                }
                // result.add(tmp.getVal());
                if (tmp.getLeft() != null) {
                    queue.offer(tmp.getLeft());
                    line.add(tmp.left.val);
                    hasChild = true;
                } else {
                    queue.offer(empty);
                    line.add(-1);
                }

                if (tmp.getRight() != null) {
                    queue.offer(tmp.getRight());
                    line.add(tmp.right.val);
                    hasChild = true;
                } else {
                    queue.offer(empty);
                    line.add(-1);
                }
            }
            lines.add(line);
        }

        ArrayList<String> lines2 = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            StringBuilder sb = new StringBuilder();
            if (i > 0) {
                sb.append(String.format("%"+((2<<i-1)-1)+"s", ""));
            }
            List<Integer> tmpLine = lines.get(lines.size() - i - 1);
            for (int j = 0; j < tmpLine.size(); j++) {
                sb.append(formatInt(tmpLine.get(j))).append(String.format("%"+((2<<i)-1)+"s", ""));
            }
            lines2.add(0, sb.toString());
        }

        System.out.println(String.join("\n", lines2));
    }

    private static String formatInt(int i) {
        return i == -1 ? "#" : i + "";
    }

    public static void insert(Node root, int val) {
        if (root == null) {
            return;
        }

        Node t = root;
        Node parent;
        do {
            parent = t;
            if (val < t.getVal()) {
                t = t.getLeft();
            } else {
                t = t.getRight();
            }
        } while (t != null);

        Node e = new Node(val, parent);
        if (val < parent.getVal()) {
            parent.setLeft(e);
        } else {
            parent.setRight(e);
        }
    }

    public static Node search(Node root, int val) {
        if (root == null) {
            return null;
        }

        Node t = root;
        do {
            if (val < t.getVal()) {
                t = t.getLeft();
            } else if (val > t.getVal()) {
                t = t.getRight();
            } else {
                return t;
            }
        } while (t != null);

        return null;
    }

    public static void remove(Node root, int val) {
        Node target = search(root, val);
        if (target == null) {
            return;
        }

        if (target.right == null) {
            if (target.parent.left == target) {
                target.parent.left = target.left;
            } else {
                target.parent.right = target.left;
            }
            if (target.left != null) {
                target.left.parent = target.parent;
            }
        } else if (target.left == null) {
            if (target.parent.left == target) {
                target.parent.left = target.right;
            } else {
                target.parent.right = target.right;
            }
            target.right.parent = target.parent;
        } else {
            // 找到前置节点
            Node t = target.left;
            while (t.right != null) {
                t = t.right;
            }

            // 将前置节点的值复制到当前节点
            target.val = t.val;
            // 将前置节点的左子树添加到父节点，替换前置节点的位置
            if (t == t.parent.left) {
                t.parent.left = t.left;
            } else {
                t.parent.right = t.left;
            }
            if (t.left != null) {
                t.left.parent = t.parent;
            }

            // 释放t
            t.left = t.parent = null;
        }
    }

    public static void rotateRight(Node t) {
        Node top = t.left;

        t.left = top.right;
        top.right = t;
        top.parent = t.parent;
        if (t.parent != null) {
            if (t.parent.left.equals(t)) {
                t.parent.left = top;
            } else {
                t.parent.right = top;
            }
        }
        t.parent = top;
    }

    public static void main(String[] args) {
//        testOrder();
        testInsert();
    }

    private static void testInsert() {
        Node root = new Node(3);
        insert(root, 1);
        insert(root, 2);
        insert(root, 4);
        insert(root, 5);
        log.info("tree = {}", root);
        List<Integer> result = new ArrayList<>();
        inOrderStack(root, result);
        log.info("inOrder = {}", result);

        remove(root, 3);
        log.info("tree = {}", root);
        result.clear();
        levelOrder(root, result);
        log.info("levelOrder = {}", result);

        levelPrint(root);
    }

    private static void testOrder() {
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
