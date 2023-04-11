import java.util.Arrays;
import java.util.Random;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
/**
 * Solution class for converting a sorted array to a height-balanced binary search tree.
 */
class Solution {
    int[] nums;

    /**
     * Helper function for constructing a height-balanced binary search tree.
     * @param left The left boundary of the current subarray.
     * @param right The right boundary of the current subarray.
     * @return The root node of the constructed subtree.
     */
    public TreeNode helper(int left, int right) {
        if (left > right) return null;

        // always choose left middle node as a root
        int p = (left + right) / 2;

        // preorder traversal: node -> left -> right
        TreeNode root = new TreeNode(nums[p]);
        root.left = helper(left, p - 1);
        root.right = helper(p + 1, right);
        return root;
    }

    /**
     * Main function for converting a sorted array to a height-balanced binary search tree.
     * @param nums The sorted input array.
     * @return The root node of the constructed height-balanced binary search tree.
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        this.nums = nums;
        return helper(0, nums.length - 1);
    }
}
public class SortedListToBST {
	// Helper method to check if a binary tree is a valid binary search tree
	private static boolean isValidBST(TreeNode node, Integer lower, Integer upper) {
        if (node == null) return true;

        int val = node.val;
        if (lower != null && val <= lower) return false;
        if (upper != null && val >= upper) return false;

        if (!isValidBST(node.right, val, upper)) return false;
        if (!isValidBST(node.left, lower, val)) return false;
        return true;
    }
	// Helper method to calculate the height of a binary tree
    private static int height(TreeNode node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }
    // Helper method to check if a binary tree is height-balanced
    private static boolean isBalanced(TreeNode node) {
        if (node == null) return true;

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return Math.abs(leftHeight - rightHeight) <= 1
                && isBalanced(node.left)
                && isBalanced(node.right);
    }
	

	public static void main(String[] args) {
		// Generate a sorted array of size 1000
        int[] testArray = new int[1000];
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            testArray[i] = rand.nextInt(1000);
        }
        Arrays.sort(testArray);

        Solution solution = new Solution();
        TreeNode root = solution.sortedArrayToBST(testArray);
        
        
       

	}

}
