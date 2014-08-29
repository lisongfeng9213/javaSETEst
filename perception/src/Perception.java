import javax.naming.InitialContext;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Perception {

	/**
	 * @param args
	 */
	Data trainData = null;
	Data testData = null;

	public static void main(String[] args) {
		Perception perception = new Perception("data/iris.data", 0.001, 100);

	}

	public Perception(String path, double eta, int iterMax) {
		// split data,train
		CopyOfGenerateTrainTest test = new CopyOfGenerateTrainTest();
		test.splitData(path);
		FileRead fdTrain = new FileRead("data/train.data");
		FileRead fdTest = new FileRead("data/test.data");
		System.out.println(fdTrain.getFileLineNumber());
		System.out.println(fdTest.getFileLineNumber());

		try {
			trainData = fdTrain.readData();
			testData = fdTest.readData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		double[][] TrainX = trainData.getX();
		int[] TrainY = trainData.getY();

		double initW[] = new double[TrainX[0].length];
		for (int i = 0; i < initW.length; i++) {
			initW[i] = 0.1;
		}
		double intitB = 0.1;

		double[] newW = new double[TrainX[0].length];
		double newB = 1;
		int iterTime = 0;
		while ((!initW.equals(newW) || intitB != newB) && iterTime <= iterMax) {
			
			for (int i = 0; i < newW.length; i++) {
				initW[i] = newW[i];
				System.out.println(newW[i]);
			}
			intitB = newB;

			System.out.println("迭代中："+iterTime);
			iterTime++;
			int xrowLen = TrainX.length;
			int xcolLen = TrainX[0].length;
			double sumX = 0;
			for(int i = 0;i<xrowLen;i++){
				sumX = 0;
				for (int j = 0; j < xcolLen; j++) {
					sumX += initW[j]*TrainX[i][j];					
				}
				sumX += intitB;
				sumX *=TrainY[i]; 
				System.out.println("sumX:"+sumX);
				if(sumX <= 0){
					for(int s = 0;s<xcolLen;s++){
					
						newW[s] +=  eta*TrainX[i][s]*TrainY[s];
						newB = newB + eta*TrainY[s];						
					}
				}				
			}	
		}
		

		for (int i = 0; i < TrainX[0].length; i++) {
			System.out.println("W["+i+"]:"+initW[i]);
		}
		
		double[][] TestX = testData.getX();
		int []TestY = testData.getY();
		
		
		int rowLen = TestX.length;
		int colLen = TestX[0].length;
		int sum = 0;
		int rightCf = 0;
		for (int i = 0; i < rowLen; i++) {
			sum = 0;
			for (int j = 0; j < colLen; j++) {
				sum += newW[j]*TestX[i][j];
			}
			sum += newB;
			if(TestY[i]*sum>=0){
				rightCf++;
			}
		}
		
		System.out.println("Test正确率："+(double)rightCf/(double)rowLen);

		
		System.out.println("b:"+intitB);
		System.out.println(fdTrain.getFileLineNumber());
		System.out.println(fdTest.getFileLineNumber());		
		

	}

}
