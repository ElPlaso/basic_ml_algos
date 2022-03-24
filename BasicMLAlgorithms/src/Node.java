public class Node {
	boolean isLeaf;
	Node leftNode;
	Node rightNode;
	String attName; 
	String catName;
	double probability;
	
	public Node(String att, Node left, Node right) {
		this.isLeaf = false;
		this.attName = att;
		this.leftNode = left;
		this.rightNode = right;
	}
	
	public Node(String cat, double prob) {
		this.isLeaf = true;
		this.catName = cat;
		this.probability = prob;
	}
	
	public Node getLeftNode() {
		return this.leftNode;
	}
	
	public Node getRightNode() {
		return this.rightNode;
	}
	
	public boolean getIsLeaf() {
		return isLeaf;
	}
}
