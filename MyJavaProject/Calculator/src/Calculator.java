import javax.swing.JFrame;


public class Calculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CalculatorFrame frame = new CalculatorFrame();
        frame.setTitle("Calculator");               
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 250);
        frame.setResizable(false);
        frame.setVisible(true);       
	}

}
