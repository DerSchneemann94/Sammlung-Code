#include <stdio.h>

int main() {


	double bruttogehalt = -1,steuersatz,steuerlast,steuer;
	int verheiratet = -1,kinder = -1,error = 0;
	
	const double steuersatz1 = 0.12;
	const double steuersatz2 = 0.15;
	const double steuersatz3 = 0.20;
	const double steuersatz4 = 0.25;
	
	
		
	scanf("%lf%d%d",&bruttogehalt,&verheiratet,&kinder);

	
	if(bruttogehalt<0) {
		error = 1;
	}
	if(verheiratet !=1 && verheiratet !=0) {
		error = 1;
	}
	if(kinder<0){
		error = 1;
	}


	if(bruttogehalt<=12000) {
		steuersatz = steuersatz1;
	}
	if(12000<bruttogehalt && bruttogehalt<=20000){
		steuersatz = steuersatz2;
	}
	if(20000<bruttogehalt &&bruttogehalt <=30000){
		steuersatz = steuersatz3;
	}
	if(bruttogehalt>30000){
		steuersatz = steuersatz4;
	}
		
	
	if(error==0){
		steuerlast = steuersatz * (1  - (verheiratet * 0.20 + kinder * 0.10));
		if(steuerlast<0) {
			steuerlast = 0;
		}
		steuer = bruttogehalt * steuerlast;
		printf ("%.2lf\n",steuer);	
	}
	else{
		printf ("Eingabefehler\n");	
	}
	
	
	return 0;
}
