package algo.übungen1;

public class Quadrat {

	private int n;
	private int lösung;
	public int nn;
	public Quadrat(int n) {
		this.n = n;
		this.nn = n*n;
	}
	
	public int quadrat() {
		int i = 1;
		int k = 1;
		while(i <= n) {
			this.lösung += k;
			i += 1;
			k += 2;
		}
		return this.lösung;
	}

	
	public static void main(String[] args) {
		
		Quadrat q1 = new Quadrat(3);
		System.out.println("Lösung 1 : " + q1.quadrat() + " Lösung 2 : " + q1.nn);
		
		Quadrat q2 = new Quadrat(9);
		System.out.println("Lösung 1 : " + q2.quadrat() + " Lösung 2 : " + q2.nn);
		
		
	}
	
}


