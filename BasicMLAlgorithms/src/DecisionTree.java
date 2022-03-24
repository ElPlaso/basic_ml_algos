import java.io.File;
import java.util.List;
import java.util.Set;

public class DecisionTree {
	static DataReader dataReader = new DataReader();
	static Set<String> categoryNames;
    static List<String> attNames;
	
	public static void main(String[] args) {
		dataReader.readDataFile("hepatitis-training");
		categoryNames = dataReader.categoryNames;
		attNames =  dataReader.attNames;
	}
	
	private void buildTree() {
		if(dataReader.allInstances.isEmpty()) {
			
		}
		else if(instancesPure()) {
			
		}
		else if(attNames.isEmpty()) {
			
		}
		else {
			findBestAttribute();
		}
	}
	
	private boolean instancesPure() {
		return false;
	}
	
	private void findBestAttribute() {
		
	}
	
	
	
}
