//ganzahliges arithmetisches Mittel zweier Ganzzahlen

#include <stdio.h>

int main() {

	int zahl1,zahl2,mittelwert;
	
	scanf("%d%d",&zahl1,&zahl2);					//Einlesen Ganzahlen
	mittelwert = (zahl1 + zahl2) / 2;				//Berechung Mittelwert
	printf("%d\n",mittelwert);						//Ausgabe Mittelwert
	
	return 0;


}
