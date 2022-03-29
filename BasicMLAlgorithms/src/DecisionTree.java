import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DecisionTree {
	static DataReader dataReader = new DataReader();
	static Set<String> categoryNames;
    static List<String> attNames;
    static List<DataReader.Instance> allInstances;
	
	public static void main(String[] args) {
		dataReader.readDataFile("./src/golf-training.dat");
		categoryNames = dataReader.categoryNames;
		attNames =  dataReader.attNames;
		allInstances = dataReader.allInstances;
		
		//Set instances = new HashSet<>();
		
		//for(DataReader.Instance in : allInstances) {
			//instances.add(in);
		//}
		
		Set<DataReader.Instance> instances = new HashSet<>(allInstances);
		
		Node root = buildTree(instances, attNames);
		root.report("");
	}
	
	private static Node buildTree(Set<DataReader.Instance> instances, List<String> attributes) {
		//if instances empty
		if(instances.isEmpty()) {
			//return a leaf node that contains the name and probability of the most probable
			//class across the whole training set (i.e. the ‘‘baseline’’ predictor)
			
			System.out.println("instances empty");
		}
		else if(instancesPure(instances)) {
			//return a leaf node that contains the name of the class and probability 1
			String catName = null;
			for(DataReader.Instance inst : instances) {
				catName = inst.getCategory();
				return new Node(catName, 1);
			}
		}
		else if(attributes.isEmpty()) {
			//return a leaf node that contains the name and probability of the majority 
			//class of instances (chosen randomly if classes are equal)
			System.out.println("attributes empty");
			
		}
		else {
			
			return findBestAttribute(new HashSet<>(instances), attributes);
		}
		return null;
	}
	
	private static boolean instancesPure(Set<DataReader.Instance> instances) {
		String cat = "";
		for(DataReader.Instance ins : instances) {
			cat = ins.getCategory();
			break;
		}
		for(DataReader.Instance ins : instances) {
			if(!ins.getCategory().equals(cat)) {
				return false;
			}
		}
		
		return true;
	}
	
	private static Node findBestAttribute(Set<DataReader.Instance> instances, List<String> attributes ) {
		Set<DataReader.Instance> bestInstTrue = new HashSet<>();
		Set<DataReader.Instance> bestInstFalse = new HashSet<>();
		String bestAtt = null;
		double bestScore = 1;
		
		boolean gotin = false;
		
		for(String attribute : attributes) {
			//separate instances into 2 sets, attributes true/false
			Set<DataReader.Instance> instTrue = new HashSet<>();
			Set<DataReader.Instance> instFalse = new HashSet<>();
			
			for(DataReader.Instance instance : instances) {
				System.out.println(attNames.indexOf(attribute));
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
				gotin = true;
			}
		}
		if(!gotin) {
			System.out.println("didnt get in");
		}
		 
		attributes.remove(bestAtt);
		
		//System.out.println(bestAtt);
		
		List<String> newAttributes = new ArrayList<>(attributes);
		
		Node left = buildTree(bestInstTrue, newAttributes);
		Node right = buildTree(bestInstFalse, newAttributes);
		
		return new Node(bestAtt, left, right);
	}
	
	private static double computeImpurity(Set<DataReader.Instance> inst){
		int total = inst.size();
		int live = 0;
		
		for(DataReader.Instance in : inst) {
			if(in.getCategory().equals("live")) {
				live++;
			}
		}
		
		int die = total - live;
		
		double impurity = 0;
		
		if(total!=0) {
			impurity = live/total * die/total; 
		}
		
		return impurity;
	}
}
