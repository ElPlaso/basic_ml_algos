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
		for(Wine wine : wineTestArray) {

			HashMap<Double, Wine> wineDistances = new HashMap<>();

			for(Wine trainWine : wineTrainingArray) {
				double distance = 0.0;
				for(int i = 0; i<numWineAttributes; i++) {
					distance += Math.pow(wine.getAttributes()[i] - trainWine.getAttributes()[i], 2) /
								Math.pow(findRange(i), 2);
				}

				wineDistances.put(distance, trainWine);
			}

			Double[] distances = (Double[]) wineDistances.keySet().toArray();
			Arrays.sort(distances);

			Wine closestWine = wineDistances.get(distances[0]);
		}
	}

	private static double findRange(int col) {
		try {
			ArrayList<Double> doubles = new ArrayList();

			for(Wine wine : wineTestArray) {
				doubles.add(wine.getAttributes()[col]);
			}

			Collections.sort(doubles);

			double max = doubles.get(0);
			double min = doubles.get(doubles.size()-1);


			return max-min;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
