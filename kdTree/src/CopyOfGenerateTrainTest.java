import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.Math;


public class CopyOfGenerateTrainTest {
	

	public void splitData(String fileName){
		Reader in;
		try {
			in = new FileReader(fileName);
			Writer outTestFile = new FileWriter("data/test.data");
			Writer outTrainFile = new FileWriter("data/train.data");
		
			BufferedReader reader = null;
			BufferedWriter writerTest = null;
			BufferedWriter writerTrain = null;
			
			reader = new BufferedReader(in);
			writerTest = new BufferedWriter(outTestFile);
			writerTrain = new BufferedWriter(outTrainFile);
			
			String stringLineString = null;
			while((stringLineString = reader.readLine())!=null){
				double random =  Math.random();
				if(random < 0.25){
					writerTest.write(stringLineString+'\n');

				}else{
					writerTrain.write(stringLineString+'\n');

				}
			}
			writerTest.close();
			writerTrain.close();
			reader.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
