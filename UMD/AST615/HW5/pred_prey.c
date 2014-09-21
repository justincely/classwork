#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define	NDERIVS	2		//Number of derivatives
typedef double real;		/* (a convenient synonym...) */

double  f(double x, double y[], int i){
  //printf("%f\t%f\n",y[1],y[0]);
  double q=0;
  //double q=1.25;
  if (i==0) return(1*y[0]-.1*y[1]*y[0]-q*y[0]);                 /* derivative of first equation */
  if (i==1) return(-1.5*y[1]+.03*y[1]*y[0]-q*y[1]);             /* derivative of second equation */
}

void runge4(double x, double y[], double step){
  int N=NDERIVS;
  double h=step/2.0,t1[N], t2[N], t3[N],k1[N], k2[N], k3[N],k4[N];      
  int i;
 
  for (i=0;i<N;i++) t1[i]=y[i]+0.5*(k1[i]=step*f(x, y, i));
  for (i=0;i<N;i++) t2[i]=y[i]+0.5*(k2[i]=step*f(x+h, t1, i));
  for (i=0;i<N;i++) t3[i]=y[i]+(k3[i]=step*f(x+h, t2, i));
  for (i=0;i<N;i++) k4[i]=step*f(x+step, t3, i);
  for (i=0;i<N;i++) y[i]+=(k1[i]+2*k2[i]+2*k3[i]+k4[i])/6.0;
}

void RK4(char *fout,double t,double tmax,double step,double x_0,double v_0){
  double y[NDERIVS];
  int i;
 
  FILE *file;
  file=fopen(fout, "w");                   /* external filename */
  y[0]=x_0;                                       /* initial position */
  y[1]=v_0;                                       /* initial velocity */
  fprintf(file, "0\t%f\t%f\n", y[0],y[1]);
 
  for (i=1; i*step<=tmax ;i++)                     /* time loop */
    {
      t=i*step;
      runge4(t, y, step); 
      fprintf(file, "%f\t%f\t%f\n", t, y[0],y[1]);
      printf("%1.10f\t%1.10f\t%1.10f\n", t, y[0],y[1]);
    }
  fclose(file);
}

int main(int argc, char** argv){
  double t=0, tmax=100, step=.1;
  double x_0=30,v_0=3;
  int i;
    /* Parse the argument list. */
    for (i = 0; i < argc; i++)
	if (argv[i][0] == '-') {
	    switch (argv[i][1]) {
		case 's':	step = atof(argv[++i]); break;
		case 't':	tmax = atof(argv[++i]); break;
		case 'v':	v_0 = atof(argv[++i]); break;
	        case 'x':	x_0 = atof(argv[++i]); break;
	    }
	}
    RK4(argv[1],t,tmax,step,x_0,v_0);
}
