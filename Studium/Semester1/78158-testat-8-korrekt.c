 #include <stdio.h>
 #include <string.h>
 #include <malloc.h>
 
int main(int Anzahl, char *Eingabe[]){
	FILE *worte;
	if(NULL == (worte = fopen("testat-8-namen.txt","r"))){
		fprintf(stderr,"Dateifehler\n");
		return 0;
	}
	fseek(worte,0,SEEK_END);
	int lang = ftell(worte);
	printf("%d\n",lang);
	rewind(worte);
	char *ziel = (char*) malloc(sizeof(char)*(lang));
	int i,counter=0;
	while(fgets(ziel,lang,worte)!=NULL){
		char *teilstring = strtok(ziel," ");
		while(teilstring != NULL){
			for(i=1;i<Anzahl;i++){
				if(strcmp(Eingabe[i],teilstring) == 0){
					counter++;
				}
			}	
		teilstring = strtok(NULL," \n");
		}
	}
	fclose(worte);
	free(ziel);
	printf("%d Treffer\n",counter);
	return 0;	
}
