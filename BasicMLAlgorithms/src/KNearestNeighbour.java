import java.io.*;
import java.util.*;

public class KNearestNeighbour {
	private static String trainingFilePath = ".src/wine-training";
	private static String testFilePath = ".src/wine-test";
	private int numWineAttributes = 14; //13 attributes + class
	private ArrayList<Double[]> wineTrainingArray = new ArrayList();
	private ArrayList<Double[]> wineTestArray = new ArrayList();

	public static void main(String[] args) {

		File trainingFile = new File(trainingFilePath);
		File testFile = new File(testFilePath);

	}

	private void readTraining(File trainFile) {
		try {
			Scanner trainScan = new Scanner(trainFile);

			trainScan.nextLine();

			int numWine = (int)trainFile.length() - 2;

			for(int i = 0; i<numWine; i++) {
				Double[] wineArray = new Double[numWineAttributes];
				for(int j = 0; j<wineArray.length; j++) {
					wineArray[j]=trainScan.nextDouble();
				}
				wineTrainingArray.add(wineArray);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}



	private void readTest(File testFile) {
		try {
			Scanner testScan = new Scanner(testFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
