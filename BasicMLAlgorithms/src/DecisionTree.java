import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DecisionTree {
	static DataReader dataReader = new DataReader();
	static Set<String> categoryNames;
    static List<String> attNames;
    static List<DataReader.Instance> allInstances;
	
	public static void main(String[] args) {
		dataReader.readDataFile("hepatitis-training");
		categoryNames = dataReader.categoryNames;
		attNames =  dataReader.attNames;
		allInstances = dataReader.allInstances;
	}
	
	private Node buildTree(Set<DataReader.Instance> instances, List<String> attributes) {
		//if instances empty
		if(allInstances.isEmpty()) {
			//return a leaf node that contains the name and probability of the most probable
			//class across the whole training set (i.e. the ‘‘baseline’’ predictor)
			
			
		}
		else if(instancesPure()) {
			//return a leaf node that contains the name of the class and probability 1
			String catName = null;
			for(DataReader.Instance inst : instances) {
				catName = inst.getCategory();
				return new Node(catName, 1);
			}
		}
		else if(attNames.isEmpty()) {
			//return a leaf node that contains the name and probability of the majority 
			//class of instances (chosen randomly if classes are equal)
		}
		else {
			return findBestAttribute(instances, attributes);
		}
		return null;
	}
	
	private boolean instancesPure() {
		return false;
	}
	
	private Node findBestAttribute(Set<DataReader.Instance> instances, List<String> attributes ) {
		Set<DataReader.Instance> bestInstTrue = null;
		Set<DataReader.Instance> bestInstFalse = null;
		String bestAtt = null;
		double bestScore = 1;
		
		for(String attribute : attNames) {
			//separate instances into 2 sets, attributes true/false
			Set<DataReader.Instance> instTrue = null;
			Set<DataReader.Instance> instFalse = null;
			
			for(DataReader.Instance instance : instances) {
				if(instance.getAtt(attNames.indexOf(attribute))) {
					instTrue.add(instance);
				}
				else {
					instFalse.add(instance);
				}
			}
			
			double impurityTrue = computeImpurity(instTrue);
			double impurityFalse = computeImpurity(instFalse);
			
			double weightedImp = instTrue.size()/instances.size() * impurityTrue 
						+ instFalse.size()/instances.size() * impurityFalse;
			
			//weighted average purity of these sets is best so far
			if(weightedImp<bestScore) {
				bestAtt = attribute;
				bestInstTrue = instTrue;
				bestInstFalse = instFalse;
				bestScore = weightedImp;
			}
		}
		 
		attributes.remove(bestAtt);
		
		Node left = buildTree(bestInstTrue, attributes);
		Node right = buildTree(bestInstFalse, attributes);
		
		return new Node(bestAtt, left, right);
	}
	
	private double computeImpurity(Set<DataReader.Instance> inst){
		int total = inst.size();
		int live = 0;
		
		for(DataReader.Instance in : inst) {
			if(in.getCategory().equals("live")) {
				live++;
			}
		}
		
		int die = total - live;
		
		double impurity = live/total * die/total; 
		
		return impurity;
	}
}
