// 78158 Domenic Rothenberger, 78013 Eric Schust, 78037 Patrick Kogel
#include <stdio.h>
#include <string.h>
void skytale(char * in, int n, char * out);
void skytale(char * in, int n, char * out) {
	int i,t=0,count=0;
	for(i=0;i<strlen(in);i++){
		out[i] = in[t];
		t = (t>=(strlen(in)-n)) ? ++count : t + n;}
	out[i] = '\0';
	}
int main() {
	char s1[1000] = "DIESERKLARTEXTISTJETZTZUVERSCHLUESSELN12",
	s2[1000]="***bereits*verwendete*zeichenkette*mit*inhalt***", // Verschlüsseln hier hinein
	s3[1000]; // Entschlüsseln in diese Variable
    int n = 20, l = strlen(s1);
	printf("%s\n", s1);
    printf("Laenge: %d\n", l);
	skytale(s1, n, s2); // Chiffriere s1 zu s2 mit 8 Buchstaben pro Umdrehung
	printf("%s\n", s2);
    if (l % n == 0) {
        skytale(s2, l / n , s3); // Chiffriere zurueck
        printf("%s\n", s3);
        if (!strcmp(s1, s3))
            printf("In diesem Fall OK\n");
        else
            printf("Problem!\n");
    }
	return 0;
}
