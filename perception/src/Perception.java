
public class Perception {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Perception perception = new Perception();
		perception.splitData("data/iris.data");
		FileRead fdTrain = new FileRead("data/train.data");
		FileRead fdTest = new FileRead("data/test.data");
		try {
			Data trainData = fdTrain.readData();
			Data testData = fdTest.readData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(fdTrain.getFileLineNumber());
		System.out.println(fdTest.getFileLineNumber());
	}
	
	public void splitData(String path){
		CopyOfGenerateTrainTest test = new CopyOfGenerateTrainTest();
		test.splitData(path);		
	}

}
