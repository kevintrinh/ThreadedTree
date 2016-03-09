public class ThreadedTree{
	private class ThreadedNode{
		int value;
		ThreadedNode right;
		ThreadedNode left;
		boolean leftThread, rightThread;
	}
	private ThreadedNode root;
	public ThreadedTree(){
		root = null;
	}
	public void insert(int value){
		
		if(root == null){
			root = new ThreadedNode();
			root.value = value;
			root.leftThread = false;
			root.rightThread = false;
		}else{
			ThreadedNode parent = null;
			ThreadedNode curr = root;
			while(curr != null){
				if(value < curr.value){
					parent = curr;
					if(curr.leftThread)
						break;
					curr = curr.left;
				}else if(value > curr.value){
					parent = curr;
					if(curr.rightThread)
						break;
					curr = curr.right;
				}else{
					return;
				}
			}
			if (value < parent.value){
				if(parent.leftThread){
					curr = new ThreadedNode();
					curr.value = value;
					curr.left = parent.left;
					curr.leftThread = true;
					curr.right = parent;
					curr.rightThread = true;
					
					parent.leftThread = false;
					parent.left = curr;
				}else{
					parent.left = new ThreadedNode();
					curr = parent.left;
					
					curr.value = value;
					curr.rightThread = true;
					curr.right = parent;
				}
			}else{
				if(parent.rightThread){
					curr = new ThreadedNode();
					curr.value = value;
					curr.right = parent.right;
					curr.rightThread = true;
					curr.left = parent;
					curr.leftThread = true;
					
					parent.rightThread = false;
					parent.right = curr;
				}else{
					parent.right = new ThreadedNode();
					curr = parent.right;
					
					curr.value = value;
					curr.leftThread = true;
					curr.left = parent;
				}
			}
		}
	}
	public void delete(int value){
		if(root == null) 
			return;
		ThreadedNode parent = root;
		ThreadedNode curr = root;
		while(curr != null){
			if(value < curr.value){
				parent = curr;
				if(curr.leftThread)
					break;
				curr = curr.left;
			}else if (value > curr.value){
				parent = curr;
				if(curr.rightThread)
					break;
				curr = curr.right;
			}else{
				break;
			}
		}
		if(curr == null) 
			return;
		int side = 0;
		if(value < parent.value){
			side = 0;
		}else{
			side = 1;
		}		
		if((curr.leftThread || curr.left == null) && (curr.rightThread || curr.right == null)){
			if(side == 0){
				parent.left = curr.left;
				parent.leftThread = true;
			}else{
				parent.right = curr.right;
				parent.rightThread = true;
			}
		}else if(curr.rightThread || curr.right == null){
			if(side == 0){
				parent.left = curr.left;
			}else{
				parent.right = curr.left;
			}
			curr.left.right = curr.right;
			curr.left.rightThread = true;
		}else if(curr.leftThread || curr.left == null ){
			if(side == 0){
				parent.left = curr.right;
			}else{
				parent.right = curr.right;
			}
			curr.right.left = curr.left;
			curr.right.leftThread = true;
		}else{ 
			ThreadedNode leftMost = curr.right;
			ThreadedNode leftMostParent = curr.right;
			while(leftMost.left != null && !leftMost.leftThread){
				leftMostParent = leftMost;
				leftMost = leftMost.left;
			}
			
			curr.value = leftMost.value;
			if(leftMost.rightThread) 
				curr.right = leftMost.right;
			if(leftMost.right != null && !leftMost.rightThread){
				leftMost.right.left = curr;
				leftMost.right.leftThread = true;
				if(leftMost != leftMostParent){
					if(leftMost.right.value != leftMostParent.value){
						leftMostParent.left = leftMost.right;
					}
				}
			}
			
		}
	}
	public boolean search(int value){
		if(root == null) 
			return false;
		
		ThreadedNode curr = root;
		while(curr != null){
			if(value < curr.value){
				if(curr.leftThread)
					break;
				curr = curr.left;
			}else if(value > curr.value){
				if(curr.rightThread)
					break;
				curr = curr.right;
			}else{
				return true;
			}
		}
		return false;
	}
}