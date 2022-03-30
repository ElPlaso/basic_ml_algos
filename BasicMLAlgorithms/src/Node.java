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
	
	public String getCat() {
		return catName;
	}
	
	public String getAtt() {
		return attName;
	}
	
	public void report(String indent){
		if(!isLeaf) {
			System.out.printf("%s%s = True:%n", indent, attName);
			if(leftNode!=null) {
				leftNode.report(indent+"\t");
			}
			System.out.printf("%s%s = False:%n", indent, attName);
			if(rightNode!=null) {
				rightNode.report(indent+"\t");
			}
		}
		else {
			if (probability==0){ //Error-checking
				System.out.printf("%sUnknown%n", indent);
			}
			else{
				System.out.printf("%sClass %s, prob=%.2f%n", indent, catName, probability);
			}
		}
	}

	
}
