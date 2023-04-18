class TreeNodes {
    int val;
    TreeNodes left;
    TreeNodes right;
    TreeNodes(int x) { val = x; }
}
class Solutions {
    public static int minDepth(TreeNodes root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
}

public class LC_111 {

	public static void main(String[] args) {
		 // Construct a binary tree
        TreeNodes root = new TreeNodes(1);
        root.left = new TreeNodes(2);
        root.right = new TreeNodes(3);
        root.left.left = new TreeNodes(4);
        root.left.right = new TreeNodes(5);
        
        // Test the minDepth method
        int minDepth = Solutions.minDepth(root);
        System.out.println("Minimum depth of the binary tree: " + minDepth);
	}

}
