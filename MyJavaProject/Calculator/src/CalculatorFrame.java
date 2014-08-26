import javax.swing.JFrame;


public class CalculatorFrame extends JFrame 
{
	public CalculatorFrame() {
		add(new CalculatorPanel());
		pack();
	}	

}
