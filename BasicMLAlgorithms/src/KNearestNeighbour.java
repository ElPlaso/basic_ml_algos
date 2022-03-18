import java.io.*;
import java.util.*;

public class KNearestNeighbour {
	private static String trainingFilePath = "./src/wine-training";
	private static String testFilePath = "./src/wine-test";
	private static int numWineAttributes = 13;
	private static ArrayList<Wine> wineTrainingArray = new ArrayList();
	private static ArrayList<Wine> wineTestArray = new ArrayList();

	public static void main(String[] args) {

		File trainingFile = new File(trainingFilePath);
		File testFile = new File(testFilePath);

		readTraining(trainingFile);
		readTest(testFile);

	}

	private static void readTraining(File trainFile) {
		try {
			Scanner trainScan = new Scanner(trainFile);

			trainScan.nextLine();

			int numWine = (int)trainFile.length() - 2;

			while(trainScan.hasNextLine()) {
				Scanner lineScan = new Scanner(trainScan.nextLine());
				double[] wineArray = new double[numWineAttributes];
				for(int j = 0; j<wineArray.length; j++) {
					wineArray[j]=lineScan.nextDouble();
				}
				wineTrainingArray.add(new Wine(wineArray, lineScan.nextInt()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void readTest(File testFile) {
		try {
			Scanner trainScan = new Scanner(testFile);

			trainScan.nextLine();

			int numWine = (int)testFile.length() - 2;

			while(trainScan.hasNextLine()) {
				Scanner lineScan = new Scanner(trainScan.nextLine());
				double[] wineArray = new double[numWineAttributes];
				for(int j = 0; j<wineArray.length; j++) {
					wineArray[j]=lineScan.nextDouble();
				}
				wineTestArray.add(new Wine(wineArray, lineScan.nextInt()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/*for(Wine wine : wineTestArray) {
			for(int i = 0; i<wine.getAttributes().length; i++) {
				System.out.print(wine.getAttributes()[i] + " ");
			}
			System.out.println("");
		}*/
	}

	private static void KNNMethod() {

	}

}
