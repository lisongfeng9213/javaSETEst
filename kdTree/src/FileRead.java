import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.Reader;
import java.util.Random;

public class FileRead {


	BufferedReader dataReader = null;
	String file = null;

	public FileRead(String fileName) {

		file = fileName;
		Reader outTrain = null;
		try {
			outTrain = new FileReader(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dataReader = new BufferedReader(outTrain);
	}

	public Data readData() throws Exception {

		int num = getFileLineNumber();
		double[][] x = new double[num][];
		int y[] = new int[num];

		for (int i = 0; i < num; i++) {
			DataLine dLine = getLine();
			x[i] = dLine.getX();
			y[i] = dLine.getY();
		}
		return new Data(x, y);

	}

	public int getFileLineNumber() {

		Reader outTrain = null;
		int num = 0;
		try {
			outTrain = new FileReader(file);

			BufferedReader bf = new BufferedReader(outTrain);
			String aString = null;
			while ((aString = bf.readLine()) != null) {
				num++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return num;
	}

	public DataLine getLine() {

		double[] x;
		int y;

		String lineString = null;
		try {
			lineString = dataReader.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		String strs[];
		int strLen = 0;
		strs = lineString.split(",");
		strLen = strs.length;

		if (strs[strLen - 1].equals("Iris-setosa")) {
			y = 1;
		} else if(strs[strLen - 1].equals("Iris-virginica")){
			y = 0;
		}else{
			y = -1;
		}
		x = new double[strLen - 1];
		for (int i = 0; i < strLen - 1; i++) {
			x[i] = Double.parseDouble(strs[i]) + Math.random()/1000;
		}
		return new DataLine(x, y);

	}
}
