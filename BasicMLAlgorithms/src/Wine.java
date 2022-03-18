
public class Wine {
	private int wineType;
	private double[] attributes = new double[13];

	public Wine(double[] attributeArray, int type) {
		this.attributes = attributeArray;
		this.wineType = type;
	}

	public int getWineType() {
		return this.wineType;
	}

	public double[] getAttributes() {
		return this.attributes;
	}

}
