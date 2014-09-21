#include <stdio.h>		//I/O library
#include <math.h>		//Math library

#define	NDERIVS	2		//Number of derivatives

void CalcDerivs(int numDerivs, float *x, float t, float *k1) {
// numDerivs is the number of derivatives to be calculated
// x is an array containing the state variables
// t is time
// k1 is the array of calculated derivatives.

	k1[0]=x[1];        //Calculate derivative;
	//k1[1]=1.0E4*fabs(sin(377.0*t))-100*x[1]-1.0E4*x[0];
	k1[1]=-x[0];
}

int main() {
	float yexact, ystar;				//Exact and approximate values of y
	float x[NDERIVS], k1[NDERIVS];		//Arrays to hold state variables and derivatives.
	float h=0.1, t=0.0, tmax=15.0;	//Step size, time, final time.
	int i;	

	FILE *file;
	file=fopen("out_euler.txt","w+");
      	printf("Welcome to Euler - the ODE solver\n");
	x[0]=0.0;							//Initial conditions.
	x[1]=1.0;	
	printf("time(ms) input output\n");
	for (t=0;t<tmax;t=t+h) {	       
		CalcDerivs(NDERIVS, x, t, k1);
		ystar=x[0];					//Get approx y.
		printf("%7.2f %7.4f %7.4f\n",t,sin(t),ystar);
		fprintf(file,"%7.2f %7.4f %7.4f\n",t,sin(t),ystar);
		for(i=0;i<NDERIVS;i++) {	//Update state variables.
			x[i]=x[i]+h*k1[i];
		}		
	}
	printf("Program is done.\n");
}
