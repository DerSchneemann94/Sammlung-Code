//Berechung von Ostern mit der Gauﬂschen Osterformel

#include <stdio.h>

int main() {

	int a,b,c,k,p,q,M,d,N,e,Jahr,Monat,Ostern,april,rest;	
	
	Monat = 3;
	
	scanf("%d",&Jahr);
		
	//Berechnung nach Vorgabe(Gauﬂsche Osterformel)
	a = Jahr % 19; 
	b = Jahr % 4;
	c = Jahr % 7; 
	k = Jahr / 100; 
	p = (8*k + 13) / 25; 
	q = k / 4;
	M = (15 + k - p - q) % 30; 
	d = (19*a + M) % 30; 
	N = (4 + k - q) % 7; 
	e = (2*b + 4*c + 6*d + N) % 7; 
	Ostern = (22 + d + e);//ter Maerz
	
	
	//Korrektur auf April	 
	april = Ostern / 32;
	rest = Ostern % 31;
	Ostern = Ostern * (1-april) + rest*april; 
	Monat += april;
	
	
	printf ("%02d.%02d.%04d\n",Ostern,Monat,Jahr);
	
	return 0;
}
