package algo.übungen1;

public class GGT {

	private int aa;
	private int a;
	private int bb;
	private int b;
	
	public GGT(int a, int b) {
		this.aa = a;
		this.a = a;
		this.bb = b;
		this.b = b;
	}
	
	public int ggt() {
		while((this.a > 0) && (this.b > 0)) {
			if(this.a > this.b) {
				this.a %= this.b;
			}	
			else {
				this.b %= this.a;	
			}
		}
		return this.a;
	}
	
	public static void main(String[] args) {
		GGT ggt1 = new GGT(9828,2970);
		System.out.println("Der GGT ist: " + ggt1.ggt());
	}

	
}

