import java.util.Random;

public class BinarySortTreeExperiment {
    
    public static void main(String[] args) {
        Random random = new Random();
        BinarySortTree tree = new BinarySortTree();
        for (int i = 0; i < 1023; i++) {
            tree.treeInsert(random.nextDouble());
        }
        int numLeaves = countLeaves(tree.root);
        int sumDepth = sumLeafDepths(tree.root, 0);
        int maxDepth = maxLeafDepth(tree.root, 0);
        double avgDepth = (double) sumDepth / numLeaves;
        System.out.println("Number of leaves: " + numLeaves);
        System.out.println("Average depth of leaves: " + avgDepth);
        System.out.println("Maximum depth of leaves: " + maxDepth);
    }
    
    public static int countLeaves(BinarySortTree.TreeNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return countLeaves(node.left) + countLeaves(node.right);
    }
    
    public static int sumLeafDepths(BinarySortTree.TreeNode node, int depth) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return depth;
        }
        return sumLeafDepths(node.left, depth + 1) + sumLeafDepths(node.right, depth + 1);
    }
    
    public static int maxLeafDepth(BinarySortTree.TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }
        if (node.left == null && node.right == null) {
            return depth;
        }
        int leftDepth = maxLeafDepth(node.left, depth + 1);
        int rightDepth = maxLeafDepth(node.right, depth + 1);
        return Math.max(leftDepth, rightDepth);
    }
    
    private static class BinarySortTree {
        
        private TreeNode root;
        
        public void treeInsert(double value) {
            if (root == null) {
                root = new TreeNode(value);
            } else {
                root.treeInsert(value);
            }
        }
        
        private static class TreeNode {
            
            private double data;
            private TreeNode left;
            private TreeNode right;
            
            public TreeNode(double data) {
                this.data = data;
            }
            
            public void treeInsert(double value) {
                if (value <= data) {
                    if (left == null) {
                        left = new TreeNode(value);
                    } else {
                        left.treeInsert(value);
                    }
                } else {
                    if (right == null) {
                        right = new TreeNode(value);
                    } else {
                        right.treeInsert(value);
                    }
                }
            }
        }
    }
}
