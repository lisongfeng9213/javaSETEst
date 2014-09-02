
public class Data {
	private double[][]x;
	private int[] y;
	Data(double[][] x,int[]y){
		this.x = x;
		this.y = y;
	}
	
	Data(){
		
	}
	
	public double[][] getX() {
		return x;
	}
	public void setX(double[][] x) {
		this.x = x;
	}
	public int[] getY() {
		return y;
	}
	public void setY(int[] y) {
		this.y = y;
	}
	
}
