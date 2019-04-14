import java.util.*;

public class BinaryTreeTool {
/**
 * FileName: BinaryTreeTool.java
 * Binary tree tool: by edges input, get TreeView, maxDepth, 
 * preOrder, inOrder, postOrder, BFS and DFS, and judge whether
 * it is balanced tree, BST, complete tree, and full tree.
 * 
 * @author Defu Shi, Xing Zheng, and Jun Qu
 * @Time 2019-4-14 10:26
 *
 */
	
	public static class TreeNode {
	/**
	 * Inner class: binary tree node
	 * 
	 * @property val: int, value of node
	 * @property left: left child of the tree
	 * @property right: right child of the tree
	 */
		int val;
		TreeNode left = null;
		TreeNode right = null;
	    
		TreeNode(int val) {
			this.val = val;
		}
	}
	
	public static void main(String[] args) {
	/**
         * main function
         * 
         * @param n: number of nodes
         * @param a: edges of the tree, (a[i][0], a[i][1]) means form a[i][1] to a[i][0]，
         * left is in front of the right.
         * @param root: root of the tree
         * @return void
         */
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[][] a = new int[n-1][2];
		for(int i=0; i<n-1; i++) {
			a[i][0] = in.nextInt();
			a[i][1] = in.nextInt();
		}
		in.close();
		TreeNode root = edgesToTree(a);
		
		System.out.println("TreeView: " + print(root));		
		System.out.println("maxDepth: " + maxDepth(root));
        
		System.out.println("preOrder: " + preOrderRecur(root));
		System.out.println("inOrder: " + inOrderRecur(root));
		System.out.println("postOrder: " + postOrderRecur(root));
		System.out.println("BFS: " + levelTraverse(root));
		System.out.println("DFS: "+ depthFirst(root));
		
		System.out.println("isBalanced? " + isBalanced(root));
		System.out.println("isBST? " + isBST(root));
		System.out.println("isComplete? " + isComplete(root));
		System.out.println("isFull? " + isFull(root));
		
	}
    
	public static TreeNode edgesToTree(int[][] a) {    
        /**
         * rebuild binary tree by edges.
         * 
         * @param map: <Integer, TreeNode>, map from value to node
         * @param set: <Integer>, node value
         * @return TreeNode: root
         */
		HashMap<Integer, TreeNode> map = new HashMap<>();
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			if (!set.contains(a[i][1])) {
				set.add(a[i][1]);
				TreeNode n1 = new TreeNode(a[i][1]);
				map.put(a[i][1], n1);
			}
			if (!set.contains(a[i][0])) {
				set.add(a[i][0]);
				TreeNode n2 = new TreeNode(a[i][0]);
				map.put(a[i][0], n2);
			}
			if (map.get(a[i][1]).left != null) {
				map.get(a[i][1]).right = map.get(a[i][0]);
			} else {
				map.get(a[i][1]).left = map.get(a[i][0]);
			}
		}
		for(Integer integer : set) {
			int j = 0;
			while(j < a.length) {
				if(integer == a[j][0])
					break;
				j++;
			}
			if(j == a.length)
				return map.get(integer);
		}
		return null;
	}
    
	public static ArrayList<ArrayList<Integer> > print(TreeNode root){
	/**
	 * print the tree by level.
	 * 
	 * @param list: ArrayList<ArrayList<Integer>>
	 * @param depth: depth of current node
         * @return list: ArrayList<ArrayList<Integer>>
	 */
		ArrayList<ArrayList<Integer>> list = new ArrayList<>();
		printCore(root, 1, list);
		return list;
	}
	
	private static void printCore(TreeNode root, int depth, ArrayList<ArrayList<Integer>> list) {
		if(root == null) return;
		if(depth > list.size())
		list.add(new ArrayList<Integer>());
		list.get(depth -1).add(root.val);
		printCore(root.left, depth + 1, list);
		printCore(root.right, depth + 1, list);
	}

	public static int maxDepth(TreeNode root) {
        /**
         * find the max depth of three tree.
         * 
         * @return maxDepth: int
         */
		if (root == null) {
			return 0;
		}
		int left = maxDepth(root.left);
		int right = maxDepth(root.right);
		return Math.max(left, right) + 1;
	}
    
	public static ArrayList<Integer> preOrderRecur(TreeNode root) {
	/**
	 * print the preorder traversal by recursion.
	 * 
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root == null) {
			return list;
		}
		
		list.add(root.val);
		list.addAll(preOrderRecur(root.left));
		list.addAll(preOrderRecur(root.right));
		return list;
	}
	
	public static ArrayList<Integer> inOrderRecur(TreeNode root) {
	/**
	 * print the inorder traversal by recursion.
	 * 
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root == null) {
			return list;
		}
		
		list.addAll(inOrderRecur(root.left));
		list.add(root.val);
		list.addAll(inOrderRecur(root.right));
		return list;
	}

	public static ArrayList<Integer> postOrderRecur(TreeNode root) {
	/**
	 * print the postorder traversal by recursion.
	 * 
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root == null) {
			return list;
		}
		
		list.addAll(postOrderRecur(root.left));
		list.addAll(postOrderRecur(root.right));
		list.add(root.val);
		return list;
	}
	
	public static ArrayList<Integer> preOrderUnRecur(TreeNode root) {
	/**
	 * print the preorder traversal by stack.
	 * 
	 * @param stack: Stack<TreeNode>
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			while (!stack.isEmpty() || root != null) {
				if (root != null) {
					list.add(root.val);
					stack.push(root);
					root = root.left;
				} else {
					root = stack.pop();
					root = root.right;
				}
			}
		}
		return list;
	}
	
	public static ArrayList<Integer> inOrderUnRecur(TreeNode root) {
	/**
	 * print the inorder traversal by stack.
	 * 
	 * @param stack: Stack<TreeNode>
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			while (!stack.isEmpty() || root != null) {
				if (root != null) {
					stack.push(root);
					root = root.left;
				} else {
					root = stack.pop();
					list.add(root.val);
					root = root.right;
				}
			}
		}
		return list;
	}
	
	public static ArrayList<Integer> postOrderUnRecur1(TreeNode root) {
	/** print the postorder traversal by two stacks.
	 * 
	 * @param s1: Stack<TreeNode>
	 * @param s2: Stack<TreeNode>
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root != null) {
			Stack<TreeNode> s1 = new Stack<>();
			Stack<TreeNode> s2 = new Stack<>();
			s1.push(root);
			while (!s1.isEmpty()) {
				root = s1.pop();
				s2.push(root);
				if (root.left != null) {
					s1.push(root.left);
				}
				if (root.right != null) {
					s1.push(root.right);
				}
			}
			while (!s2.isEmpty()) {
				list.add(s2.pop().val);
			}
		}
		return list;
	}

	public static ArrayList<Integer> postOrderUnRecur2(TreeNode root) {
	/** print the postorder traversal by one stack.
	 * 
	 * @param stack: Stack<TreeNode>
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			stack.push(root);
			TreeNode node = null;
			while (!stack.isEmpty()) {
				node = stack.peek();
				if (node.left != null && root != node.left && root != node.right) {
					stack.push(node.left);
				} else if (node.right != null && root != node.right) {
					stack.push(node.right);
				} else {
					list.add(stack.pop().val);
					root = node;
				}
			}
		}
		return list;
	}
	
	public static ArrayList<Integer> levelTraverse(TreeNode root) { 
	/** print the level-order traversal by queue.
	 * 
	 * @param queue: Queue<TreeNode>
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root == null) {  
			return list;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
			list.add(node.val);
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
		return list;
	}  
	
	public static ArrayList<Integer> depthFirst(TreeNode root) {
	/** print the depth-first-order traversal by stack, adapting preorder traversal.
	 * 
	 * @param stack: Stack<TreeNode>
	 * @return list: ArrayList<Integer>
	 */
		ArrayList<Integer> list = new ArrayList<>();
		if (root != null) {
			Stack<TreeNode> stack = new Stack<>();
			stack.push(root);
			while (!stack.isEmpty()) {
				root = stack.pop();
				list.add(root.val);
				if (root.right != null) {
					stack.push(root.right);
				}
				if (root.left != null) {
					stack.push(root.left);
				}
			}
		}
		return list;
	}

	public static boolean isBalanced(TreeNode root) {
	/** judge whether the tree is a balanced binary tree by pruning.
	 * 
	 * @return boolean
	 */
		return pruning(root) != -1;
	}
		     
	private static int pruning(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int left = pruning(root.left);
		if (left == -1) {
			return -1;
		}
		int right = pruning(root.right);
		if (right == -1) {
			return -1;
		}
		return Math.abs(left - right) > 1 ? -1 : 1 + Math.max(left, right);
	}
	
	public static boolean isBST(TreeNode root) {
	/** judge whether the tree is a binary sorted tree.
	 * 
	 * @param stack: Stack<TreeNode>
	 * @param cur: current node
	 * @param pre: previous node
	 * @return boolean
	 */
		if(root == null) {
			return true;
		}
		Stack<TreeNode> stack = new Stack<>();
		TreeNode cur = root;
		TreeNode pre = null;
		while(!stack.isEmpty() || cur != null){
			if(cur != null){
				stack.push(cur);
				cur = cur.left;
				}else {
	                cur = stack.pop();
	                if(pre != null && cur.val <= pre.val) {
	                    return false;
	                }
	                pre = cur;
	                cur = cur.right;
	            }
	        }
	        return true;
	    }
	
	public static boolean isComplete(TreeNode root) {
        /** judge whether the tree is a complete binary tree.
         * 
         * @param queue: Queue<Stack>
         * @param left: boolean, whether left child tree is complete.
         * @return boolean
         */
		if(root == null) {
			return false;
		}
		TreeNode leftChild = null;
		TreeNode rightChild = null;
		boolean left = false;
		Queue<TreeNode> queue = new LinkedList<>();
	        queue.offer(root);  
	        while (!queue.isEmpty()) {
			TreeNode head = queue.poll();
			leftChild = head.left;
			rightChild = head.right;
			if((rightChild != null && leftChild == null) ||
			   (left && (rightChild != null ||leftChild != null))) {
				return false;
			}
			if(leftChild != null) {
				queue.offer(leftChild);
			}
			if(rightChild != null) {
				queue.offer(rightChild);
			}else {
				left = true; 
			}
		}
		return true;  
	}  
	
	public static boolean isFull(TreeNode root) {
	/** judge whether the tree is a full binary tree.
	 * 
	 * @return boolean
	 */
		return (root == null) ||
			(root != null && isFull(root.left) && isFull(root.right)) &&
			maxDepth(root.left) == maxDepth(root.right);
	}
	
}
