#include <stdio.h>

int primfaktor();

int main() { 
	
	// Test der Schnittstelle auf alle Funktionen
	int pf;
	while (( pf = primfaktor (12)) > 1){
		printf("%d\n" , pf );
	pf = primfaktor(12);
	printf("%d\n" , pf );	
	}
	
	while (( pf = primfaktor (20)) > 1){
		printf("%d\n" , pf );	 
	}
	
	pf = primfaktor(1);
	printf("%d\n" , pf );	
	
	while (( pf = primfaktor (100)) > 1){
		printf("%d\n" , pf );	 
	}
	
	pf = primfaktor(100);
	printf("%d\n" , pf );
	
	pf = primfaktor(100);
	printf("%d\n" , pf );
	
	pf = primfaktor(0);
	printf("%d\n" , pf );
	
	return 0;
		
}

int primfaktor(int n) {
	
	static int referenz,wert,teiler;
	int back;
	
	// Überprüfung auf neue Eingabe 
	if(referenz != n){
		wert = n;
		referenz = n;
		teiler = 2;
	}
		
	//Ausgabe, wenn Primfaktorzerlegung fertig 	
	if(wert < 2) {
		back = -1;
	}
	
	//Fehlerabfrage, wenn Zahl < 2
	if(n < 2) {
		back = -2;
	}
	
	//Ermittlung der Primfaktoren
	while(wert>1){
		if((wert%teiler)==0) {
			wert /= teiler;
			back = teiler;
			break;
		}
		else {
			teiler++;
		}
	} 
	return back;
}

