package algo.�bungen1;

public class Quadrat {

	private int n;
	private int l�sung;
	public int nn;
	public Quadrat(int n) {
		this.n = n;
		this.nn = n*n;
	}
	
	public int quadrat() {
		int i = 1;
		int k = 1;
		while(i <= n) {
			this.l�sung += k;
			i += 1;
			k += 2;
		}
		return this.l�sung;
	}

	
	public static void main(String[] args) {
		
		Quadrat q1 = new Quadrat(3);
		System.out.println("L�sung 1 : " + q1.quadrat() + " L�sung 2 : " + q1.nn);
		
		Quadrat q2 = new Quadrat(9);
		System.out.println("L�sung 1 : " + q2.quadrat() + " L�sung 2 : " + q2.nn);
		
		
	}
	
}


