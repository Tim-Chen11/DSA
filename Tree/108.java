class Solution {
	public TreeNode sortedArrayToBST(int[] nums) {
		TreeNode root = traversal(nums, 0, nums.length - 1);
		return root;
	}

	private TreeNode traversal(int[] nums, int left, int right) {
		if (left > right) return null;

		int mid = left + ((right - left) >> 1);
		TreeNode root = new TreeNode(nums[mid]);
		root.left = traversal(nums, left, mid - 1);
		root.right = traversal(nums, mid + 1, right);
		return root;
	}
}