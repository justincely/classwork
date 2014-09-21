/* Runge Kutta for a set of first order differential equations */
#include <stdio.h>
#include <math.h>

#define N 2                     /* number of first order equations */
#define dist .1                /* stepsize in t*/
#define MAX 15.0                /* max for t */
 
FILE *output;                   /* internal filename */

void runge4(double x, double y[], double step); /* Runge-Kutta function */

double f(double x, double y[], int i);          /* function for derivatives */

main()
{
double t, y[N];
int j;
 
output=fopen("RK4.txt", "w");                   /* external filename */
y[0]=0.0;                                       /* initial position */
y[1]=1.0;                                       /* initial velocity */
 fprintf(output, "0\t%f\t%f\n", sin(0),y[0]);
 
for (j=1; j*dist<=MAX ;j++)                     /* time loop */
{
   t=j*dist;
   runge4(t, y, dist);

   fprintf(output, "%f\t%f\t%f\n", t, sin(t),y[0]);
   printf("%f\t%f\t%f\n", t, sin(t),y[0]);
}

fclose(output);
}

void runge4(double x, double y[], double step)
{
double h=step/2.0,                      /* the midpoint */
        t1[N], t2[N], t3[N],            /* temporary storage arrays */
        k1[N], k2[N], k3[N],k4[N];      /* for Runge-Kutta */
int i;
 
for (i=0;i<N;i++) t1[i]=y[i]+0.5*(k1[i]=step*f(x, y, i));
for (i=0;i<N;i++) t2[i]=y[i]+0.5*(k2[i]=step*f(x+h, t1, i));
for (i=0;i<N;i++) t3[i]=y[i]+    (k3[i]=step*f(x+h, t2, i));
for (i=0;i<N;i++) k4[i]=                step*f(x+step, t3, i);

for (i=0;i<N;i++) y[i]+=(k1[i]+2*k2[i]+2*k3[i]+k4[i])/6.0;
}

double  f(double x, double y[], int i)
{
if (i==0) return(y[1]);                 /* derivative of first equation */
if (i==1) return(-1*y[0]);       /* derivative of second equation */
}
