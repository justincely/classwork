#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define	NDERIVS	2		//Number of derivatives

double deriv(double x){
    return -1*x;
}

void advance_vel(double x, double *v, double step){
    *v += step*deriv(x);		/* offset the velocity by half a
				   time step using the Euler method */
}

void leapfrog(double *x, double *v, double *t, double step){
    *x+=step*(*v);
    *v+=step*deriv(*x);
    *t+=step;
}

void lpf_int(char *fout,double t, double tmax, double step, double x, double v){
    /* Default integration parameters and initial conditions: */
    int i;
    FILE *file;
    file=fopen(fout,"w");
    if (t > 0) advance_vel(x, &v, -0.5*step);
    printf("%f\t%e\t%e\n", t, sin(t),x);
    fprintf(file,"%f\t%e\t%e\n", t, sin(t),x);
    advance_vel(x, &v, 0.5*step);
    while (t < tmax) {
      leapfrog(&x, &v, &t, step);
      printf("%f\t%e\t%e\n", t, sin(t),x);
      fprintf(file,"%f\t%e\t%e\n", t, sin(t),x);
    }
}

void CalcDerivs(int numDerivs, float *x, float t, float *k1) {
// numDerivs is the number of derivatives to be calculated
// x is an array containing the state variables
// t is time
// k1 is the array of calculated derivatives.

        k1[0]=x[1];        //Calculate derivative;
	k1[1]=-x[0];
}

int euler(char *fout, double t, double tmax, double step, double x, double v) {
        float ystar;				//Exact and approximate values of y
	float xs[NDERIVS], k1[NDERIVS];		        //Arrays to hold state variables and derivatives.
	int i;	

	FILE *file;
	file=fopen(fout,"w");
	xs[0]=x;							//Initial conditions.
	xs[1]=v;	
	for (t=0;t<tmax;t=t+step) {	       
		CalcDerivs(NDERIVS, xs, t, k1);
		printf("%f\t%e\t%e\n",t,sin(t),xs[0]);
		fprintf(file,"%f\t%e\t%e\n",t,sin(t),xs[0]);
		for(i=0;i<NDERIVS;i++) {	
			xs[i]=xs[i]+step*k1[i];
		}		
	}
}

double  f(double x, double y[], int i){
  //printf("%f\t%f\n",y[1],y[0]);
  if (i==0) return(y[1]);                 /* derivative of first equation */
  if (i==1) return(-1*y[0]);       /* derivative of second equation */
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
  file=fopen(fout, "w");              
  y[0]=x_0;                                       
  y[1]=v_0;                                       
  fprintf(file, "0\t%e\t%e\n", sin(t),y[0]);
 
  for (i=1; i*step<=tmax ;i++){
      t=i*step;
      runge4(t, y, step); 
      fprintf(file, "%f\t%e\t%e\n", t, sin(t),y[0]);
      printf("%f\t%e\t%e\n", t, sin(t),y[0]);
    }
  fclose(file);
}

int main(int argc, char** argv){
  double t=0, tmax=15, step=.1;
  double x_0=0,v_0=1;
  int i;
  char mode='l';
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
    mode=atoi(argv[1]);
    if (mode==1){euler(argv[2],t,tmax,step,x_0,v_0);}
    if (mode==2){RK4(argv[2],t,tmax,step,x_0,v_0);}
    if (mode==3){lpf_int(argv[2],t,tmax,step,x_0,v_0);}
}
