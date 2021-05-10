#ifndef testat_6
#define testat_6 1

struct parabel {
    double a;
    double b;
    double c;
};

int scheitelhoehe(struct parabel * p, double *y);
// Wenn p->a != 0 dann wird in *y die Scheitelhoehe geschrieben und 0 zurueckgegeben
// Wenn p->a == 0 dann wird *y nicht geschrieben und 1 zurueckgegeben


void sort_parabeln(struct parabel * p, int n);
// Sortiert das Feld nach Scheitelhoehe aufsteigend

#endif
