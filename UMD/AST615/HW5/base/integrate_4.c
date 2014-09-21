/* Integration of 1-D simple harmonic motion using the Leapfrog scheme.
 * Usage:
 *	    integrate.p [-m mode] [-d time-step]  [-t end-time]  [-v initial-v]
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define	NDERIVS	4		//Number of derivatives
typedef double real;		/* (a convenient synonym...) */

real deriv(real x)		/* determine a = dv/dt, given x */
{
    return -1*x;
}

void advance_vel(real x, real *v, real dt2)
{
    *v += dt2*deriv(x);		/* offset the velocity by half a
				   time step using the Euler method */
}

void output(real x, real v, real t, real dt)
{
    /* Synchronize the velocity if necessary: */
    if (t > 0) advance_vel(x, &v, -0.5*dt);
    printf("%f\t%f\t%f\n", t, sin(t),x);
}

void leapfrog(real *x, real *v, real *t, real step)
{
    *x+=step*(*v);
    *v+=step*deriv(*x);
    *t+=step;
}

void lpf_int(double t, double tmax, double step, double x, double v)
{
    /* Default integration parameters and initial conditions: */
    int i;
    
    output(x, v, t, step);
    advance_vel(x, &v, 0.5*step);
    while (t < tmax) {
	leapfrog(&x, &v, &t, step);
	if (t > 0) advance_vel(x, &v, -0.5*step);
	printf("%f\t%f\t%f\n", t, sin(t),x);
	//output(x, v, t, step);
    }
}

void CalcDerivs(int numDerivs, float *x, float t, float *k1) {
// numDerivs is the number of derivatives to be calculated
// x is an array containing the state variables
// t is time
// k1 is the array of calculated derivatives.

        k1[0]=x[1];        //Calculate derivative;
	k1[1]=-x[0];
	//k1[0]=1*x[0]-.1*x[0]*x[1]-1*x[0];        //Calculate derivative;
	//k1[1]=1.5*x[1]*.03*x[0]*x[1]-1*x[1];
}

int euler(double t, double tmax, double step, double x, double v) {
        float yexact, ystar;				//Exact and approximate values of y
	float xs[NDERIVS], k1[NDERIVS];		        //Arrays to hold state variables and derivatives.
	int i;	

	FILE *file;
	file=fopen("out_euler.txt","w+");
	xs[0]=x;							//Initial conditions.
	xs[1]=v;
	for (t=0;t<tmax;t=t+step) {	       
		CalcDerivs(NDERIVS, xs, t, k1);
		ystar=xs[0];					//Get approx y.
		printf("%f\t%f\t%f\n",t,sin(t),ystar);
		fprintf(file,"%f\t%f\t%f\n",t,sin(t),ystar);
		for(i=0;i<NDERIVS;i++) {	//Update state variables.
			xs[i]=xs[i]+step*k1[i];
		}		
	}
}

double  f(double x, double y[], int i){
  //printf("%f\t%f\n",y[1],y[0]);
  //if (i==0) return(y[1]);                 /* derivative of first equation */
  //if (i==1) return(-1*y[0]);       /* derivative of second equation */
  //if (i==0) return(1*y[0]-.1*y[1]*y[0]-.01*y[0]);                 /* derivative of first equation */
  //if (i==1) return(-1.5*y[1]+.03*y[1]*y[0]-.01*y[1]);       /* derivative of second equation */
  if (i==0) return(y[0]);                 /* derivative of first equation */
  if (i==1) return(y[1]);       /* derivative of second equation */
  if (i==2) return((-2*y[1])/pow((1+2*y[1]*y[1]+2*y[0]*y[0]),(3/2)));                 /* derivative of first equation */
  if (i==3) return((-2*y[0])/pow((1+2*y[1]*y[1]+2*y[0]*y[0]),(3/2)));       /* derivative of second equation */

}

void runge4(double x, double y[], double step){
  int N=NDERIVS;
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

void RK4(double t,double tmax,double step,double x_0,double v_0){
  double y[NDERIVS];
  int i;
 
  FILE *file;
  file=fopen("RK4.txt", "w");                   /* external filename */
  y[0]=1;                                       /* initial position */
  y[1]=0;
  y[2]=0;
    y[3]=.1;                                    /* initial velocity */
  fprintf(file, "0\t%f\t%f\n", y[1],y[0]);
 
  for (i=1; i*step<=tmax ;i++)                     /* time loop */
    {
      t=i*step;
      runge4(t, y, step); 
      fprintf(file, "%f\t%f\t%f\n", t, y[1],y[0]);
      printf("%f\t%f\t%f\n", t, y[0],y[1]);
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
    if (mode==0){lpf_int(t,tmax,step,x_0,v_0);}
    if (mode==1){euler(t,tmax,step,x_0,v_0);}
    if (mode==2){RK4(t,tmax,step,x_0,v_0);}
}
