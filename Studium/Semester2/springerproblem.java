package algo.�bungen.springerproblem;

public class springerproblem {
	
	private boolean[][] besucht;
	private koordinate[] pfad;
	private int breite; 
	private int h�he;
	private static int L�sungen;
	
	
	public springerproblem(int breite,int h�he) {
		this.besucht = new boolean[breite][h�he];
		this.pfad = new koordinate[breite*h�he];
		this.breite = breite - 1;
		this.h�he = h�he - 1;
	}
	
	public void sprung(int x, int y,int z) {
		if(x < 0 || x > this.breite || y < 0 || y > this.h�he || this.besucht[x][y] == true) {return;}
		this.besucht[x][y] = true;
		this.pfad[z] = new koordinate(x,y);
		if(z==((this.breite+1)*(this.h�he+1)-1)) {
			for(int i=0;i<this.pfad.length;i++){
				int xx = this.pfad[i].x + 1;
				int yy = this.pfad[i].y + 1;
				int ii = i+1;
				System.out.println("Schritt " + ii + ":  " + xx + "," + yy);
			}
			springerproblem.L�sungen++;
		}	
		this.sprung(x-2,y-1,z+1);
		this.sprung(x-2,y+1,z+1);
		this.sprung(x+2,y-1,z+1);
		this.sprung(x+2,y+1,z+1);
		this.sprung(x-1,y-2,z+1);
		this.sprung(x+1,y-2,z+1);
		this.sprung(x-1,y+2,z+1);
		this.sprung(x+1,y+2,z+1);
		
		this.besucht[x][y] = false;
		this.pfad[z] = null;
	}
	
	
	public static void main(String[] args) {
		springerproblem a = new springerproblem(5,6);
		int z�hler=0;
		a.sprung(0, 0,z�hler);
		System.out.println(springerproblem.L�sungen);
 	}
}
