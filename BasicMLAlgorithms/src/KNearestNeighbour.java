import java.io.*;
import java.util.*;

public class KNearestNeighbour {
	private static String trainingFilePath = "./src/wine-training";
	private static String testFilePath = "./src/wine-test";
	private static int numWineAttributes = 13;
	private static ArrayList<Double[]> wineTrainingArray = new ArrayList();
	private static ArrayList<Integer> wineTrainingClasses = new ArrayList();
	private static ArrayList<Double[]> wineTestArray = new ArrayList();
	private static ArrayList<Integer> wineTestClasses = new ArrayList();

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
				Double[] wineArray = new Double[numWineAttributes];
				for(int j = 0; j<wineArray.length; j++) {
					wineArray[j]=lineScan.nextDouble();
				}
				wineTrainingClasses.add(lineScan.nextInt());
				wineTrainingArray.add(wineArray);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/*for(Double[] wine : wineTrainingArray) {
			for(int i = 0; i<wine.length; i++) {
				System.out.print(wine[i] + " ");
			}
			System.out.println("");
		}*/
	}

	private static void readTest(File testFile) {
		try {
			Scanner trainScan = new Scanner(testFile);

			trainScan.nextLine();

			int numWine = (int)testFile.length() - 2;

			while(trainScan.hasNextLine()) {
				Scanner lineScan = new Scanner(trainScan.nextLine());
				Double[] wineArray = new Double[numWineAttributes];
				for(int j = 0; j<wineArray.length; j++) {
					wineArray[j]=lineScan.nextDouble();
				}
				wineTestClasses.add(lineScan.nextInt());
				wineTestArray.add(wineArray);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/*for(Double[] wine : wineTestArray) {
			for(int i = 0; i<wine.length; i++) {
				System.out.print(wine[i] + " ");
			}
			System.out.println("");
		}*/
	}

}
