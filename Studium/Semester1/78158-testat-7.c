#include <stdio.h>
#include <math.h>
#include "testat-7.h"
int huepfspiel(spielfeld * s, int nr);
    int huepfspiel(spielfeld * s, int nr) {
		int sprungRechts=0,sprungLinks=0,rc;
        
		if((nr<0)||(nr>(s->n)))
			return -1;   
						        
        if(nr == s->n){
        	return 0;}
		
		if(s->feld[nr]<=0)
			return -1;
		
		s->feld[nr] *= -1;
		sprungRechts = huepfspiel(s,nr + (-1)*(s->feld[nr]));
        sprungLinks = huepfspiel(s,nr - (-1)*(s->feld[nr])/2);
       	
    	if((sprungRechts == -1)&&(sprungLinks == -1)){
    		s->feld[nr] *= -1;
			return -1;}
		
		if(((sprungRechts<=sprungLinks)&&(sprungRechts!=-1))||sprungLinks==-1)
			rc = sprungRechts + 1;
	    if(((sprungLinks<=sprungRechts)&&(sprungLinks!=-1))||sprungRechts==-1)
			rc = sprungLinks + 1; 
    
	s->feld[nr] *= -1;
	return rc;
}

int main() {
    spielfeld s = {20, {5 ,3 ,2 ,1 ,8 ,2 ,1 ,2 ,3 ,4 ,5 ,2 ,3 ,7 ,1 ,4 ,1 ,9 ,0 ,1}};
    printf("Loesbar: %d\n", huepfspiel(&s, 0));
    return 0;
}
