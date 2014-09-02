
public class Pair {
	
	
	
	public Pair(double[] ds, int y) {
		super();
		this.x = ds;
		this.y = y;
	}
	public Pair() {
		// TODO Auto-generated constructor stub
	}
	double[]x;
	int y;
	public double[] getX() {
		return x;
	}
	public void setX(double[] x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public double distance(Pair p){
		
		double sum = 0.0;
		double[] xP = p.getX();
		for(int i = 0;i<xP.length;i++){
			sum += (x[i] - xP[i])*(x[i] - xP[i]);
		}
		return sum;
	}
	
}
