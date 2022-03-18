import java.io.*;
import java.util.*;

public class KNearestNeighbour {

	private ArrayList<ArrayList<Double>> wineTrainingArray;
	private ArrayList<ArrayList<Double>> wineTestArray;

	public void KNearestNeighbout() {
		this.wineTrainingArray = new ArrayList();
		this.wineTestArray = new ArrayList();
	}

	public static void main(String[] args) {
		String training = args[0];
		String test = args[1];

		File trainingFile = new File(training);
		File testFile = new File(test);

	}

	private void readTraining(File trainFile) {
		try {
			Scanner trainScan = new Scanner(trainFile);



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
