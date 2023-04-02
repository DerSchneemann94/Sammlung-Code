// 78158 Domenic Rothenberger, 78013 Eric Schust, 78037 Patrick Kogel
#include <stdio.h>
#include "testat-6.h"

int scheitelhoehe(struct parabel *p, double *y) {
    int rc = 0;
    double x;
    if(p->a == 0){
    	rc = 1;
	}
	else{
		x = - (p->b) / (2 * (p->a)) ;
		*y = p->a *  x*x + p->b * x + p->c;
	}
	return rc;
}

void sort_parabeln(struct parabel *p, int n) {
	double g=0,h=0,dummy;
	int i,j; 
	for (i=0;i<2*n;i++) { 
	 	for (j=0;j<n - 1;j++){
			scheitelhoehe(&p[j],&g);
			scheitelhoehe(&p[j+1],&h);
			if(((h < g) || (p[j].a == 0)) && (p[j+1].a != 0)){
				dummy = p[j].a;
				p[j].a = p[j+1].a;
				p[j+1].a = dummy;

				dummy = p[j].b;
				p[j].b = p[j+1].b;
				p[j+1].b = dummy;
					
				dummy = p[j].c;
				p[j].c = p[j+1].c;
				p[j+1].c = dummy;
			}
		}
	}
}
		
int main() {
   
    struct parabel p = {2,5,-19};
    double y;
    printf("Index 0 ist eine echte Parabel: %d\n", scheitelhoehe(&p, &y));
    printf("%.2lf\n\n",y);
	struct parabel g[] = {
        {0,5,-2},
        {1,2,40},
		{-1,0,0},
		{1,3,-32},
		{2,5,-19},
		{1,3,15},
		{0,4,6},
		{-0,0,5},
		{3,0,17},
		{0,0,0},
		{-4,0,27}
	};
    
	sort_parabeln(g, sizeof(g) / sizeof(struct parabel));
	int i;
	for(i=0;i<(sizeof(g)/sizeof(struct parabel));i++){
		printf("%.2lf\n%.2lf\n%.2lf\n",g[i].a,g[i].b,g[i].c);
		printf("\n");
	}
	
	return 0;		
}
