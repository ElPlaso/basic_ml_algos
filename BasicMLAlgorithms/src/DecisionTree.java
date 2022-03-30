import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

public class DecisionTree {
	static DataReader dataReader = new DataReader();
	static Set<String> categoryNames;
    static List<String> attNames;
    static List<DataReader.Instance> allInstances;
	//hepatitis-training
    //golf-training.dat
	public static void main(String[] args) {
		dataReader.readDataFile("./src/hepatitis-training");
		categoryNames = dataReader.categoryNames;
		attNames =  dataReader.attNames;
		allInstances = dataReader.allInstances;
		
		//Set instances = new HashSet<>();
		
		//for(DataReader.Instance in : allInstances) {
			//instances.add(in);
		//}
		
		//Set<DataReader.Instance> instances = new HashSet<>(allInstances);
		
		DataReader testData = new DataReader();
		testData.readDataFile("./src/hepatitis-test");
		
		Node root = buildTree(new ArrayList<>(allInstances), attNames);
		root.report("");
		
		testTree(root, testData);
	}
	
	//method sourced from online : https://stackoverflow.com/questions/19031213/java-get-most-common-element-in-a-list
	public static <T> T mostCommon(List<T> list) {
	    Map<T, Integer> map = new HashMap<>();

	    for (T t : list) {
	        Integer val = map.get(t);
	        map.put(t, val == null ? 1 : val + 1);
	    }

	    Entry<T, Integer> max = null;

	    for (Entry<T, Integer> e : map.entrySet()) {
	        if (max == null || e.getValue() > max.getValue())
	            max = e;
	    }

	    return max.getKey();
	}
	
	private static void testTree(Node rootNode, DataReader testSet) {
		Set<String> testCats = testSet.categoryNames;
	    List<String> testAtts = testSet.attNames;
	    List<DataReader.Instance> testInstances = testSet.allInstances;
	    
	    double counter = 0;
	    
	    for(DataReader.Instance testIn : testInstances) {
	    	String predictedCat = recurseTree(rootNode, testIn);
	    	if(predictedCat.equals(testIn.getCategory())) {
	    		counter++;
	    	}
	    }
	    
	    double correctRatio = counter / testInstances.size();
	    
	    System.out.println(correctRatio);
	    
	}
	
	private static String recurseTree(Node node, DataReader.Instance testIn) {
		if(node.getIsLeaf()) {
			return node.getCat();
		}
		else if(testIn.getAtt(attNames.indexOf(node.getAtt()))){
			return recurseTree(node.getLeftNode(), testIn);
		}
		else {
			return recurseTree(node.getRightNode(), testIn);
		}
	}
	
	private static Node buildTree(List<DataReader.Instance> instances, List<String> attributes) {
		//if instances empty
		if(instances.isEmpty()) {
			//return a leaf node that contains the name and probability of the most probable
			//class across the whole training set (i.e. the ‘‘baseline’’ predictor)
			
			List<String> catNames = new ArrayList<>();
			
			for(DataReader.Instance ins : allInstances) {
				catNames.add(ins.getCategory());
			}
			
			
			String mostCat = mostCommon(catNames);
			
			double freq = Collections.frequency(catNames, mostCat);
			double prob = freq / catNames.size();
			
			return new Node(mostCat, prob);
			//System.out.println("instances empty");
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
			
			List<String> catNames = new ArrayList<>();
			
			for(DataReader.Instance ins : instances) {
				catNames.add(ins.getCategory());
			}
			
			
			String mostCat = mostCommon(catNames);
			
			double freq = Collections.frequency(catNames, mostCat);
			double prob = freq / catNames.size();
			
			return new Node(mostCat, prob);
			
			//System.out.println("attributes empty");
		}
		else {
			return findBestAttribute(new ArrayList<DataReader.Instance>(instances), new ArrayList<>(attributes));
		}
		return null;
	}
	
	private static boolean instancesPure(List<DataReader.Instance> instances) {
		String cat = instances.get(0).getCategory();
		for(DataReader.Instance ins : instances) {
			if(!ins.getCategory().equals(cat)) {
				return false;
			}
			
		}
		return true;
	}
	
	private static Node findBestAttribute(List<DataReader.Instance> instances, List<String> attributes ) {
		List<DataReader.Instance> bestInstTrue = new ArrayList<>();
		List<DataReader.Instance> bestInstFalse = new ArrayList<>();
		String bestAtt = null;
		double bestScore = 1;
		
		double insSize = (double)instances.size();
		
		for(String attribute : attributes) {
			//separate instances into 2 sets, attributes true/false
			List<DataReader.Instance> instTrue = new ArrayList<>();
			List<DataReader.Instance> instFalse = new ArrayList<>();
						
			for(DataReader.Instance instance : instances) {
				if(instance.getAtt(attNames.indexOf(attribute))) {
					instTrue.add(instance);
				}
				else {
					instFalse.add(instance);
				}
			}
			
			System.out.println("Size " + instTrue.size() * instFalse.size());
			
			//System.out.println("inst is pure: " + instancesPure(instances));
			//System.out.println("empty true: " + instTrue.isEmpty());
			//System.out.println("empty false: " + instFalse.isEmpty());
			
			double impurityTrue = computeImpurity(instTrue);
			double impurityFalse = computeImpurity(instFalse);
			
			System.out.println(impurityTrue);
			System.out.println(impurityFalse);
			
			double weightedImp = (double)instTrue.size()/insSize * impurityTrue 
						+ (double)instFalse.size()/insSize * impurityFalse;
			
			System.out.println(attribute + " " + weightedImp);
			
			
			
			//weighted average purity of these sets is best so far
			if(weightedImp<bestScore) {
				bestAtt = attribute;
				bestInstTrue = instTrue;
				bestInstFalse = instFalse;
				bestScore = weightedImp;
			}
		}
		 
		attributes.remove(bestAtt);
		
		List<String> leftAttributes = new ArrayList<>(attributes);
		List<String> rightAttributes = new ArrayList<>(attributes);
		
		//throw new Error("");
		
		Node left = buildTree(bestInstTrue, leftAttributes);
		Node right = buildTree(bestInstFalse, rightAttributes);
		
		
		
		return new Node(bestAtt, left, right);
	}
	
	private static double computeImpurity(List<DataReader.Instance> inst){
		double total = (double)inst.size();
		double count = 0.0;
		
		String firstCat = "";
		for(String cats : categoryNames) {
			firstCat = cats;
			break;
		}
		
		for(DataReader.Instance in : inst) {
			if(in.getCategory().equals(firstCat)) {
				count++;
			}
		}
		
		double other = total - count;
		
		double impurity = 0;
		
		if(total!=0) {
			impurity = (count * other) / Math.pow(total, 2);
		}
		else {
			System.out.println("empty inst");
		}
		
		return impurity;
	}
}
