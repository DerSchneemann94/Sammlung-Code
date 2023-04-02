#include <stdio.h>

int main() {
	
	int zahl,rechner=1,prim=1,i,rest;
	
	scanf("%d",&zahl);
	
	if(zahl<2){
	printf ("%d kann nicht zerlegt werden.\n",zahl);
		rechner = 0;
	}
	
	for(i=2;i<zahl;i++){
		if((zahl%i)==0) {
			prim = 0;
		}
	}
	
	if(prim==1 && rechner==1){
		printf ("%d ist prim.\n",zahl);
		rechner = 0;
	}
	rest = zahl;
	for(i=2;i<rest+1;i++){
		while((rest%i)==0){
			rest /= i;
			printf("%d\n",i);
		}	
	}
	
	return 0;
}
